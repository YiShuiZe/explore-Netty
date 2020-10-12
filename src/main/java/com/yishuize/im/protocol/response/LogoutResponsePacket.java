package com.yishuize.im.protocol.response;

import com.yishuize.im.protocol.Packet;
import lombok.Data;

import static com.yishuize.im.protocol.command.Command.LOGOUT_RESPONSE;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-16 上午10:50
 * updated by yang on 20-5-16 上午10:50
 */
@Data
public class LogoutResponsePacket extends Packet {

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {

        return LOGOUT_RESPONSE;
    }
}
