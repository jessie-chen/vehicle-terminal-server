package com.suyun.vehicle.action.impl;

import com.suyun.vehicle.action.BaseAction;
import com.suyun.vehicle.protocol.Message;
import com.suyun.vehicle.protocol.MessageCode;
import com.suyun.vehicle.protocol.body.PositionBody;
import com.suyun.vehicle.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationReportAction extends BaseAction {
    @Override
    public int actionCode() {
        return MessageCode.T_REQ_LOCATION_REPORT;
    }

    @Autowired
    private VehicleService service;

    @Override
    public Message handle(Message in) throws Exception {
        int result;
        PositionBody body = (PositionBody) in.body();
        if (service.saveLocationData(body,in.header().mobile().toHexString())){
            result = SUCCESS;
        } else {
            result = FAILURE;
        }
        return commonResponse(in, result);
    }
}
