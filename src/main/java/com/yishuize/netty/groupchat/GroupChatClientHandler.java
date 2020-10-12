package com.yishuize.netty.groupchat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-4-9 下午4:16
 * updated by yang on 20-4-9 下午4:16
 */
public class GroupChatClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(s.trim());
    }
}
