package com.yishuize.im.protocol.request;

import com.yishuize.im.protocol.Packet;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.yishuize.im.protocol.command.Command.GROUP_MESSAGE_REQUEST;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-16 下午4:04
 * updated by yang on 20-5-16 下午4:04
 */
@Data
@NoArgsConstructor
public class GroupMessageRequestPacket extends Packet {

    private String toGroupId;

    private String message;

    public GroupMessageRequestPacket(String toGroupId, String message) {
        this.toGroupId = toGroupId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_REQUEST;
    }
}
