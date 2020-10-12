package com.yishuize.netty.dubborpc.publicinterface;

/**
 * <h3>netty-study</h3>
 * <p>
 *     这个是接口，是服务提供方 和 服务消费方 都需要的
 * </p>
 * Created by yang on 20-4-12 下午11:14
 * updated by yang on 20-4-12 下午11:14
 */
public interface HelloService {

    String hello(String mes);
}
