package com.suyun.vehicle.action.impl;

import com.suyun.vehicle.action.BaseAction;
import com.suyun.vehicle.protocol.Message;
import com.suyun.vehicle.protocol.body.CANPassthrough;
import com.suyun.vehicle.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;

public class PassthrowAction extends BaseAction {

    @Autowired
    public VehicleService service;

    @Override
    public int actionCode() {
        return 0;
    }

    @Override
    public Message handle(Message in) throws Exception {
        CANPassthrough messageBody = (CANPassthrough) in.body();
        boolean saveDataResult = service.savePassthrouwCanData(messageBody, in.header().mobile().toHexString());
        int result;
        if (saveDataResult){
            result = SUCCESS;
        } else {
            result = FAILURE;
        }
        return commonResponse(in,result);
    }
}
