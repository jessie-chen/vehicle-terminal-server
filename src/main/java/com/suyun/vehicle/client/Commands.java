package com.suyun.vehicle.client;


import com.suyun.vehicle.protocol.Body;
import com.suyun.vehicle.protocol.Message;
import com.suyun.vehicle.protocol.MessageBuilder;
import com.suyun.vehicle.protocol.ProtoException;
import com.suyun.vehicle.protocol.body.ServerCommonResponse;
import com.suyun.vehicle.protocol.body.TerminalAuthentication;
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
                hexString = auth();
                //hexString = "7E010200030134123456782C8E313234A87E";
                break;
            case "heartbeat":
                hexString = "7E000200000134123456782C8F9C7E";
                break;
            case "logoff":
                hexString = "7E00030000013412345678000A347E";
                break;
            case "location":
                hexString="7E0200003C013751000100" +
                        "001F00000100000400000000000000000000000000000000161012142529010400003530030200002504000000002A0200022B0400000000300118310100D97E";
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

    private static String auth() {
        String code = "4D544D304D54497A4E4455324E7A673D2E72484E446C335A344C5459794730393944374C2F347775344F32383D";
        Body auth = new TerminalAuthentication(code);
        MessageImpl msg = (MessageImpl) MessageBuilder.create("13412345678", 5, auth);
        String s = msg.toHexString(true);
        System.out.println(s);
        return s;
    }


}
