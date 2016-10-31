package com.suyun.vehicle.action.impl;

import com.suyun.vehicle.action.BaseAction;
import com.suyun.vehicle.protocol.Message;
import com.suyun.vehicle.protocol.MessageCode;
import com.suyun.vehicle.protocol.body.CanBusBody;
import com.suyun.vehicle.service.VehicleCanService;
import com.suyun.vehicle.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  can总线协议（0x0c20）
 */
@Service
public class CanBusAction extends BaseAction {

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
        return commonResponse(in,result);
    }
}
