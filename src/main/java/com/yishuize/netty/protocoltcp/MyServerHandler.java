package com.yishuize.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * <h3>netty-study</h3>
 * <p>
 *     Server 端处理业务的 Handler
 * </p>
 * Created by yang on 20-4-11 下午1:04
 * updated by yang on 20-4-11 下午1:04
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol) throws Exception {

        // 接收到数据并处理
        int len = messageProtocol.getLen();
        byte[] content = messageProtocol.getContent();

        System.out.println("服务端接收到信息如下");
        System.out.println("长度=" + len);
        System.out.println("内容=" + new String(content, Charset.forName("utf-8")));

        System.out.println("服务器接收到消息包数据量" + (++this.count));

        // 回复消息
        String responseContent = UUID.randomUUID().toString();
        byte[] responseContentBytes = responseContent.getBytes(Charset.forName("utf-8"));
        int length = responseContentBytes.length;
        // 构建一个协议包
        MessageProtocol responseMessageProtocol = new MessageProtocol();
        responseMessageProtocol.setLen(length);
        responseMessageProtocol.setContent(responseContentBytes);

        channelHandlerContext.writeAndFlush(responseMessageProtocol);


    }
}
