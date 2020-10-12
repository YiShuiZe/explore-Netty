package com.yishuize.im.server.handler;

import com.yishuize.im.protocol.request.GroupMessageRequestPacket;
import com.yishuize.im.protocol.response.GroupMessageResponsePacket;
import com.yishuize.im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * <h3>netty-study</h3>
 * <p>
 *     1. A，B，C 依然会经历登录流程，服务端保存用户标识对应的 TCP 连接
 *     2. A 发起群聊的时候，将 A，B，C 的标识发送至服务端，服务端拿到之后建立一个群聊 ID，然后把这个 ID 与 A，B，C 的标识绑定
 *     3. 群聊里面任意一方在群里聊天的时候，将群聊 ID 发送至服务端，服务端拿到群聊 ID 之后，取出对应的用户标识，遍历用户标识对应的 TCP 连接，就可以将消息发送至每一个群聊成员
 *
 *     群聊的原理和单聊类似，无非都是通过标识拿到 channel。
 *     本小节，我们重构了一下控制台的程序结构，在实际带有 UI 的 IM 应用中，我们输入的第一个指令其实就是对应我们点击 UI 的某些按钮或菜单的操作。
 *     通过 ChannelGroup，我们可以很方便地对一组 channel 进行批量操作。
 * </p>
 * Created by yang on 20-5-16 下午4:13
 * updated by yang on 20-5-16 下午4:13
 */
@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    private GroupMessageRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GroupMessageRequestPacket groupMessageRequestPacket) throws Exception {
        // 1. 拿到 groupId 构造群聊消息的响应
        String groupId = groupMessageRequestPacket.getToGroupId();
        GroupMessageResponsePacket groupMessageResponsePacket = new GroupMessageResponsePacket();
        groupMessageResponsePacket.setFromGroupId(groupId);
        groupMessageResponsePacket.setMessage(groupMessageRequestPacket.getMessage());
        groupMessageResponsePacket.setFromUser(SessionUtil.getSession(channelHandlerContext.channel()));

        // 2. 拿到群聊对应的 channelGroup，写到每个客户端
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.writeAndFlush(groupMessageResponsePacket);
    }
}
