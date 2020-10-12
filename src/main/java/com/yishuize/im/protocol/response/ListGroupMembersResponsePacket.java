package com.yishuize.im.protocol.response;

import com.yishuize.im.protocol.Packet;
import com.yishuize.im.session.Session;
import lombok.Data;

import java.util.List;

import static com.yishuize.im.protocol.command.Command.LIST_GROUP_MEMBERS_RESPONSE;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-16 下午12:32
 * updated by yang on 20-5-16 下午12:32
 */
@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {

        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
