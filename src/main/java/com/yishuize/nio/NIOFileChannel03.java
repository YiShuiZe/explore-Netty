package com.yishuize.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-4-5 下午10:10
 * updated by yang on 20-4-5 下午10:10
 */
public class NIOFileChannel03 {
    public static void main(String[] args) throws Exception {

        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel fileInputStreamChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while (true) {// 循环读取

            // 这里有一个重要的操作，一定不要忘了
            /**
             * public final Buffer clear() {
             *     position = 0;
             *     limit = capacity;
             *     mark = -1;
             *     return this;
             * }
             */
            byteBuffer.clear();
            int read = fileInputStreamChannel.read(byteBuffer);
            if (read == -1) {//表示读完
                break;
            }
            // 将buffer 中的数据写入到 fileOutputStreamChannel --> 2.txt
            byteBuffer.flip();
            fileOutputStreamChannel.write(byteBuffer);
        }

        // 关闭相关的流
        fileInputStream.close();
        fileOutputStream.close();
    }
}
