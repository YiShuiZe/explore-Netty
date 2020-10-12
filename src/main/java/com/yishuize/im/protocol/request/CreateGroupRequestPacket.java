package com.yishuize.im.protocol.request;

import com.yishuize.im.protocol.Packet;
import lombok.Data;

import java.util.List;

import static com.yishuize.im.protocol.command.Command.CREATE_GROUP_REQUEST;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-16 上午10:48
 * updated by yang on 20-5-16 上午10:48
 */
@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;

    @Override
    public Byte getCommand() {

        return CREATE_GROUP_REQUEST;
    }
}
