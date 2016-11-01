package com.suyun.vehicle.action.impl;

import com.suyun.vehicle.action.BaseAction;
import com.suyun.vehicle.protocol.Body;
import com.suyun.vehicle.protocol.Message;
import com.suyun.vehicle.protocol.MessageCode;
import com.suyun.vehicle.protocol.body.TerminalRegister;
import com.suyun.vehicle.protocol.body.TerminalRegisterResponse;
import com.suyun.vehicle.protocol.utils.HexConverter;
import com.suyun.vehicle.service.VehicleService;
import com.suyun.vehicle.utils.MobileUtil;
import com.suyun.vehicle.utils.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Register Action
 *
 * Created by IT on 16/10/8.
 */
@Service
public class RegisterAction extends BaseAction {

    @Autowired
    private VehicleService vehicleService;
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterAction.class);
    @Autowired
    private TokenUtil tokenUtil;

    public static int SUCCESS = 0;
    public static int TERMINAL_ALREADY_SIGNED = 3;
    public static int CAR_NOT_FOUND = 2;
    public static int CAR_ALREADY_SIGNED = 1;
    public static int TERMINAL_NOT_FOUND = 4;

    @Override
    public int actionCode() {
        return MessageCode.T_REQ_REGISTER;
    }

    @Override
    public Message handle(Message in) throws Exception {
        String mobileNumber = MobileUtil.transferMobile(in.header().mobile().toHexString());
        TerminalRegister register = (TerminalRegister) in.body();
        Map<String,Object> resultMap = vehicleService.register(mobileNumber,register);
        int reqSeq = in.header().serialNum().intValue();
        int result = (int) resultMap.get("result");
        String token = (String) resultMap.get("token");
        Body body = new TerminalRegisterResponse(reqSeq,result,token);
        if (token != "") {
            LOGGER.info("vehicle.register.action","success,return generated token:"+ token);
            return response(in, body);
        } else {
            LOGGER.info("vehicle.register.action","valid register failed. return common Response");
            return commonResponse(in,result);
        }
    }
}
