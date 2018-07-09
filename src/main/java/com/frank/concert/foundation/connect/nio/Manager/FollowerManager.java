package com.frank.concert.foundation.connect.nio.Manager;

import com.frank.concert.foundation.connect.nio.handler.FollowerConnHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class FollowerManager {

    private static Selector selector;
    private static SocketChannel socketChannel;

    private static SelectionKey sk;

    private final static InetSocketAddress SERVER_ADDRESS = new InetSocketAddress(
            "localhost", 7788);

    private void init() throws IOException {
        initSocket();



    }

    private void initSocket() throws IOException {
        // 打开socket通道
        socketChannel = SocketChannel.open();
        // 设置为非阻塞方式
        socketChannel.configureBlocking(false);
        // 打开选择器
        selector = Selector.open();
        // 注册连接服务端socket动作
        sk = socketChannel.register(selector, SelectionKey.OP_CONNECT);
        // 连接
        socketChannel.connect(SERVER_ADDRESS);
    }

    private void initReactor(){

        FollowerConnHandler connHandler = new FollowerConnHandler(selector,socketChannel);
        sk.attach(connHandler);

    }

}
