package com.yishuize.im.protocol.request;

import com.yishuize.im.protocol.Packet;

import static com.yishuize.im.protocol.command.Command.HEARTBEAT_REQUEST;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-18 下午5:30
 * updated by yang on 20-5-18 下午5:30
 */
public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {

        return HEARTBEAT_REQUEST;
    }
}
