package com.yishuize.im.server.handler;

import com.yishuize.im.protocol.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

import static com.yishuize.im.protocol.command.Command.*;

/**
 * <h3>netty-study</h3>
 * <p>
 *     压缩 handler - 合并平行 handler
 *
 *     我们的 pipeline 链中，绝大部分都是与指令相关的 handler，
 *     我们把这些 handler 编排在一起，是为了逻辑简洁，
 *     但是随着指令相关的 handler 越来越多，handler 链越来越长，
 *     在事件传播过程中性能损耗会被逐渐放大，因为解码器解出来的每个 Packet 对象都要在每个 handler 上经过一遍。
 *
 *     对这个应用程序来说，每次 decode 出来一个指令对象之后，其实只会在一个指令 handler 上进行处理，
 *     因此，我们其实可以把这么多的指令 handler 压缩为一个 handler。
 *
 *     1. 首先，IMHandler 是无状态的，依然是可以写成一个单例模式的类。
 *     2. 我们定义一个 map，存放指令到各个指令处理器的映射。
 *     3. 每次回调到 IMHandler 的 channelRead0() 方法的时候，我们通过指令找到具体的 handler，然后调用指令 handler 的 channelRead，他内部会做指令类型转换，最终调用到每个指令 handler 的 channelRead0() 方法。
 * </p>
 * Created by yang on 20-5-16 下午4:53
 * updated by yang on 20-5-16 下午4:53
 */
@ChannelHandler.Sharable
public class IMHandler extends SimpleChannelInboundHandler<Packet> {

    public static final IMHandler INSTANCE = new IMHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private IMHandler() {
        handlerMap = new HashMap<>();

        handlerMap.put(MESSAGE_REQUEST, MessageRequestHandler.INSTANCE);
        handlerMap.put(CREATE_GROUP_REQUEST, CreateGroupRequestHandler.INSTANCE);
        handlerMap.put(JOIN_GROUP_REQUEST, JoinGroupRequestHandler.INSTANCE);
        handlerMap.put(QUIT_GROUP_REQUEST, QuitGroupRequestHandler.INSTANCE);
        handlerMap.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestHandler.INSTANCE);
        handlerMap.put(GROUP_MESSAGE_REQUEST, GroupMessageRequestHandler.INSTANCE);
        handlerMap.put(LOGOUT_REQUEST, LogoutRequestHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet) throws Exception {
        handlerMap.get(packet.getCommand()).channelRead(channelHandlerContext, packet);
    }
}
