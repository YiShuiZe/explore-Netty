package com.yishuize.im.protocol.response;

import com.yishuize.im.protocol.Packet;
import lombok.Data;

import java.util.List;

import static com.yishuize.im.protocol.command.Command.CREATE_GROUP_RESPONSE;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-16 上午10:52
 * updated by yang on 20-5-16 上午10:52
 */
@Data
public class CreateGroupResponsePacket extends Packet {

    private boolean success;

    private String groupId;

    private List<String> userNameList;

    @Override
    public Byte getCommand() {

        return CREATE_GROUP_RESPONSE;
    }
}
