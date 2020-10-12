package com.yishuize.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-4-9 下午3:49
 * updated by yang on 20-4-9 下午3:49
 */
public class GroupChatClient {

    // 属性
    private final String host;
    private final int port;

    public GroupChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        try {

            ChannelFuture channelFuture = bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            // 得到pipeline
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 加入相关的handler
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            // 加入自定义的handler
                            pipeline.addLast(new GroupChatClientHandler());
                        }
                    })
                    .connect(host, port)
                    .sync();

            // 得到channel

            Channel channel = channelFuture.channel();
            System.out.println("------------" + channel.localAddress() + "------------");

            // 客户端需要输入信息，创建一个扫描器
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                // 通过channel　发送到服务器端
                channel.writeAndFlush(msg + "\r\n");
            }

        } finally {
          group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new GroupChatClient("127.0.0.1", 7000).run();
    }
}
