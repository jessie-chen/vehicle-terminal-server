package com.suyun.vehicle.action;

import com.suyun.vehicle.protocol.Body;
import com.suyun.vehicle.protocol.Header;
import com.suyun.vehicle.protocol.Message;
import com.suyun.vehicle.protocol.MessageBuilder;
import com.suyun.vehicle.protocol.body.ServerCommonResponse;
import org.springframework.stereotype.Component;

/**
 * Base Action
 *
 * Created by IT on 16/10/8.
 */
public abstract class BaseAction implements Action {

    // RESULT CODE
    public final static int SUCCESS = 0;
    public final static int FAILURE = 1;
    public final static int FAULT = 2;
    public final static int NOT_SUPPORT = 3;
    public final static int ALARM = 3;
    private static int seqNum = 0;

    protected Message commonResponse(Message in, int result) {
        Header header = in.header();
        String mobile = header.mobile().toHexString();
        int reqSeq = header.serialNum().intValue();
        int code = header.id().intValue();
        Body body = new ServerCommonResponse(reqSeq, code, result);
        return MessageBuilder.create(mobile, getSeqNum(), body);
    }

    protected Message response(Message in, Body body) {
        String mobile = in.header().mobile().toHexString();
        return MessageBuilder.create(mobile, getSeqNum(), body);
    }

    protected int getSeqNum() {
        return seqNum++;
    }
}
