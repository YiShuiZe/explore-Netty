package com.yishuize.im.protocol.request;

import com.yishuize.im.protocol.Packet;
import lombok.Data;

import static com.yishuize.im.protocol.command.Command.JOIN_GROUP_REQUEST;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-16 下午12:20
 * updated by yang on 20-5-16 下午12:20
 */
@Data
public class JoinGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {

        return JOIN_GROUP_REQUEST;
    }
}
