package com.yishuize.im.protocol.response;

import com.yishuize.im.protocol.Packet;
import com.yishuize.im.session.Session;
import lombok.Data;

import static com.yishuize.im.protocol.command.Command.GROUP_MESSAGE_RESPONSE;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-16 下午4:07
 * updated by yang on 20-5-16 下午4:07
 */
@Data
public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_RESPONSE;
    }
}
