package com.yishuize.netty.dubborpc.customer;

import com.yishuize.netty.dubborpc.netty.NettyClient;
import com.yishuize.netty.dubborpc.publicinterface.HelloService;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-4-13 下午1:54
 * updated by yang on 20-4-13 下午1:54
 */
public class ClientBootstrap {

    //这里定义协议头
    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) throws InterruptedException {

        // 创建一个消费者
        NettyClient customer = new NettyClient();

        // 创建代理对象
        HelloService service = (HelloService) customer.getBean(
                HelloService.class, providerName);

        for (;;) {
            Thread.sleep(2 * 1000);
            // 通过代理对象调用服务提供者的方法（服务）
            String res = service.hello("你好 dubbo~");
            System.out.println("调用的结果 res = " + res);
        }
    }
}
