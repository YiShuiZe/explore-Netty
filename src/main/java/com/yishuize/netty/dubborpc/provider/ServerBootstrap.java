package com.yishuize.netty.dubborpc.provider;

import com.yishuize.netty.dubborpc.netty.NettyServer;

/**
 * <h3>netty-study</h3>
 * <p>
 *     ServerBootStrap 会启动一个服务提供者，就是NettyServer
 * </p>
 * Created by yang on 20-4-13 上午10:36
 * updated by yang on 20-4-13 上午10:36
 */
public class ServerBootstrap {
    public static void main(String[] args) {

        //
        NettyServer.startServer("127.0.0.1", 7000);
    }
}
