package com.suyun.vehicle.client;

import com.suyun.vehicle.protocol.MessageBuilder;
import com.suyun.vehicle.server.TerminalProtoDecoder;
import com.suyun.vehicle.server.TerminalProtoEncoder;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

/**
 * Client Initializer
 *
 * Created by jcchen on 16-9-5.
 */
public class TerminalClientInitializer extends ChannelInitializer<SocketChannel> {

    private static final int maxFrameLength = 8192;
    private static final byte delimeter = MessageBuilder.DELIMETER;

    private static final TerminalClientHandler CLIENT_HANDLER = new TerminalClientHandler();

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
        pipeline.addLast(CLIENT_HANDLER);
    }
}
