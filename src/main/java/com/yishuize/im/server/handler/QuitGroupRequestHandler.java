package com.yishuize.im.server.handler;

import com.yishuize.im.protocol.request.QuitGroupRequestPacket;
import com.yishuize.im.protocol.response.QuitGroupResponsePacket;
import com.yishuize.im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-16 下午3:15
 * updated by yang on 20-5-16 下午3:15
 */
@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {

    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();

    private QuitGroupRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, QuitGroupRequestPacket quitGroupRequestPacket) throws Exception {
        // 1. 获取群对应的 channelGroup， 然后将当前用户的 channel 移除
        String groupId = quitGroupRequestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.remove(channelHandlerContext.channel());

        // 2. 构造退群响应发送给客户端
        QuitGroupResponsePacket quitGroupResponsePacket = new QuitGroupResponsePacket();
        quitGroupResponsePacket.setGroupId(groupId);
        quitGroupResponsePacket.setSuccess(true);
        channelHandlerContext.channel().writeAndFlush(quitGroupResponsePacket);
    }
}
