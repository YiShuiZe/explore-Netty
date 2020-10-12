package com.yishuize.im.server.handler;

import com.yishuize.im.protocol.request.MessageRequestPacket;
import com.yishuize.im.protocol.response.MessageResponsePacket;
import com.yishuize.im.session.Session;
import com.yishuize.im.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * <h3>netty-study</h3>
 * <p>
 *     1. A 要和 B 聊天，首先 A 和 B 需要与服务器建立连接，然后进行一次登录流程，服务端保存用户标识和 TCP 连接的映射关系
 *     2. A 发消息给 B，首先需要将带有 B 标识的消息数据包发送到服务器，然后服务器从消息数据包中拿到 B 的标识，找到对应的 B 的连接，将消息发送给 B。
 * </p>
 * Created by yang on 20-5-15 下午12:55
 * updated by yang on 20-5-15 下午12:55
 */
@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    private MessageRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) throws Exception {
//        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
//        System.out.println(new Date() + ": 收到客户端消息： " + messageRequestPacket.getMessage());
//        messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
//        channelHandlerContext.channel().writeAndFlush(messageResponsePacket);

        // 1. 拿到消息发送方的会话信息
        Session session = SessionUtil.getSession(channelHandlerContext.channel());

        // 2. 通过消息发送方的会话信息构造要发送的信息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        // 3. 拿到消息接收方的channel
        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());

        // 4. 将消息发送到消息接收方
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            System.out.println("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败！");
        }
    }
}
