package com.yishuize.netty.protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-4-11 下午12:59
 * updated by yang on 20-4-11 下午12:59
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new MyMessageDecoder());// 解码器
        pipeline.addLast(new MyMessageEncoder());// 编码器
        pipeline.addLast(new MyServerHandler());
    }
}
