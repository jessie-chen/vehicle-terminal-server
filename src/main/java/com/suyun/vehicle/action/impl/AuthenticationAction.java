package com.suyun.vehicle.action.impl;

import com.suyun.vehicle.action.ActionFactory;
import com.suyun.vehicle.action.BaseAction;
import com.suyun.vehicle.common.queue.MessagePublisher;
import com.suyun.vehicle.common.queue.MessageTypes;
import com.suyun.vehicle.common.queue.Topic;
import com.suyun.vehicle.gen.model.BusInfo;
import com.suyun.vehicle.protocol.Body;
import com.suyun.vehicle.protocol.Message;
import com.suyun.vehicle.protocol.MessageCode;
import com.suyun.vehicle.protocol.body.TerminalAuthentication;
import com.suyun.vehicle.service.VehicleService;
import com.suyun.vehicle.utils.MobileUtil;
import com.suyun.vehicle.utils.TokenUtil;
import jdk.nashorn.internal.ir.Terminal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Authentication Action
 *
 * Created by IT on 16/10/8.
 */
@Service
public class AuthenticationAction extends BaseAction {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationAction.class);
    @Override
    public int actionCode() {
        return MessageCode.T_REQ_AUTHENTICATION;
    }

    @Autowired
    private VehicleService service;

//    @Autowired
//    private MessagePublisher publisher;

    @Override
    public Message handle(Message in) throws Exception {
        String headMobile = in.header().mobile().toHexString();
        String authCode = ((TerminalAuthentication) in.body()).getAuthenticationCodeSrc();
        headMobile = MobileUtil.transferMobile(headMobile);
        int result = service.validAuthenticationCode(authCode,headMobile);
        LOGGER.info("vehicle.authentication.action","valid auth code result : "+ result);
        return commonResponse(in, result);
    }
}
