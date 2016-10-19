package com.suyun.vehicle.client;

import com.suyun.vehicle.protocol.Message;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Client handler
 *
 * Created by jcchen on 16-9-5.
 */
@Sharable
public class TerminalClientHandler extends SimpleChannelInboundHandler<Message> {

    public static final Logger LOGGER = LoggerFactory.getLogger(TerminalClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ByteBuf msg = Unpooled.buffer(256);
//        for (int i = 0; i < msg.capacity(); i++) {
//            msg.writeByte((byte)i);
//        }
//        ctx.writeAndFlush(msg);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        System.out.println("Receive: " + msg);
        System.out.println("R (HEX): " + msg.toHexString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        LOGGER.error("Client error", cause);
        ctx.close();
    }
}
