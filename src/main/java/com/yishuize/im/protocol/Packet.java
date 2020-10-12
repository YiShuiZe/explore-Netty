package com.yishuize.im.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * <h3>netty-study</h3>
 * <p>
 *     通信协议
 * </p>
 * Created by yang on 20-5-5 下午9:35
 * updated by yang on 20-5-5 下午9:35
 */
@Data
public abstract class Packet {
    // 协议版本
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;
    // 指令
    @JSONField(serialize = false)
    public abstract Byte getCommand();
}
