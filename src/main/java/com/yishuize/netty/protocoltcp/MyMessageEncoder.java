package com.yishuize.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-4-11 下午2:25
 * updated by yang on 20-4-11 下午2:25
 */
public class MyMessageEncoder extends MessageToByteEncoder<MessageProtocol> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext,
                          MessageProtocol messageProtocol,
                          ByteBuf byteBuf) throws Exception {
        System.out.println("MyMessageEncoder encode 方法被调用");
        byteBuf.writeInt(messageProtocol.getLen());
        byteBuf.writeBytes(messageProtocol.getContent());
    }
}
