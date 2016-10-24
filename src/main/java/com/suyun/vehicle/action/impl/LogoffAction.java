package com.suyun.vehicle.action.impl;

import com.suyun.vehicle.action.BaseAction;
import com.suyun.vehicle.gen.model.BusInfo;
import com.suyun.vehicle.protocol.Message;
import com.suyun.vehicle.protocol.MessageCode;
import com.suyun.vehicle.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Logoff Action
 *
 * Created by IT on 16/10/8.
 */
@Service
public class LogoffAction extends BaseAction {
    @Override
    public int actionCode() {
        return MessageCode.T_REQ_LOGOFF;
    }

    @Autowired
    public VehicleService service;


    @Override
    public Message handle(Message in) {
        // 业务处理
        int result;
        BusInfo busInfo = service.getBusInfoByPhoneNo(in.checkcode().toHexString());
        if (null != busInfo){
            result = SUCCESS;
        } else {
            result = FAILURE;
        }
        return commonResponse(in, result);
    }
}
