package com.yishuize.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-4-9 下午3:22
 * updated by yang on 20-4-9 下午3:22
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    // 定义一个channle 组，管理所有的channel
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // handlerAdded 表示连接建立，一旦连接，第一个被执行
    // 将当前channel 加入到 channleGroup
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 将该客户加入聊天的信息推送给其它在线的客户端
        // 该方法会将 channelGroup 中所有的channel 遍历，并发送消息
        // 我们不需要遍历
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + " 加入聊天\n" + "时间: " + sdf.format(new Date()));
        channelGroup.add(channel);
    }

    // 断开连接，将xx客户离开信息推送给当前在线的客户端
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + " 离开了\n");
        // 此事件结束会自动将该channle从ChannelGroup中移除
        System.out.println("channleGroup size = " + channelGroup.size());
    }

    // 表示channel 处于一个活动状态，提示 xx上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println(ctx.channel().remoteAddress() + " 上线了～");
    }

    // 表示channel 处于不活动状态，提示 xx离线
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        System.out.println(ctx.channel().remoteAddress() + " 离线了～");
    }

    // 读取数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {

        // 获取到当前channle
        Channel channel = ctx.channel();
        // 这时我们遍历channleGroup，根据不同情况，回送不同的消息

        channelGroup.forEach(ch -> {
            if (channel != ch) {// 不是当前的channel，转发消息
                ch.writeAndFlush("[客户]" + channel.remoteAddress() + " 发送了消息: " + s + "\n");
            } else {// 回显自己发送的消息给自己
                ch.writeAndFlush("[自己]发送了消息: " + s + "\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 关闭通道
        ctx.close();
    }
}
