package com.yishuize.im.server.handler;

import com.yishuize.im.protocol.request.LoginRequestPacket;
import com.yishuize.im.protocol.response.LoginResponsePacket;
import com.yishuize.im.session.Session;
import com.yishuize.im.util.LoginUtil;
import com.yishuize.im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

/**
 * <h3>netty-study</h3>
 * <p>
 *     SimpleChannelInboundHandler
 *     从字面意思也可以看到，使用它非常简单，我们在继承这个类的时候，给他传递一个泛型参数
 *     然后在 channelRead0() 方法里面，
 *     我们不用再通过 if 逻辑来判断当前对象是否是本 handler 可以处理的对象，也不用强转，
 *     不用往下传递本 handler 处理不了的对象，这一切都已经交给父类 SimpleChannelInboundHandler 来实现了，
 *     我们只需要专注于我们要处理的业务逻辑即可。
 * </p>
 * Created by yang on 20-5-15 下午12:46
 * updated by yang on 20-5-15 下午12:46
 */
// 1. 加上注解标识，表明该 handler 是可以多个 channel 共享的
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    // 2. 构造单例
    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    protected LoginRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) throws Exception {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserName(loginRequestPacket.getUsername());

        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            String userId = randomUserId();
            loginResponsePacket.setUserId(userId);
            System.out.println("[" + loginRequestPacket.getUsername() + "]登录成功");
            SessionUtil.bindSession(new Session(userId, loginRequestPacket.getUsername()), channelHandlerContext.channel());
        } else {
            // 校验失败
            loginResponsePacket.setReason("账号密码校验失败!");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() + ": 登录失败!");
        }

        // 登录响应
        channelHandlerContext.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

    private static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
