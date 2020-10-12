package com.yishuize.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * <h3>netty-study</h3>
 * <p>
 *     说明
 *     1. SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter
 *     2. HttpObject 客户端和服务器端相互通讯的数据被封装成 HttpObject
 * </p>
 * Created by yang on 20-4-8 下午9:31
 * updated by yang on 20-4-8 下午9:31
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    // channelRead0 读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        // 判断 msg 是不是 httprequest请求
        if (msg instanceof HttpRequest) {

            System.out.println("pipeline hashcode" + ctx.pipeline().hashCode() +
                    "TestTestHttpServerHandler hash=" + this.hashCode());

            System.out.println("msg 类型=" + msg.getClass());
            System.out.println("客户端地址" + ctx.channel().remoteAddress());

            // 获取到
            HttpRequest httpRequest = (HttpRequest) msg;
            // 获取uri
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求了 favicon.ico，不做响应");
                return;
            }

            // 回复信息给浏览器[http协议]

            ByteBuf content = Unpooled.copiedBuffer("hello,我是服务器", CharsetUtil.UTF_8);

            // 构造一个http的响应，即httpresponse
            FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);


            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            // 将构建好的 httpResponse返回
            ctx.writeAndFlush(httpResponse);
        }

    }
}
