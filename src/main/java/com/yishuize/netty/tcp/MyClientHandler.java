package com.yishuize.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-4-11 下午1:00
 * updated by yang on 20-4-11 下午1:00
 */
public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 使用for循环发送10条数据 hello,server 编号
        for (int i = 0; i < 10; i++) {
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello,server " + i, Charset.forName("utf-8"));
            ctx.writeAndFlush(byteBuf);
        }
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        byte[] buffle = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(buffle);

        String message = new String(buffle, Charset.forName("utf-8"));
        System.out.println("客户端接收到消息=" + message);

        System.out.println("客户端接收消息数量=" + (++this.count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
