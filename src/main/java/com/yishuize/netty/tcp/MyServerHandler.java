package com.yishuize.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-4-11 下午1:04
 * updated by yang on 20-4-11 下午1:04
 */
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {

        byte[] buffer = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(buffer);

        // 将buffer转成字符串
        String message = new String(buffer, Charset.forName("utf-8"));

        System.out.println("服务器接收到数据 " + message);
        System.out.println("服务器接收到消息量=" + (++this.count));

        // 服务器回送数据给客户端，回送一个随机id
        ByteBuf responseByteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString() + " ", Charset.forName("utf-8"));
        channelHandlerContext.writeAndFlush(responseByteBuf);
    }
}
