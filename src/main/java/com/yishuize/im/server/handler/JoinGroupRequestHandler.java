package com.yishuize.im.server.handler;

import com.yishuize.im.protocol.request.JoinGroupRequestPacket;
import com.yishuize.im.protocol.response.JoinGroupResponsePacket;
import com.yishuize.im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-16 下午3:01
 * updated by yang on 20-5-16 下午3:01
 */
@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {

    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();

    private JoinGroupRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, JoinGroupRequestPacket joinGroupRequestPacket) throws Exception {
        // 1. 获取群对应的 channelGroup，然后将当前用户的 channel 添加进去
        String groupId = joinGroupRequestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.add(channelHandlerContext.channel());

        // 2. 构造加群响应发送给客户端
        JoinGroupResponsePacket joinGroupResponsePacket = new JoinGroupResponsePacket();

        joinGroupResponsePacket.setSuccess(true);
        joinGroupResponsePacket.setGroupId(groupId);
        channelHandlerContext.channel().writeAndFlush(joinGroupResponsePacket);

    }
}
