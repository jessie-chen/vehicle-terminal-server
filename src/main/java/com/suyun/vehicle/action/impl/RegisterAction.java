package com.suyun.vehicle.action.impl;

import com.suyun.vehicle.action.BaseAction;
import com.suyun.vehicle.protocol.Body;
import com.suyun.vehicle.protocol.Message;
import com.suyun.vehicle.protocol.MessageCode;
import com.suyun.vehicle.protocol.body.TerminalRegister;
import com.suyun.vehicle.protocol.body.TerminalRegisterResponse;
import com.suyun.vehicle.protocol.utils.HexConverter;
import com.suyun.vehicle.service.VehicleService;
import com.suyun.vehicle.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Register Action
 *
 * Created by IT on 16/10/8.
 */
@Service
public class RegisterAction extends BaseAction {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public int actionCode() {
        return MessageCode.T_REQ_REGISTER;
    }

    @Override
    public Message handle(Message in) throws Exception {
            String mobileNumber = in.header().mobile().toHexString();
            String bodyMessage = HexConverter.bytesToHexString(in.body().getBytes());
            TerminalRegister register = new TerminalRegister();
            register.fromHexString(bodyMessage);
            vehicleService.saveCarRegisterInfo(in.header().mobile().toHexString(),register);
            boolean validResult = vehicleService.validRegister(mobileNumber);
            String token; int result;
            if (validResult) {
                result = SUCCESS;
                vehicleService.updateDataActiveStatus(mobileNumber,true);
                token = tokenUtil.generateToken(mobileNumber);
            } else {
                result = FAILURE;
                token = "";
            }
            int reqSeq = in.header().serialNum().intValue();
            Body body = new TerminalRegisterResponse(reqSeq,result,token);
                return response(in, body);
        }
}
