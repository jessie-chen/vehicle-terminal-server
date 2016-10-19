package com.suyun.vehicle.server;

import com.suyun.vehicle.protocol.MessageBuilder;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

/**
 * Server Initializer
 *
 * Created by jcchen on 16-9-5.
 */
public class TerminalServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final int maxFrameLength = 8192;
    private static final byte delimeter = MessageBuilder.DELIMETER;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new DelimiterBasedFrameDecoder(maxFrameLength,
            Unpooled.copiedBuffer(new byte[]{delimeter, delimeter}),
            Unpooled.copiedBuffer(new byte[]{delimeter})
        ));

        //pipeline.addLast(new LoggingHandler(LogLevel.ERROR));

        pipeline.addLast(new TerminalProtoDecoder());
        pipeline.addLast(new TerminalProtoEncoder());
        pipeline.addLast(new TerminalServerHandler());
    }
}
