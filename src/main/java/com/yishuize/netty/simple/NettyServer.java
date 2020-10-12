package com.yishuize.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-4-8 上午11:02
 * updated by yang on 20-4-8 上午11:02
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {

        // 创建BossGroup 和 WorkerGruop\
        // 说明
        //1. 创建两个线程组 bossGroup 和 workerGroup
        //2. bossGroup 只是处理连接请求，真正的客户端业务处理，会交给workerGroup完成
        //3. 两个都是无限循环
        //4. bossGroup 和 workerGroup 含有的子线程(NioEventLoop)的个数
        // 默认实际 cpu核数 * 2
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

        // 创建服务器端的启动对象，配置参数
        ServerBootstrap bootstrap = new ServerBootstrap();

        // 使用链式变成来进行设置
        bootstrap.group(bossGroup, workerGroup) // 设置两个线程组
                .channel(NioServerSocketChannel.class) // 使用NioServerSocketChannel 作为服务器的通道实现
                .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列得到的连接个数
                .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持活动连接状态
                .childHandler(new ChannelInitializer<SocketChannel>() {// 创建一个通道测试对象（匿名对象）
                    // 给pipeline 设置处理器
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new NettyServerHandler());
                    }
                }); // 给我们的workerGroup 的 EventLoop 对应的管道设置处理器


        System.out.println("...... server is ready ......");

        // 绑定一个端口并且同步，生成一个 ChannelFuture 对象
        // 启动服务器（并绑定端口）
        ChannelFuture cf = bootstrap.bind(6668).sync();

        // 对关闭通道进行监听
        cf.channel().closeFuture().sync();


        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
