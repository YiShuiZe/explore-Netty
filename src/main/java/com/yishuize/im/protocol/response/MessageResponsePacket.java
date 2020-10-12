package com.yishuize.im.protocol.response;

import com.yishuize.im.protocol.Packet;
import com.yishuize.im.protocol.command.Command;
import lombok.Data;


/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-14 下午7:55
 * updated by yang on 20-5-14 下午7:55
 */
@Data
public class MessageResponsePacket extends Packet {

    private String fromUserId;

    private String fromUserName;

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
