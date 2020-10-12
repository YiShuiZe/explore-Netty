package com.yishuize.im.client.handler;

import com.yishuize.im.protocol.response.LogoutResponsePacket;
import com.yishuize.im.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-16 上午11:53
 * updated by yang on 20-5-16 上午11:53
 */
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LogoutResponsePacket logoutResponsePacket) throws Exception {
        SessionUtil.unBindSession(channelHandlerContext.channel());
    }
}
