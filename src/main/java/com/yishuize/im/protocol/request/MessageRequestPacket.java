package com.yishuize.im.protocol.request;

import com.yishuize.im.protocol.Packet;
import com.yishuize.im.protocol.command.Command;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-14 下午7:53
 * updated by yang on 20-5-14 下午7:53
 */
@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {

    private String toUserId;
    private String message;

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
