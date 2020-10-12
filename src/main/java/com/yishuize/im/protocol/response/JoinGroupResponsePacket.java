package com.yishuize.im.protocol.response;

import com.yishuize.im.protocol.Packet;
import lombok.Data;

import static com.yishuize.im.protocol.command.Command.JOIN_GROUP_RESPONSE;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-16 下午12:29
 * updated by yang on 20-5-16 下午12:29
 */
@Data
public class JoinGroupResponsePacket extends Packet {

    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_RESPONSE;
    }
}
