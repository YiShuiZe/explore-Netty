package com.yishuize.im.server.handler;

import com.yishuize.im.protocol.Packet;
import com.yishuize.im.protocol.PacketCodec;
import com.yishuize.im.protocol.request.LoginRequestPacket;
import com.yishuize.im.protocol.request.MessageRequestPacket;
import com.yishuize.im.protocol.response.LoginResponsePacket;
import com.yishuize.im.protocol.response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * <h3>netty-study</h3>
 * <p>
 *     所有的业务处理都在这里，耦合太高，需要拆分。
 *     if-else 泛滥.
 *     每次发指令数据包都要手动调用编码器编码成 ByteBuf，
 *     对于这类场景的编码优化，我们能想到的办法自然是模块化处理，
 *     不同的逻辑放置到单独的类来处理，最后将这些逻辑串联起来，形成一个完整的逻辑处理链。
 * </p>
 * Created by yang on 20-5-14 下午6:52
 * updated by yang on 20-5-14 下午6:52
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf requestByteBuf = (ByteBuf) msg;

        // 解码
        Packet packet = PacketCodec.INSTANCE.decode(requestByteBuf);

        // 判断是否是登录请求数据包
        if (packet instanceof LoginRequestPacket) {
            System.out.println(new Date() + ": 收到客户端登录请求......");

            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());
            // 登录检验
            if (valid(loginRequestPacket)) {
                // 校验成功
                loginResponsePacket.setSuccess(true);
                System.out.println(new Date() + ": 登录成功");
            } else {
                // 校验失败
                loginResponsePacket.setReason("账号密码校验失败");
                loginResponsePacket.setSuccess(false);
                System.out.println(new Date() + ": 登录失败");
            }

            // 编码
            ByteBuf responseByteBuf = PacketCodec.INSTANCE.encode(requestByteBuf, loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        } else if (packet instanceof MessageRequestPacket) {
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            System.out.println(new Date() + ": 收到客户端消息： " + messageRequestPacket.getMessage());

            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("服务器回复【" + messageRequestPacket.getMessage() + "】");
            ByteBuf responseByteBuf = PacketCodec.INSTANCE.encode(requestByteBuf, messageResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
