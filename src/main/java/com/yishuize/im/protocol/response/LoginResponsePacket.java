package com.yishuize.im.protocol.response;

import com.yishuize.im.protocol.Packet;
import com.yishuize.im.protocol.command.Command;
import lombok.Data;


/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-14 下午7:18
 * updated by yang on 20-5-14 下午7:18
 */
@Data
public class LoginResponsePacket extends Packet {

    private String userId;

    private String userName;

    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
