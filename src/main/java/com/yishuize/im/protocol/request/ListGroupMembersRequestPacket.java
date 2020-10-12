package com.yishuize.im.protocol.request;

import com.yishuize.im.protocol.Packet;
import lombok.Data;

import static com.yishuize.im.protocol.command.Command.LIST_GROUP_MEMBERS_REQUEST;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-16 下午12:28
 * updated by yang on 20-5-16 下午12:28
 */
@Data
public class ListGroupMembersRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_REQUEST;
    }
}
