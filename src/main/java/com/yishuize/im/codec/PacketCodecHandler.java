package com.yishuize.im.codec;

import com.yishuize.im.protocol.Packet;
import com.yishuize.im.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * <h3>netty-study</h3>
 * <p>
 *     压缩 handler - 合并编解码器
 *     使用MessageToMessageCodec
 * </p>
 * Created by yang on 20-5-16 下午4:41
 * updated by yang on 20-5-16 下午4:41
 */

/**
 * 如果一个 handler 要被多个 channel 进行共享，
 * 必须要加上 @ChannelHandler.Sharable 显示地告诉 Netty，
 * 这个 handler 是支持多个 channel 共享的，否则会报错
 */
@ChannelHandler.Sharable
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, Packet> {

    public static final PacketCodecHandler INSTANCE = new PacketCodecHandler();

    private PacketCodecHandler() {}

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, List<Object> list) throws Exception {
        // 在 encode() 方法里面，我们调用了 channel 的 内存分配器手工分配了 ByteBuf。
        ByteBuf byteBuf = channelHandlerContext.channel().alloc().ioBuffer();
        PacketCodec.INSTANCE.encode(byteBuf, packet);
        list.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(PacketCodec.INSTANCE.decode(byteBuf));
    }
}
