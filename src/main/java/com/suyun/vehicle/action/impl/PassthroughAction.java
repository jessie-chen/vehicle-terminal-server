package com.suyun.vehicle.action.impl;

import com.suyun.vehicle.action.BaseAction;
import com.suyun.vehicle.gen.model.CanRawData;
import com.suyun.vehicle.protocol.Message;
import com.suyun.vehicle.protocol.MessageCode;
import com.suyun.vehicle.protocol.Passthrough;
import com.suyun.vehicle.protocol.body.PassthroughBody;
import com.suyun.vehicle.service.VehicleCanService;
import com.suyun.vehicle.utils.MobileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassthroughAction extends BaseAction {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterAction.class);
    @Autowired
    public VehicleCanService service;

    @Override
    public int actionCode() {
        return MessageCode.T_REQ_PASSTHROUGH;
    }

    @Override
    public Message handle(Message in) throws Exception {
        PassthroughBody messageBody = (PassthroughBody) in.body();
        String mobile = MobileUtil.transferMobile(in.header().mobile().toHexString());
        List<CanRawData> rawDataList = service.parseBodyToCanRowData(in.body(),VehicleCanService.CAN_PASSTHROUGH_DATA,mobile);
        int result = 0;
        switch (messageBody.getMessageType()) {
            case 0x01:
                result = service.saveDataToLogger(rawDataList) ? SUCCESS :FAILURE;
                LOGGER.info("vehicle.can_passthrough_data.action","save data result: >>"+result);
                return commonResponse(in, result);
            case 0x81:
                LOGGER.info("vehicle.can_passthrough_data.action","message type 0x81 dose not supported");
                result = FAULT;
                return commonResponse(in, result);
            default:
                LOGGER.info("vehicle.can_passthrough_data.action","message type doesn't exists");
                return commonResponse(in, result);
        }
    }
}
