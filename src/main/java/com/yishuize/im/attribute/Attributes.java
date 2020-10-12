package com.yishuize.im.attribute;

import com.yishuize.im.session.Session;
import io.netty.util.AttributeKey;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-14 下午7:57
 * updated by yang on 20-5-14 下午7:57
 */
public interface Attributes {
    // 是否成功的标志位
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
