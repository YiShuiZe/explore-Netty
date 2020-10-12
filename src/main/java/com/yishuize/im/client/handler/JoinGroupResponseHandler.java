package com.yishuize.im.client.handler;

import com.yishuize.im.protocol.response.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-16 下午3:11
 * updated by yang on 20-5-16 下午3:11
 */
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, JoinGroupResponsePacket joinGroupResponsePacket) throws Exception {
        if (joinGroupResponsePacket.isSuccess()) {
            System.out.println("加入群[" + joinGroupResponsePacket.getGroupId() + "]成功!");
        } else {
            System.out.println("加入群[" + joinGroupResponsePacket.getGroupId() + "]失败，原因为：" + joinGroupResponsePacket.getReason());
        }
    }
}
