package com.suyun.vehicle.action.impl;

import com.suyun.vehicle.action.BaseAction;
import com.suyun.vehicle.protocol.Body;
import com.suyun.vehicle.protocol.Message;
import com.suyun.vehicle.protocol.MessageCode;
import com.suyun.vehicle.protocol.ProtoException;
import com.suyun.vehicle.protocol.body.CanBusBatch;
import com.suyun.vehicle.service.VehicleCanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 报文传输附加协议 :CAN总线批量上报(0x0C21)
 * Created by liam on 24/10/2016.
 */
@Service
public class CanBusBatchAction extends BaseAction{
    private static final Logger LOGGER = LoggerFactory.getLogger(CanBusBatchAction.class);
    @Override
    public int actionCode() {
        return MessageCode.T_REQ_CAN_BUS_BATCH;
    }

    @Autowired
    private VehicleCanService service;

    @Override
    public Message handle(Message in) throws Exception {
        CanBusBatch busBatch = (CanBusBatch) in.body();
        int result = service.saveDataToLogger(busBatch) ? SUCCESS : FAILURE;
        LOGGER.info("vehicle.save_0c21_can_data.action","save data to elastic search result : "+ result);
        return commonResponse(in,result);
    }
}
