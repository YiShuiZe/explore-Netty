package com.yishuize.im.client.handler;

import com.yishuize.im.protocol.Packet;
import com.yishuize.im.protocol.PacketCodec;
import com.yishuize.im.protocol.request.LoginRequestPacket;
import com.yishuize.im.protocol.response.LoginResponsePacket;
import com.yishuize.im.protocol.response.MessageResponsePacket;
import com.yishuize.im.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.UUID;

/**
 * <h3>netty-study</h3>
 * <p>
 *     所有业务处理都在这里，耦合太高，需要拆分
 *     if-else 泛滥.
 *     每次发指令数据包都要手动调用编码器编码成 ByteBuf，
 *     对于这类场景的编码优化，我们能想到的办法自然是模块化处理，
 *     不同的逻辑放置到单独的类来处理，最后将这些逻辑串联起来，形成一个完整的逻辑处理链。
 * </p>
 * Created by yang on 20-5-14 下午6:51
 * updated by yang on 20-5-14 下午6:51
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端开始登录");

        // 创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("flash");
        loginRequestPacket.setPassword("pwd");

        // 编码
        //ByteBuf byteBuf = PacketCodec.INSTANCE.encode(, loginRequestPacket);

        ctx.channel().writeAndFlush(loginRequestPacket);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodec.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if (loginResponsePacket.isSuccess()) {
                System.out.println(new Date() + "： 客户端登录成功");
//                LoginUtil.markAsLogin(ctx.channel());
            } else {
                System.out.println(new Date() + "： 客户端登录失败，原因："  + loginResponsePacket.getReason());
            }
        } else if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            System.out.println(new Date() + ": 收到服务端的消息：" + messageResponsePacket.getMessage());
        }
    }
}
