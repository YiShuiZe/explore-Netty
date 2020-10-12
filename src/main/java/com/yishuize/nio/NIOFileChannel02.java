package com.yishuize.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-4-5 下午9:51
 * updated by yang on 20-4-5 下午9:51
 */
public class NIOFileChannel02 {
    public static void main(String[] args) throws Exception {

        // 创建文件的输入流
        File file = new File("");
        FileInputStream fileInputStream = new FileInputStream(file);

        // 通过fileInputStream 获取对应的FileChannel -> 实际类型 FileChannelImpl
        FileChannel fileChannel = fileInputStream.getChannel();

        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        // 将 通道的数据读到缓冲区
        fileChannel.read(byteBuffer);

        // 将byteBuffer 的 字节数据 转成String
        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();
    }
}
