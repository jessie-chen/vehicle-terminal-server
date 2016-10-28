package com.suyun.vehicle.action.impl;

import com.suyun.vehicle.action.BaseAction;
import com.suyun.vehicle.gen.model.BusInfo;
import com.suyun.vehicle.protocol.Body;
import com.suyun.vehicle.protocol.Message;
import com.suyun.vehicle.protocol.MessageCode;
import com.suyun.vehicle.protocol.body.TerminalAuthentication;
import com.suyun.vehicle.service.VehicleService;
import com.suyun.vehicle.utils.MobileUtil;
import com.suyun.vehicle.utils.TokenUtil;
import jdk.nashorn.internal.ir.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Authentication Action
 *
 * Created by IT on 16/10/8.
 */
@Service
public class AuthenticationAction extends BaseAction {
    @Override
    public int actionCode() {
        return MessageCode.T_REQ_AUTHENTICATION;
    }

    @Autowired
    private VehicleService service;


    @Override
    public Message handle(Message in) throws Exception {
        String headMobile = in.header().mobile().toHexString();
        String authCode = ((TerminalAuthentication) in.body()).getAuthenticationCodeSrc();
        headMobile = MobileUtil.transferMobile(headMobile);
        int result = service.validAuthenticationCode(authCode,headMobile);
        return commonResponse(in, result);
    }
}
