package com.yishuize.im.protocol.request;

import com.yishuize.im.protocol.Packet;
import lombok.Data;

import static com.yishuize.im.protocol.command.Command.QUIT_GROUP_REQUEST;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-16 下午12:26
 * updated by yang on 20-5-16 下午12:26
 */
@Data
public class QuitGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {

        return QUIT_GROUP_REQUEST;
    }
}
