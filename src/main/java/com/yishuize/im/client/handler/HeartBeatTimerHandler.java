package com.yishuize.im.client.handler;

import com.yishuize.im.protocol.request.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * <h3>netty-study</h3>
 * <p>
 *     服务端在一段时间内没有收到客户端的数据，
 *     这个现象产生的原因可以分为以下两种：
 *     1. 连接假死。
 *     2. 非假死状态下确实没有发送数据。
 *     我们只需要排除掉第二种可能性，那么连接自然就是假死的。
 *     要排查第二种情况，我们可以在客户端定期发送数据到服务端，
 *     通常这个数据包称为心跳数据包，
 *     接下来，我们定义一个 handler，定期发送心跳给服务端
 *
 * </p>
 * Created by yang on 20-5-18 下午5:33
 * updated by yang on 20-5-18 下午5:33
 */
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

    private static final int HEARTBEAT_INIERVAL = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        scheduleSendHeartBeat(ctx);

        super.channelActive(ctx);
    }

    private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {
        // ctx.executor() 返回的是当前的 channel 绑定的 NIO 线程
        // NIO 线程有一个方法，schedule()，类似 jdk 的延时任务机制，可以隔一段时间之后执行一个任务。
        ctx.executor().schedule(() -> {

            if (ctx.channel().isActive()) {
                ctx.writeAndFlush(new HeartBeatRequestPacket());
                scheduleSendHeartBeat(ctx);
            }
        }, HEARTBEAT_INIERVAL, TimeUnit.SECONDS);
    }
}
