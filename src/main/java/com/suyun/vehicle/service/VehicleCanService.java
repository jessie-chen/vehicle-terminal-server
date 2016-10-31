package com.suyun.vehicle.service;

import com.suyun.vehicle.gen.model.CanRawData;
import com.suyun.vehicle.protocol.Body;
import com.suyun.vehicle.protocol.body.CANPassthrough;
import com.suyun.vehicle.protocol.body.CanBusBatch;
import com.suyun.vehicle.protocol.body.CanBusBody;
import com.suyun.vehicle.utils.TimeUtil;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.params.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * save can data as logger
 * Created by liam on 24/10/2016.
 */
@Service
public class VehicleCanService {
    @Autowired
    private JestClient client;

    public boolean saveDataToLogger(Object obj) throws IOException {
        if (null != obj) {
            Index index = new Index.Builder(obj)
                    .index("can_data").type("can_raw_data")
                    .setParameter(Parameters.REFRESH, true).build();
            client.execute(index);
            return true;
        } else {
            return false;
        }
    }

    public static final String CAN_BUS_DATA = "CAN_BUS_DATA";
    public static final String CAN_BUS_BATCH_DATA = "CAN_BUS_BATCH_DATA";
    public static final String CAN_PASSTHROUGH_DATA = "CAN_PASSTHROUGH_DATA";

    @Autowired
    private VehicleService service;

    //analytic attach protocol
    public List<CanRawData> parseBodyToCanRowData(Body body, String type,String mobile) throws ParseException {
        String busID = service.getBusInfoByPhoneNo(mobile).getId();
        CanRawData data = new CanRawData();
        List<CanRawData> rawDataList = new ArrayList<>();
        switch (type) {
            case CAN_BUS_DATA:
                CanBusBody busBody = (CanBusBody) body;
                for (CanBusBody.CanPackage canPackage : busBody.getPackages()){
                    data.setBus_id(busID);
                    data.setDate(TimeUtil.BCDToDate(busBody.getDatetime()));
                    data.setValue(canPackage.getCanData());
                    data.setCan_id(String.valueOf(canPackage.getCanId()));
                    rawDataList.add(data);
                }
                return rawDataList;
            case CAN_BUS_BATCH_DATA:
                CanBusBatch batch = (CanBusBatch) body;
                for (CanBusBatch.BatchItem items : batch.getItems()) {
                    for (CanBusBody.CanPackage canPackage : items.getContent().getPackages()) {
                        data.setBus_id(busID);
                        data.setDate(TimeUtil.BCDToDate(items.getContent().getDatetime()));
                        data.setValue(canPackage.getCanData());
                        data.setCan_id(String.valueOf(canPackage.getCanId()));
                        rawDataList.add(data);
                    }
                }
                return rawDataList;
            case CAN_PASSTHROUGH_DATA:
                CANPassthrough canData = (CANPassthrough) body;
                for (CANPassthrough.CANPackage canPackage : canData.getPackages()) {
                    data.setCan_id(String.valueOf(canPackage.getCanId()));
                    data.setValue(canPackage.getCanData());
                    data.setBus_id(busID);
                    data.setDate(TimeUtil.BCDToDate(canData.getDate()));
                    rawDataList.add(data);
                }
                return rawDataList;
            default:
                System.out.println("class type unsupported");
                return new ArrayList<>();
        }
    }
}
