package com.yishuize.im.server.handler;

import com.yishuize.im.protocol.request.LoginRequestPacket;
import com.yishuize.im.protocol.request.LogoutRequestPacket;
import com.yishuize.im.protocol.response.LogoutResponsePacket;
import com.yishuize.im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * <h3>netty-study</h3>
 * <p>
 *     登出请求
 * </p>
 * Created by yang on 20-5-16 上午11:47
 * updated by yang on 20-5-16 上午11:47
 */
@ChannelHandler.Sharable
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {

    public static final LogoutRequestHandler INSTANCE = new LogoutRequestHandler();

    private LogoutRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LogoutRequestPacket logoutRequestPacket) throws Exception {
        SessionUtil.unBindSession(channelHandlerContext.channel());
        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
        logoutResponsePacket.setSuccess(true);
        channelHandlerContext.channel().writeAndFlush(logoutResponsePacket);
    }
}
