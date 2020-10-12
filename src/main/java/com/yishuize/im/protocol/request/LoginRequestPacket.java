package com.yishuize.im.protocol.request;

import com.yishuize.im.protocol.Packet;
import com.yishuize.im.protocol.command.Command;
import lombok.Data;

/**
 * <h3>netty-study</h3>
 * <p>
 *     登录请求数据包
 * </p>
 * Created by yang on 20-5-5 下午9:43
 * updated by yang on 20-5-5 下午9:43
 */
@Data
public class LoginRequestPacket extends Packet {
    private String userId;
    private String username;
    private String password;
    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
