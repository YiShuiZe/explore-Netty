package com.yishuize.nio;

import java.nio.IntBuffer;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-4-5 下午7:48
 * updated by yang on 20-4-5 下午7:48
 */
public class BasicBuffer {
    public static void main(String[] args) {

        // 举例说明Buffer 的使用
        // 创建一个Buffer，大小为5，即可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        // 向Buffer中存放数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }

        // 如何从buffer中读取数据
        // 将buffer转换，读写切换
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());//get好比迭代器的next
        }

    }
}
