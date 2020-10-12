package com.yishuize.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-4-9 下午4:43
 * updated by yang on 20-4-9 下午4:43
 */
public class MyServer {
    public static void main(String[] args) throws Exception {

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
                            // 加入一个netty 提供的 IdleStateHandler
                            // 说明
                            // 1. IdleStateHandler 是 netty 提供的处理空闲状态的处理器
                            // 2. long readerIdleTime : 表示多长时间没有读，就会发送一个心跳检测包检测是否连接
                            // 3. long writerIdleTime : 表示多长时间没有写，就会发送一个心跳检测包检测是否连接
                            // 4. long allIdleTime : 表示多长时间没有读写，就会发送一个心跳检测包检测是否连接
                            // 当IdleStateEvent 触发后，就会传递给管道 的下一个handler去处理
                            // 通过调用（触发）下一个handler的userEventTiggered，在该方法中去处理
                            // IdleStateEvent (读空闲、写空闲、读写空闲)
                            pipeline.addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));
                            // 加入一个对空闲检测进一步处理的handler(自定义)
                            pipeline.addLast(new MyServerHandler());
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
