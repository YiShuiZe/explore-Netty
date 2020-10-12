package com.yishuize.netty.codec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * <h3>netty-study</h3>
 * <p>
 *     说明：
 *     1. 我们自定义一个 Handler 需要继承netty 规定好的某个HandlerAdapter(规范)
 *     2. 这时我们自定义一个 Handler,才能称为一个handler
 * </p>
 * Created by yang on 20-4-8 上午11:20
 * updated by yang on 20-4-8 上午11:20
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    // 读取事件的事件（这里可以读取客户端发送的消息）
    // 1. ChannelHandlerContext ctx:上下文对象，含有 管道pipeline，通道channel，地址
    // 2. Object msg：客户端发送的数据 默认Object
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 比如这里我们有一个非常耗时长的业务 -> 异步执行 -> 提交该channel 对应的
        // NIOEventLoop  的 taskQueue中

        // 解决方案1 用户程序自定义的普通任务

        ctx.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(5 * 1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~(>^ω^<)喵2", CharsetUtil.UTF_8));
                System.out.println("channel code=" + ctx.channel().hashCode());
            } catch (Exception ex) {
                System.out.println("发生异常" + ex.getMessage());
            }
        });

        ctx.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(5 * 1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~(>^ω^<)喵3", CharsetUtil.UTF_8));
                System.out.println("channel code=" + ctx.channel().hashCode());
            } catch (Exception ex) {
                System.out.println("发生异常" + ex.getMessage());
            }
        });

        // 用户自定义定时任务  ->  该任务是提交到 scheduleTaskQueue中的
        ctx.channel().eventLoop().schedule(() -> {
            try {
                Thread.sleep(5 * 1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~(>^ω^<)喵4", CharsetUtil.UTF_8));
                System.out.println("channel code=" + ctx.channel().hashCode());
            } catch (Exception ex) {
                System.out.println("发生异常" + ex.getMessage());
            }
        }, 5, TimeUnit.SECONDS);



//        System.out.println("服务器读取线程 " + Thread.currentThread().getName());
//
//        System.out.println("server ctx = " + ctx);
//        // 将 msg 转成一个 ByteBuf
//        // ByteBuf 是 Netty 提供的，不是NIO 的 ByteBuffer
//        ByteBuf buf = (ByteBuf) msg;
//        System.out.println("客户端发送消息是:" + buf.toString(CharsetUtil.UTF_8));
//        System.out.println("客户端地址：" + ctx.channel().remoteAddress());
    }

    // 数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        // writeAndFlush 是 write + flush
        // 将数据写入到缓存，并刷新
        // 一般来讲，我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello，客户端~", CharsetUtil.UTF_8));
    }

    // 处理异常，一般需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
