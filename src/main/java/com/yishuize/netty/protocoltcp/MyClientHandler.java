package com.yishuize.netty.protocoltcp;

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
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 使用for循环发送10条数据 “今天阴天，傍晚看直播” 编号
        for (int i = 0; i < 5; i++) {
            String mes = "今天阴天，傍晚看直播";
            byte[] content = mes.getBytes(Charset.forName("utf-8"));
            int length = mes.getBytes(Charset.forName("utf-8")).length;

            // 创建协议包对象
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLen(length);
            messageProtocol.setContent(content);
            ctx.writeAndFlush(messageProtocol);
        }

    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol) throws Exception {
        int len = messageProtocol.getLen();
        byte[] content = messageProtocol.getContent();

        System.out.println("肯段接收到的消息如下");
        System.out.println("长度=" + len);
        System.out.println("内容=" + new String(content, Charset.forName("utf-8")));

        System.out.println("客户端接受消息数量=" + (++this.count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常消息=" + cause.getMessage());
        ctx.close();
    }
}
