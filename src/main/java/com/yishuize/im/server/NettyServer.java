package com.yishuize.im.server;

import com.yishuize.im.codec.PacketCodecHandler;
import com.yishuize.im.codec.Spliter;
import com.yishuize.im.handler.IMIdleStateHandler;
import com.yishuize.im.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-5 下午7:13
 * updated by yang on 20-5-5 下午7:13
 */
public class NettyServer {

    private static final int PORT = 8000;

    public static void main(String[] args) {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024) // 给服务端channel设置属性，so_backlog表示系统用于临时存放已完成三次握手的请求的队列的最大长度，如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                //.attr(AttributeKey.newInstance("serverName"), "nettyServer") // 可以给服务端的channel，也就是NioServerSocketChannel绑定一些自定义属性
                .childHandler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        // 指定连接数据读写逻辑
                        // 获取服务端侧关于这条连接的逻辑处理链pipeline
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        //pipeline.addLast(new FirstServerHandler());
                        /**
                         * 每次有新连接到来的时候，都会调用 ChannelInitializer 的 initChannel() 方法，然后这里 9 个指令相关的 handler 都会被 new 一次。
                         * 其实这里的每一个指令 handler，他们内部都是没有成员变量的，也就是说是无状态的，我们完全可以使用单例模式，即调用 pipeline().addLast() 方法的时候，都直接使用单例，不需要每次都 new，提高效率，也避免了创建很多小的对象。
                         */
                        pipeline.addLast(new IMIdleStateHandler()) // 空闲检测
                                .addLast(new Spliter())
                                //.addLast(new PacketDecoder())
                                // 压缩handler，合并编解码器
                                .addLast(PacketCodecHandler.INSTANCE)
                                .addLast(LoginRequestHandler.INSTANCE) // 登录请求处理器
                                // 服务端的 pipeline 中需要再加上如下一个 handler - HeartBeatRequestHandler，由于这个 handler 的处理其实是无需登录的，所以，我们将该 handler 放置在 AuthHandler 前面
                                .addLast(HeartBeatRequestHandler.INSTANCE)
                                .addLast(AuthHandler.INSTANCE)
                                // 压缩 handler - 合并平行 handler
                                .addLast(IMHandler.INSTANCE);
                                //.addLast(new PacketEncoder());
                    }
                })
                // childOption可以给每条连接设置一些TCP底层相关的属性
                .childOption(ChannelOption.SO_KEEPALIVE, true)// 表示是否开启TCP底层心跳机制，true未开启
                .childOption(ChannelOption.TCP_NODELAY, true);// 表示是否开启Nagle算法。通俗的说，如果要求高实时性，有数据发送时就马上发送，就关闭，如果需要减少发送次数减少网络交互，就开启。
                //.childAttr(AttributeKey.newInstance("clientKey"), "clientValue"); // 可以给每一条连接指定自定义属性
        bind(serverBootstrap, PORT);

    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口[" + port + "]绑定成功！");
            } else {
                System.out.println("端口[" + port + "]绑定失败!");
                bind(serverBootstrap, port + 1);
            }
        });
    }
}
