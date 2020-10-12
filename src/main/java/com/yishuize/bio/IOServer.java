package com.yishuize.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <h3>netty-study</h3>
 * <p>
 *     IO编程
 * </p>
 * Created by yang on 20-4-5 下午1:03
 * updated by yang on 20-4-5 下午1:03
 */
public class IOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);

        System.out.println("服务器启动了");

        new Thread(() -> {// 这个线程也可以不需要，可以直接使用主线程
            while (true) {
                try {
                    System.out.println("线程信息 id = " + Thread.currentThread().getId() +
                            " 名字 = " + Thread.currentThread().getName());
                    // (1) 阻塞方法获取新的连接
                    // 监听，等待客户端连接
                    System.out.println("等待连接......");
                    Socket socket = serverSocket.accept(); // 阻塞的地方
                    System.out.println("连接到一个客户端");

                    // (2) 每一个新的连接都创建一个线程，负责读取数据
                    new Thread(() -> {// 连接线程可以使用线程池优化
                        try {
                            // 输出线程的信息
                            System.out.println("线程信息 id = " + Thread.currentThread().getId() +
                                    " 名字 = " + Thread.currentThread().getName());

                            byte[] data = new byte[1024];
                            InputStream inputStream = socket.getInputStream();

                            // 循环读取客户端发送的数据
                            while (true) {

                                System.out.println("线程信息 id = " + Thread.currentThread().getId() +
                                        " 名字 = " + Thread.currentThread().getName());

                                // (3) 按字节流方式读取数据
                                System.out.println("read......");
                                int read = inputStream.read(data); // 阻塞的地方
                                if (read != -1) {
                                    System.out.println(new String(data, 0, read));// 输出客户端发送的数据
                                } else {
                                    break;
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            System.out.println("关闭与client的连接");
                            try {
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                } catch (IOException e) {
                }

            }
        }).start();
        // 开启多个客户端进行测试，看是否一个线程对应一个客户端的连接
    }
}
