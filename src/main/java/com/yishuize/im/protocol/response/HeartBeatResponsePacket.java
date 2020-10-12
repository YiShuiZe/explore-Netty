package com.yishuize.im.protocol.response;

import com.yishuize.im.protocol.Packet;

import static com.yishuize.im.protocol.command.Command.HEARTBEAT_RESPONSE;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-18 下午5:31
 * updated by yang on 20-5-18 下午5:31
 */
public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {

        return HEARTBEAT_RESPONSE;
    }
}
