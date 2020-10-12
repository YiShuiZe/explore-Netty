package com.yishuize.im.client.handler;

import com.yishuize.im.protocol.response.GroupMessageResponsePacket;
import com.yishuize.im.session.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-16 下午4:23
 * updated by yang on 20-5-16 下午4:23
 */
public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GroupMessageResponsePacket groupMessageResponsePacket) throws Exception {
        String fromGroupId = groupMessageResponsePacket.getFromGroupId();
        Session fromUser = groupMessageResponsePacket.getFromUser();
        System.out.println("收到群[" + fromGroupId + "]中[" + fromUser + "]发来的消息：" + groupMessageResponsePacket.getMessage());
    }
}
