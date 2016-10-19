package com.suyun.vehicle.server;

import com.suyun.vehicle.protocol.Message;
import com.suyun.vehicle.protocol.MessageBuilder;
import com.suyun.vehicle.protocol.utils.HexConverter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Protocol decoder
 *
 * Created by jcchen on 16-9-5.
 */
public class TerminalProtoDecoder extends ByteToMessageDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(TerminalProtoDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int len = in.readableBytes();
        if (len <= 0) {
            return;
        }
        byte[] bytes = new byte[len];
        in.readBytes(bytes);
        try {
            Message msg = MessageBuilder.create(unescape(bytes));
            out.add(msg);
        } catch (Exception e) {
            LOGGER.error("Decode to message failed. hex msg: " + HexConverter.bytesToHexString(bytes), e);
        }
    }

    private byte[] unescape(byte[] content) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < content.length; i++) {
            if(content[i] == 0x7d && content[i+1] == 0x01 && i+1 < content.length) {
                baos.write(0x7d);
                i++;
            } else if(content[i] == 0x7d && content[i+1] == 0x02 && i+1 < content.length) {
                baos.write(0x7e);
                i++;
            } else {
                baos.write(content[i]);
            }
        }
        return baos.toByteArray();
    }
}
