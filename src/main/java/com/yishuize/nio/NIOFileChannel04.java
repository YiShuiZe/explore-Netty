package com.yishuize.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-4-5 下午10:36
 * updated by yang on 20-4-5 下午10:36
 */
public class NIOFileChannel04 {
    public static void main(String[] args) throws Exception {

        // 创建相关流
        FileInputStream fileInputStream = new FileInputStream("a.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("b.jpg");

        // 获取各个流对应的Channel
        FileChannel sourceCh = fileInputStream.getChannel();
        FileChannel destCh = fileOutputStream.getChannel();

        // 使用transferForm完成拷贝
        destCh.transferFrom(sourceCh, 0, sourceCh.size());

        // 关闭相关相关通道和流
        sourceCh.close();
        destCh.close();
        fileInputStream.close();
        fileOutputStream.close();

    }
}
