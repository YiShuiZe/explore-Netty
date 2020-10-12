package com.yishuize.im.server.handler;

import com.yishuize.im.protocol.request.ListGroupMembersRequestPacket;
import com.yishuize.im.protocol.response.ListGroupMembersResponsePacket;
import com.yishuize.im.session.Session;
import com.yishuize.im.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-16 下午3:19
 * updated by yang on 20-5-16 下午3:19
 */
@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    private ListGroupMembersRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ListGroupMembersRequestPacket listGroupMembersRequestPacket) throws Exception {
        // 1. 获取群的 ChannelGroup
        String groupId = listGroupMembersRequestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        // 2. 遍历群成员的 channel 对应的 session，构造群成员的信息
        List<Session> sessionList = new ArrayList<>();
        for (Channel channel : channelGroup) {
            Session session = SessionUtil.getSession(channel);
            sessionList.add(session);
        }

        // 3. 构造获取群成员列表响应写回客户端
        ListGroupMembersResponsePacket listGroupMembersResponsePacket = new ListGroupMembersResponsePacket();

        listGroupMembersResponsePacket.setGroupId(groupId);
        listGroupMembersResponsePacket.setSessionList(sessionList);
        channelHandlerContext.channel().writeAndFlush(listGroupMembersResponsePacket);
    }
}
