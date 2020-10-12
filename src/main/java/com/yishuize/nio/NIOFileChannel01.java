package com.yishuize.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-4-5 下午8:54
 * updated by yang on 20-4-5 下午8:54
 */
public class NIOFileChannel01 {
    public static void main(String[] args) throws Exception {

        String str = "hello, FileChannel";
        // 创建一个输出流 -> channel
        FileOutputStream fileOutputStream = new FileOutputStream("");

        // 通过 fileOutputStream 获取 对应的 FileChannel
        // 这个fileChannel 真实 类型是 FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();

        // 创建一个缓冲区 ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 将 str 放入 byteBuffer
        byteBuffer.put(str.getBytes());

        // 对buteBuffer 进行flip
        byteBuffer.flip();

        // 将buteBuffer 数据写入到fileChannel
        fileChannel.write(byteBuffer);
        fileOutputStream.close();

    }
}
