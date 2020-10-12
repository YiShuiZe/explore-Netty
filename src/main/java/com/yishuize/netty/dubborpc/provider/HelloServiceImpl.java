package com.yishuize.netty.dubborpc.provider;

import com.yishuize.netty.dubborpc.publicinterface.HelloService;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-4-12 下午11:15
 * updated by yang on 20-4-12 下午11:15
 */
public class HelloServiceImpl implements HelloService {

    private static int count = 0;

    // 当有消费方调用该方法时，就返回一个结果
    @Override
    public String hello(String mes) {
        System.out.println("收到客户端消息=" + mes);
        // 根据mes 返回不同的结果
        if (mes != null) {
            return "你好客户端，我已经收到你的消息 [" + mes + "] 第" + (++count) + "次";
        } else {
            return "你好客户端，我已经收到你的消息 ";
        }
    }
}
