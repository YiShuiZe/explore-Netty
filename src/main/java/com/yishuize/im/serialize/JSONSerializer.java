package com.yishuize.im.serialize;

import com.alibaba.fastjson.JSON;

/**
 * <h3>netty-study</h3>
 * <p>
 *     json 序列化
 * </p>
 * Created by yang on 20-5-5 下午9:58
 * updated by yang on 20-5-5 下午9:58
 */
public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
