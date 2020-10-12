package com.yishuize.im.client.handler;

import com.yishuize.im.protocol.response.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-16 下午3:25
 * updated by yang on 20-5-16 下午3:25
 */
public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, QuitGroupResponsePacket quitGroupResponsePacket) throws Exception {
        if (quitGroupResponsePacket.isSuccess()) {
            System.out.println("退出群聊[" + quitGroupResponsePacket.getGroupId() + "]成功！");
        } else {
            System.out.println("退出群聊[" + quitGroupResponsePacket.getGroupId() + "]失败！");
        }
    }
}
