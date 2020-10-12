package com.yishuize.im.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-16 上午10:36
 * updated by yang on 20-5-16 上午10:36
 */
public interface ConsoleCommand {
    void exec(Scanner scanner, Channel channel);
}
