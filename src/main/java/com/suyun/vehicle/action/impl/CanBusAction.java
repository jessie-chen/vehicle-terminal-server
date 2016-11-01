package com.suyun.vehicle.action.impl;

import com.suyun.vehicle.action.BaseAction;
import com.suyun.vehicle.protocol.Message;
import com.suyun.vehicle.protocol.MessageCode;
import com.suyun.vehicle.protocol.body.CanBusBody;
import com.suyun.vehicle.service.VehicleCanService;
import com.suyun.vehicle.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  can总线协议（0x0c20）
 */
@Service
public class CanBusAction extends BaseAction {
    private static final Logger LOGGER = LoggerFactory.getLogger(CanBusAction.class);
    @Override
    public int actionCode() {
        return MessageCode.T_REQ_CAN_BUS;
    }
    @Autowired
    private VehicleCanService service;

    @Override
    public Message handle(Message in) throws Exception {
        int result;
        CanBusBody canBusBody = (CanBusBody) in.body();
        result = service.saveDataToLogger(canBusBody) ? SUCCESS : FAILURE;
        LOGGER.info("vehicle.save_0c20_can_data.action","save data to es search result : "+ result);
        return commonResponse(in,result);
    }
}
