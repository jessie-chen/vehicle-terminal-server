package com.suyun.vehicle.server;

import com.suyun.vehicle.action.Action;
import com.suyun.vehicle.action.ActionFactory;
import com.suyun.vehicle.action.BaseAction;
import com.suyun.vehicle.protocol.Body;
import com.suyun.vehicle.protocol.Message;
import com.suyun.vehicle.protocol.MessageCode;
import com.suyun.vehicle.protocol.body.ServerCommonResponse;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

/**
 * Server handler
 *
 * Created by jcchen on 16-9-5.
 */
public class TerminalServerHandler extends SimpleChannelInboundHandler<Message> {

    public static final Logger LOGGER = LoggerFactory.getLogger(TerminalServerHandler.class);

    /**
     * 免鉴权 action code
     */
    private static final int[] IGNORE_AUTH_CHECK_CODE = new int[] {
            MessageCode.T_REQ_REGISTER, MessageCode.T_REQ_AUTHENTICATION
    };

    /**
     * 关闭链接 action code
     */
    private static final int[] CLOSE_CONNECTION_CODE = new int[] {
            MessageCode.T_REQ_LOGOFF,
    };

    /**
     * 鉴权 action code
     */
    private static final int[] VERIFY_AUTH_CODE = new int[] {
            MessageCode.T_REQ_AUTHENTICATION
    };

    private boolean isAuthentication = false;


    @Override
    public void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        System.out.println(msg);
        System.out.println(msg.toHexString());

        int code = msg.header().id().intValue();

        // 权限判断: 如果不是"注册"/"鉴权"请求,需要先判断是否已鉴权. 如果没有,不回复任何消息
        if (notAuth(code)) {
            return;
        }

        // TODO 分包处理,加密处理
        // 1. 如果是分包请求,解析"消息包封装项",判断是否所有的分包都收到. 如未收到,向客户端发送"补传分包请求"
        // 2. 分包的逻辑是什么? 把每个分包的body取出来,然后拼到一起,才拼成一个完整的数据包?
        // 3. 加密是怎么加? 第个包只加自己的body部分?

        Action action = ActionFactory.getInstance().getAction(code);
        if (action == null) {
            LOGGER.error("No handler for 0x" + Integer.toHexString(code) + ". Please check with developer");
            return;
        }

        Message result = action.handle(msg);
        if (result != null) {
            ChannelFuture future = ctx.write(result);

            if (isInArray(code, VERIFY_AUTH_CODE)) {
                if (isSuccessResult(result)) {
                    isAuthentication = true;
                }
            }
            if (isInArray(code, CLOSE_CONNECTION_CODE)) {
                if (isSuccessResult(result)) {
                    future.addListener(ChannelFutureListener.CLOSE);
                }
            }
        }
    }

    private boolean isSuccessResult(Message result) {
        Body body = result.body();
        if (body instanceof ServerCommonResponse) {
            return ((ServerCommonResponse) body).getResult() == BaseAction.SUCCESS;
        }
        return false;
    }

    private boolean notAuth(int code) {
        boolean checkIgnore = isInArray(code, IGNORE_AUTH_CHECK_CODE);
        if(!checkIgnore && !isAuthentication) {
            LOGGER.info("No authentication. Discard request");
            return true;
        }
        return false;
    }

    private boolean isInArray(int code, int[] array) {
        return IntStream.of(array).anyMatch(x -> code == x);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        LOGGER.error("Server error", cause);
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

}
