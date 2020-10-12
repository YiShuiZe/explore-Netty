package com.yishuize.netty.websocket;

import com.yishuize.netty.heartbeat.MyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-4-9 下午9:11
 * updated by yang on 20-4-9 下午9:11
 */
public class MyServer {
    public static void main(String[] args) throws InterruptedException {
        // 创建两个线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)) // 在bossGroup 增加一个日志处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();

                            // 因为基于http协议，使用Http编码和解码器
                            pipeline.addLast(new HttpServerCodec());
                            // 是以块方式写，添加ChunkedWriteHandler处理器
                            pipeline.addLast(new ChunkedWriteHandler());

                            // http数据在传输过程中是分段的，HttpObjectAggregator，就是可以将多个段聚合
                            // 这就是为什么，当浏览器发送大量数据时，就会发出多次http请求
                            pipeline.addLast(new HttpObjectAggregator(8192));

                            // 对于websocket，它的数据是以 帧(frame)的形式传递
                            // 可以看到WebSocketFrame 下面有六个子类
                            // 浏览器请求时 ws://localhost:7000/hello  表示请求的uri
                            // WebSocketServerProtocolHandler 核心功能是将 http协议升级为 ws协议，保持长连接
                            // 是通过一个 状态码 101
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));

                            // 自定义的handler，处理业务逻辑
                            pipeline.addLast(new MyTestWebSocketFrameHandler());
                        }
                    });

            ChannelFuture channelFuture = bootstrap.bind(7000).sync();
            channelFuture.channel().closeFuture().sync();


        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
