package com.suyun.vehicle.action.impl;

import com.suyun.vehicle.action.BaseAction;
import com.suyun.vehicle.protocol.Message;
import com.suyun.vehicle.protocol.body.CanBusBody;
import com.suyun.vehicle.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;

public class CanBusAction extends BaseAction {

    @Override
    public int actionCode() {
        return 0;
    }
    @Autowired
    private VehicleService service;

    @Override
    public Message handle(Message in) throws Exception {
        CanBusBody canBusBody = (CanBusBody) in.body();
        int result;
        if (service.saveCanData(canBusBody,in.header().mobile().toHexString())) {
            result = SUCCESS;
        } else {
            result = FAILURE;
        }
        return commonResponse(in,result);
    }
}
