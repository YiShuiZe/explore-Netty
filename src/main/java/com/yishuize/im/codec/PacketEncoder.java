package com.yishuize.im.codec;

import com.yishuize.im.protocol.Packet;
import com.yishuize.im.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * <h3>netty-study</h3>
 * <p>
 *     我们处理每一种指令完成之后的逻辑是类似的，都需要进行编码，
 *     然后调用 writeAndFlush() 将数据写到客户端，这个编码的过程其实也是重复的逻辑，
 *     而且在编码的过程中，我们还需要手动去创建一个 ByteBuf。
 *
 *     Netty 提供了一个特殊的 channelHandler 来专门处理编码逻辑，
 *     我们不需要每一次将响应写到对端的时候调用一次编码逻辑进行编码，也不需要自行创建 ByteBuf。
 *
 *     我们只需要实现 encode() 方法，我们注意到，在这个方法里面，
 *     第二个参数是 Java 对象，而第三个参数是 ByteBuf 对象，
 *     我们在这个方法里面要做的事情就是把 Java 对象里面的字段写到 ByteBuf，
 *     我们不再需要自行去分配 ByteBuf，因此，大家注意到，PacketCodeC 的 encode() 方法的定义也改了。
 *
 *     当我们向 pipeline 中添加了这个编码器之后，我们在指令处理完毕之后就只需要 writeAndFlush java 对象即可
 * </p>
 * Created by yang on 20-5-15 下午12:38
 * updated by yang on 20-5-15 下午12:38
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        PacketCodec.INSTANCE.encode(byteBuf, packet);
    }
}
