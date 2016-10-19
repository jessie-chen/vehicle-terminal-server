package com.suyun.vehicle.client;


import com.suyun.vehicle.protocol.Body;
import com.suyun.vehicle.protocol.Message;
import com.suyun.vehicle.protocol.MessageBuilder;
import com.suyun.vehicle.protocol.ProtoException;
import com.suyun.vehicle.protocol.body.ServerCommonResponse;
import com.suyun.vehicle.protocol.impl.MessageImpl;

/**
 * Test commands
 *
 * Created by jcchen on 16-9-12.
 */
public class Commands {

    public static Message getContent(String command) {
        MessageImpl msg = new MessageImpl();
        String hexString;
        switch (command) {
            case "register":
                hexString = "7E0100002C0134123456782C8C000000000000000000534C363638302D474200000000000000000000003136443232333400313644323233348F7E";
                break;
            case "authentication":
                hexString = "7E010200030134123456782C8E313234A87E";
                break;
            case "heartbeat":
                hexString = "7E000200000134123456782C8F9C7E";
                break;
            case "logoff":
                hexString = "7E00030000013412345678000A347E";
                break;
            case "location":
                hexString="7E0200003C064620519468001F00000100000400000000000000000000000000000000161012142529010400003530030200002504000000002A0200022B0400000000300118310100D97E";
                break;
            default:
                hexString = null;
        }
        if (hexString == null) {
            return null;
        }

        try {
            msg.fromHexString(hexString, true);
        } catch (ProtoException e) {
            e.printStackTrace();
        }
        return msg;
    }

    private static Message register() {
        MessageImpl msg = new MessageImpl();
        try {
            msg.fromHexString("7E0100002C0134123456782C8C000000000000000000534C363638302D474200000000000000000000003136443232333400313644323233348F7E", true);
        } catch (ProtoException e) {
            e.printStackTrace();
        }
        System.out.println(msg);
        System.out.println(msg.toHexString());
        return msg;
    }

    private static Message serverCommonResponse() {
        Body body = new ServerCommonResponse(0x7d,0x7e,1);
        Message msg = MessageBuilder.create(body.msgId(), "18138438111", 10, body);
        return msg;
    }
}
