package com.suyun.vehicle.action.impl;

import com.suyun.vehicle.action.BaseAction;
import com.suyun.vehicle.gen.model.BusInfo;
import com.suyun.vehicle.protocol.Message;
import com.suyun.vehicle.protocol.MessageCode;
import com.suyun.vehicle.service.VehicleService;
import com.suyun.vehicle.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Authentication Action
 *
 * Created by IT on 16/10/8.
 */
public class AuthenticationAction extends BaseAction {
    @Override
    public int actionCode() {
        return MessageCode.T_REQ_AUTHENTICATION;
    }
    @Autowired
    private VehicleService service;

    @Autowired
    private TokenUtil tokenUtil;
    @Override
    public Message handle(Message in) throws Exception {
        BusInfo busInfo;int result;
        String no = in.header().mobile().toHexString();
        if (null != service.getBusInfoByPhoneNo(no)){
            busInfo = service.getBusInfoByPhoneNo(no);
            if (busInfo.getMobile().equals(tokenUtil.extractToken(in.checkcode().toString()))){
                result = SUCCESS;
            } else {
                result = FAILURE;
            }
        } else {
            result = FAILURE;
        }
        return commonResponse(in, result);
    }
}
