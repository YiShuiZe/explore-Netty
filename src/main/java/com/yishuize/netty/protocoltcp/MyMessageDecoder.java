package com.yishuize.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-4-11 下午2:28
 * updated by yang on 20-4-11 下午2:28
 */
public class MyMessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext,
                          ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("MyMessageDecoder decode 被调用");

        // 需要将得到的二进制字节码 ->  MessageProtocol 数据包（对象）
        int length = byteBuf.readInt();
        byte[] content = new byte[length];
        byteBuf.readBytes(content);

        // 封装成 MessageProtocol 对象，放入list，传递给下一个handler业务处理
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(length);
        messageProtocol.setContent(content);

        list.add(messageProtocol);
    }
}
