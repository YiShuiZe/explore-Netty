package com.yishuize.im.util;

import java.util.UUID;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-16 上午11:11
 * updated by yang on 20-5-16 上午11:11
 */
public class IDUtil {
    public static String randomId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
