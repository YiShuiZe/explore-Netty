package com.yishuize.im.client;

import com.yishuize.im.client.console.ConsoleCommandManager;
import com.yishuize.im.client.console.LoginConsoleCommand;
import com.yishuize.im.client.handler.*;
import com.yishuize.im.codec.PacketDecoder;
import com.yishuize.im.codec.PacketEncoder;
import com.yishuize.im.codec.Spliter;
import com.yishuize.im.handler.IMIdleStateHandler;
import com.yishuize.im.protocol.PacketCodec;
import com.yishuize.im.protocol.request.LoginRequestPacket;
import com.yishuize.im.protocol.request.MessageRequestPacket;
import com.yishuize.im.protocol.response.MessageResponsePacket;
import com.yishuize.im.util.LoginUtil;
import com.yishuize.im.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-5 下午7:38
 * updated by yang on 20-5-5 下午7:38
 */
public class NettyClient {
    private static final int MAX_RETRY= 10;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 1. 指定线程模型
                .group(workerGroup)
                // 2. 指定IO类型为 NIO
                .channel(NioSocketChannel.class)
                //.attr(AttributeKey.newInstance("clientName"), "nettyClient")
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)//连接的超时时间
                .option(ChannelOption.SO_KEEPALIVE, true)//开启TCP底层心跳机制
                .option(ChannelOption.TCP_NODELAY, true)//开启Nagle算法
                // 3. IO 处理逻辑
                .handler( new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // pipeline是和这条连接相关的逻辑处理链，采用了责任链模式
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //pipeline.addLast(new FirstClientHandler());
                        //pipeline.addLast(new ClientHandler());
                        pipeline.addLast(new IMIdleStateHandler()) // 空闲检测
                                .addLast(new Spliter())
                                .addLast(new PacketDecoder())
                                .addLast(new LoginResponseHandler()) // 登录相应处理器
                                .addLast(new MessageResponseHandler()) // 接收单聊消息处理器
                                .addLast(new CreateGroupResponseHandler()) // 创建群响应处理器
                                .addLast(new JoinGroupResponseHandler()) // 加群响应处理器
                                .addLast(new QuitGroupResponseHandler()) // 退群响应处理器
                                .addLast(new ListGroupMembersResponseHandler()) // 获取群成员响应处理器
                                .addLast(new GroupMessageResponseHandler())
                                .addLast(new LogoutResponseHandler()) // 登出响应处理器
                                .addLast(new PacketEncoder())
                                .addLast(new HeartBeatTimerHandler()); // 心跳定时器
                    }
                });
        // 建立连接
        connect(bootstrap, HOST, PORT, MAX_RETRY);

    }

    // 通常情况下，连接建立失败不会立即重新连接，而是会通过一个指数退避的方式，
    // 比如每隔1秒、2秒、4秒、8秒，以2的幂次来建立连接，到达一定次数之后就放弃连接
    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 连接成功，启动控制台线程......");
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
            } else if (retry == 0){
                System.out.println("重试次数已用完，放弃连接!");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.out.println(new Date() + ": 连接失败，第" + order + "次重连......");
                bootstrap
                        .config()
                        .group()
                        .schedule(
                                () -> connect(bootstrap, host, port, retry - 1),
                                delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {
                    loginConsoleCommand.exec(scanner, channel);
                } else {
                    consoleCommandManager.exec(scanner, channel);
                }
            }
        }).start();
    }
}
