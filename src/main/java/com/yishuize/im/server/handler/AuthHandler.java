package com.yishuize.im.server.handler;

import com.yishuize.im.util.LoginUtil;
import com.yishuize.im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * <h3>netty-study</h3>
 * <p>
 *     在 MessageRequestHandler 之前插入了一个 AuthHandler，
 *     因此 MessageRequestHandler 以及后续所有指令相关的 handler（后面小节会逐个添加）的处理
 *     都会经过 AuthHandler 的一层过滤，只要在 AuthHandler 里面处理掉身份认证相关的逻辑，
 *     后续所有的 handler 都不用操心身份认证这个逻辑
 *
 *     AuthHandler 继承自 ChannelInboundHandlerAdapter，覆盖了 channelRead() 方法，
 *     表明他可以处理所有类型的数据。
 *
 *     如果有很多业务逻辑的 handler 都要进行某些相同的操作，我们完全可以抽取出一个 handler 来单独处理
 * 如果某一个独立的逻辑在执行几次之后（这里是一次）不需要再执行了，
 * 那么我们可以通过 ChannelHandler 的热插拔机制来实现动态删除逻辑，应用程序性能处理更为高效
 * </p>
 * Created by yang on 20-5-15 下午8:49
 * updated by yang on 20-5-15 下午8:49
 */
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {

    public static final AuthHandler INSTANCE = new AuthHandler();

    private AuthHandler() {}

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            // 强制关闭连接（实际生产环境可能逻辑要复杂些，这里我们的重心在于学习 Netty，这里就粗暴些）
            ctx.channel().close();
        } else {
            /**
             * 如果客户端已经登录成功了，那么在每次处理客户端数据之前，我们都要经历这么一段逻辑
             */
            // 如果已经经过权限认证，那么就直接调用 pipeline 的 remove() 方法删除自身
            // pipeline动态删除AuthHandler
            ctx.pipeline().remove(this);
            // 就把读到的数据向下传递，传递给后续指令处理器
            super.channelRead(ctx, msg);
        }
    }
//
//    @Override
//    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//        if (LoginUtil.hasLogin(ctx.channel())) {
//            System.out.println("当前连接登录验证完毕，无需再次验证，AuthHandler被移除");
//        } else {
//            System.out.println("无登录验证，强制关闭连接！");
//        }
//    }
}
