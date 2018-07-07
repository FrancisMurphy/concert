package com.frank.concert.foundation.connect.nio.handler;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

@Slf4j
public class LeaderAcceptHandler implements Runnable {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    private SocketChannel socketChannel;

    public LeaderAcceptHandler(ServerSocketChannel serverSocketChannel, Selector selector) {
        this.serverSocketChannel = serverSocketChannel;
        this.selector = selector;
    }

    public void run() {
        try {
            log.debug("-->ready for accept!");
            acceptFollower();
            initReceive();
        } catch (IOException ex) {
            log.debug("accept stop!" + ex);
        }
    }

    private void acceptFollower() throws IOException {

        // 接受到此通道套接字的连接。
        // 此方法返回的套接字通道（如果有）将处于阻塞模式。
        socketChannel = serverSocketChannel.accept();
        if (socketChannel != null){
            // 配置为非阻塞
            socketChannel.configureBlocking(false);
            // 注册到selector，等待连接
            socketChannel.register(selector, SelectionKey.OP_READ);
        }

    }

    private void initReceive() {

    }

}
