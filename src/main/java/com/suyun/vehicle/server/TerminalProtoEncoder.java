package com.suyun.vehicle.server;

import com.suyun.vehicle.protocol.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ByteArrayOutputStream;


/**
 * Created by jcchen on 16-9-5.
 */
//@Sharable
public class TerminalProtoEncoder extends MessageToByteEncoder<Message> {

    private static final byte DELIMETER = 0x7E;

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        out.writeByte(DELIMETER);
        out.writeBytes(escape(msg.getBytes()));
        out.writeByte(DELIMETER);
    }

    private byte[] escape(byte[] content) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (byte b : content) {
            if(b == 0x7d) {
                baos.write(0x7d);
                baos.write(0x01);
            } else if(b == 0x7e) {
                baos.write(0x7d);
                baos.write(0x02);
            } else {
                baos.write(b);
            }
        }
        return baos.toByteArray();
    }
}
