package com.suyun.vehicle.action.impl;

import com.suyun.vehicle.action.BaseAction;
import com.suyun.vehicle.protocol.Message;
import com.suyun.vehicle.protocol.MessageCode;
import org.springframework.stereotype.Service;

/**
 * Heartbeat handler
 *
 * Created by IT on 16/10/8.
 */
@Service
public class HeartbeatAction extends BaseAction {

    @Override
    public int actionCode() {
        return MessageCode.T_REQ_HEARTBEAT;
    }

    @Override
    public Message handle(Message in) {
        int result = SUCCESS;
        return commonResponse(in, result);
    }
}
