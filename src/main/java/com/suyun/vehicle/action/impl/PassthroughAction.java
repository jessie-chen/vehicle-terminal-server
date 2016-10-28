package com.suyun.vehicle.action.impl;

import com.suyun.vehicle.action.BaseAction;
import com.suyun.vehicle.protocol.Message;
import com.suyun.vehicle.protocol.MessageCode;
import com.suyun.vehicle.protocol.Passthrough;
import com.suyun.vehicle.protocol.body.PassthroughBody;
import com.suyun.vehicle.service.VehicleCanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassthroughAction extends BaseAction {

    @Autowired
    public VehicleCanService service;

    @Override
    public int actionCode() {
        return MessageCode.T_REQ_PASSTHROUGH;
    }

    @Override
    public Message handle(Message in) throws Exception {
        PassthroughBody messageBody = (PassthroughBody) in.body();
        int result = 0;
        switch (messageBody.getMessageType()) {
            case 0x01:
                Passthrough passthrough = messageBody.getBody();
                result = service.saveDataToLogger(passthrough) ? SUCCESS :FAILURE;
                return commonResponse(in, result);
            case 0x81:
                result = FAULT;
                return commonResponse(in, result);
            default:
                return commonResponse(in, result);
        }
    }
}
