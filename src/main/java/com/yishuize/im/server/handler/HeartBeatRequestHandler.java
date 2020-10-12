package com.yishuize.im.server.handler;

import com.yishuize.im.protocol.request.HeartBeatRequestPacket;
import com.yishuize.im.protocol.response.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.*;

/**
 * <h3>netty-study</h3>
 * <p>
 *     为了排除是否是因为服务端在非假死状态下确实没有发送数据，服务端也要定期发送心跳给客户端。
 *     而其实在前面我们已经实现了客户端向服务端定期发送心跳，
 *     服务端这边其实只要在收到心跳之后回复客户端，给客户端发送一个心跳响应包即可。
 *     如果在一段时间之内客户端没有收到服务端发来的数据，也可以判定这条连接为假死状态。
 * </p>
 * Created by yang on 20-5-18 下午6:30
 * updated by yang on 20-5-18 下午6:30
 */
@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {

    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

    private HeartBeatRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HeartBeatRequestPacket heartBeatRequestPacket) throws Exception {
        channelHandlerContext.writeAndFlush(new HeartBeatResponsePacket());
    }
}
