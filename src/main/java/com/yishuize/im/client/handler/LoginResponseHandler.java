package com.yishuize.im.client.handler;

import com.yishuize.im.protocol.request.LoginRequestPacket;
import com.yishuize.im.protocol.response.LoginResponsePacket;
import com.yishuize.im.session.Session;
import com.yishuize.im.util.LoginUtil;
import com.yishuize.im.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-15 下午12:59
 * updated by yang on 20-5-15 下午12:59
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginResponsePacket loginResponsePacket) throws Exception {

        String userId = loginResponsePacket.getUserId();
        String userName = loginResponsePacket.getUserName();

        if (loginResponsePacket.isSuccess()) {
            System.out.println("[" + userName + "]登录成功，userId为：" + userId);
            SessionUtil.bindSession(new Session(userId, userName), channelHandlerContext.channel());
        } else {
            System.out.println("[" + userName + "]登录失败，原因："  + loginResponsePacket.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接被关闭！");
    }
}
