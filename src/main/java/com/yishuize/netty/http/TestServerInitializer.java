package com.yishuize.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-4-8 下午9:31
 * updated by yang on 20-4-8 下午9:31
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        // 向管道加入处理器

        // 得到管道
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 加入一个netty 提供的httpServerCodec codec => [coder - decoder]
        // HttpServerCodec 说明
        // 1. HttpServerCodec 是netty 提供的处理http 的编码解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());

        // 2. 增加一个自定义的handler
        pipeline.addLast("MyTestHttpServerHandler", new TestHttpServerHandler());
    }
}
