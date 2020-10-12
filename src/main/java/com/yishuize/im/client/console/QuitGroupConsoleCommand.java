package com.yishuize.im.client.console;

import com.yishuize.im.protocol.request.QuitGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-16 下午2:51
 * updated by yang on 20-5-16 下午2:51
 */
public class QuitGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        QuitGroupRequestPacket quitGroupRequestPacket = new QuitGroupRequestPacket();

        System.out.print("输入 groupId，退出群聊：");
        String groupId = scanner.next();

        quitGroupRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(quitGroupRequestPacket);
    }
}
