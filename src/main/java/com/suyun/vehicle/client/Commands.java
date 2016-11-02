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
                hexString = "7E0100002C0646205194820CE2000000000000000000484C363631000000000000000000000000000000363647303034340036364730303434D17E";
                break;
            case "authentication":
//                hexString = auth();
                hexString = "7E0102002006462051948216104E6A51324D6A41314D546B304F44493D2E6C465330616F5167386E6645507771537E";
                break;
            case "heartbeat":
                hexString = "7E000200000134123456782C8F9C7E";
                break;
            case "logoff":
                hexString = "7E00030000013412345678000A347E";
                break;
            case "location":
                hexString="7E0200003C0159241722950053000000000004000301CEC1C10727E5510034000000B3161014214512010400000008030200002504000000002A0204002B040000000030010E3101042B7E";
                break;
            case "can1":
                hexString="7E0C20003301592417229500601C000000000004000301CEC1C10727E551002C000000B31610142146301610142146300000000198FF50E50210010000000000BC7E";
                break;
            case "can2":
                hexString="7E0C21031E015924172295005D000F0000331C000000000004000301CEC1C10727E5510045000000B31610142143131610142143130000000198FF50E5021001000000000000331C000000000004000301CEC1C10727E5510043000000B31610142143231610142143230000000198FF50E5021001000000000000331C000000000004000301CEC1C10727E5510041000000B31610142143331610142143330000000198FF50E5021001000000000000331C000000000004000301CEC1C10727E5510040000000B31610142143431610142143430000000198FF50E5021001000000000000331C000000000004000301CEC1C10727E5510040000000B31610142143531610142143530000000198FF50E5021001000000000000331C000000000004000301CEC1C10727E551003F000000B31610142144031610142144030000000198FF50E5021001000000000000331C000000000004000301CEC1C10727E551003D000000B31610142144131610142144130000000198FF50E5021001000000000000331C000000000004000301CEC1C10727E551003A000000B31610142144231610142144230000000198FF50E5021001000000000000331C000000000004000301CEC1C10727E5510039000000B31610142144331610142144330000000198FF50E5021001000000000000331C000000000004000301CEC1C10727E5510039000000B31610142144431610142144430000000198FF50E5021001000000000000331C000000000004000301CEC1C10727E5510038000000B31610142144531610142144530000000198FF50E5021001000000000000331C000000000004000301CEC1C10727E5510035000000B31610142145031610142145030000000198FF50E5021001000000000000331C000000000004000301CEC1C10727E5510034000000B31610142145131610142145130000000198FF50E5021001000000000000331C000000000004000301CEC1C10727E5510033000000B31610142145231610142145230000000198FF50E5021001000000000000331C000000000004000301CEC1C10727E5510032000000B31610142145331610142145330000000198FF50E50210010000000000F97E" ;
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
