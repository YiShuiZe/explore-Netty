package com.yishuize.im.codec;

import com.yishuize.im.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * <h3>netty-study</h3>
 * <p>
 *     基于长度域拆包器 LengthFieldBasedFrameDecoder
 *     第一个参数指的是数据包的最大长度，
 *     第二个参数指的是长度域的偏移量，
 *     第三个参数指的是长度域的长度，
 *     这样一个拆包器写好之后，只需要在 pipeline 的最前面加上这个拆包器。
 *
 *     这样，在后续 PacketDecoder 进行 decode 操作的时候，ByteBuf 就是一个完整的自定义协议数据包。
 *
 *     拆包器的作用就是根据我们的自定义协议，
 *     把数据拼装成一个个符合我们自定义数据包大小的 ByteBuf，
 *     然后送到我们的自定义协议解码器去解码。
 * </p>
 * Created by yang on 20-5-15 下午8:16
 * updated by yang on 20-5-15 下午8:16
 */
public class Spliter extends LengthFieldBasedFrameDecoder {

    private static final int LENGTH_FIELD_OFFSET = 7;
    private static final int LENGTH_FIELD_LENGTH = 4;

    public Spliter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        /**
         * 为什么可以在 decode() 方法写这段逻辑？
         * 是因为这里的 decode() 方法中，第二个参数 in，每次传递进来的时候，均为一个数据包的开头
         */
        /**
         * 拒绝非本协议连接
         *
         * 设计魔数的原因是为了尽早屏蔽非本协议的客户端，通常在第一个 handler 处理这段逻辑。
         * 我们接下来的做法是每个客户端发过来的数据包都做一次快速判断，
         * 判断当前发来的数据包是否是满足我的自定义协议，
         * 我们只需要继承自 LengthFieldBasedFrameDecoder 的 decode() 方法，
         * 然后在 decode 之前判断前四个字节是否是等于我们定义的魔数 0x12345678
         */
        /**
         * 最后，
         * ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
         *  替换为
         * ch.pipeline().addLast(new Spliter());
         */
        // 屏蔽非本协议的客户端
        if (in.getInt(in.readerIndex()) != PacketCodec.MAGIC_NUMBER) {
            ctx.channel().close();
            return null;
        }

        return super.decode(ctx, in);
    }
}
