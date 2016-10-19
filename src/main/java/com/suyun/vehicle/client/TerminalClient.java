package com.suyun.vehicle.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Terminal client
 *
 * Created by jcchen on 16-9-5.
 */
public class TerminalClient {

    private String host = "127.0.0.1";
    private int port;

    public TerminalClient(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new TerminalClient(8001).run();
    }

    private void run() throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new TerminalClientInitializer());
            Channel ch = b.connect(host, port).sync().channel();

            // Read commands from stdin
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            ChannelFuture lastWriteFuture = null;
            while (true) {
                String line = in.readLine();
                if(line == null) break;
                lastWriteFuture = ch.writeAndFlush(Commands.getContent(line));

                if("logoff".equals(line.toLowerCase())) {
                    ch.closeFuture().sync();
                    break;
                }
            }
            if(lastWriteFuture != null) {
                lastWriteFuture.sync();
            }

        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
