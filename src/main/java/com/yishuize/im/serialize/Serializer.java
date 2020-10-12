package com.yishuize.im.serialize;

/**
 * <h3>netty-study</h3>
 * <p>
 *     序列化接口
 * </p>
 * Created by yang on 20-5-5 下午9:46
 * updated by yang on 20-5-5 下午9:46
 */
public interface Serializer {

    // json序列化
    byte JSON_SERIALIZER = 1;

    Serializer DEFAULT = new JSONSerializer();

    // 序列化算法，获取具体的序列化算法标识
    byte getSerializerAlgorithm();

    // java 对象转换成二进制 字节数组
    byte[] serialize(Object object);

    // 二进制 字节数组 转换成 java 对象
    <T> T deserialize(Class<T> clazz, byte[] bytes);

}
