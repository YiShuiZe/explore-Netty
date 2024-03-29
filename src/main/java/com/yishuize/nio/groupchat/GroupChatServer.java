package com.yishuize.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-4-7 下午3:48
 * updated by yang on 20-4-7 下午3:48
 */
public class GroupChatServer {
    // 定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    // 构造器
    // 初始化工作
    public GroupChatServer() {
        try {
            // 得到选择器
            selector = Selector.open();
            // ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            // 绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            // 设置非阻塞模式
            listenChannel.configureBlocking(false);
            // 将该listenChannel注册到Selector上
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 监听
    public void listen() {
        try {

            // 循环处理
            while (true) {

                int count = selector.select();
                if (count > 0) {// 有事件处理

                    // 遍历得到selectionKey 集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        // 取出selectionKey
                        SelectionKey key = iterator.next();

                        // 监听到accept
                        if (key.isAcceptable()) {
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            // 将该sc 注册到selector
                            sc.register(selector, SelectionKey.OP_READ);
                            // 提示上线（即连接）
                            System.out.println(sc.getRemoteAddress() + " 上线 ");
                        }
                        if (key.isReadable()) {// 通道发送read事件，即通道是可读的状态
                            // 处理读 （封装方法）
                            readData(key);
                        }

                        // 当前的key删除，防止重复处理
                        iterator.remove();

                    }
                } else {
                    System.out.println("等待");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 发生异常处理
        }
    }

    // 读取客户端消息
    public void readData(SelectionKey key) {

        // 取到关联的SocketChannel
        SocketChannel channel = null;

        try {
            // 得到channel
            channel = (SocketChannel) key.channel();
            // 创建Buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            //根据count的值做处理
            if (count > 0) {
                // 把缓存区的数据转成字符串
                String msg = new String(buffer.array());
                // 输出该消息
                System.out.println("from 客户端： " + msg);

                // 想起他客户端转发消息(去掉自己)， 专门封装一个方法处理
                sendInfoToOtherClients(msg, channel);

            }

        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + " 离线了..");
                // 取消注册
                key.cancel();
                // 关闭通道
                channel.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }

        }
    }

    // 转发消息给其他客户端（通道）
    private void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException {

        System.out.println("服务器转发消息中...");
        System.out.println("服务器转发数据给客户端线程: " + Thread.currentThread().getName());
        // 遍历所有注册到selector上的SocketChannel，并排除自己
        for (SelectionKey key : selector.keys()) {

            // 通过 key 取出对应的SocketChannel
            Channel targetChannel = key.channel();

            // 排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != self) {

                // 转型
                SocketChannel dest = (SocketChannel) targetChannel;
                // 将msg 存储到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                // 将buffer　的数据写入通道
                dest.write(buffer);
            }
        }

    }

    public static void main(String[] args) {

        // 创建服务器对象
        GroupChatServer chatServer = new GroupChatServer();
        chatServer.listen();
    }
}
