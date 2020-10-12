package com.yishuize.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-4-9 下午9:58
 * updated by yang on 20-4-9 下午9:58
 */
public class MyTestWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    // 这里 TextWebSocketFrame 类型，表示一个文本帧(frame)

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

        System.out.println("服务器收到消息 " + textWebSocketFrame.text());

        // 回复消息
        channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(
                "服务器时间 " +
                LocalDateTime.now() +
                        " " +
                        textWebSocketFrame.text()));
    }

    // 当web客户端连接后，触发方法
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        // id 表示唯一的值，LongTest 是唯一的，ShortText不是唯一的
        System.out.println("handlerAdded 被调用" + ctx.channel().id().asLongText());
        System.out.println("handlerAdded 被调用" + ctx.channel().id().asShortText());
    }

    //
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved 被调用" + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生 " + cause.getMessage());
        ctx.close();
    }
}
