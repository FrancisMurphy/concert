package com.frank.concert.foundation.connect.nio.handler;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 异步处理socket通道的建立与selector事件的配置
 */
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
            log.debug("###ready for accept!");
            acceptFollower();
            initReceive();
            log.debug("###Accept a new guy!");
        } catch (IOException ex) {
            log.debug("accept stop!" + ex);
        }
    }

    private void acceptFollower() throws IOException {

        // 接受到此通道套接字的连接。
        // 此方法返回的套接字通道（如果有）将处于阻塞模式。
        socketChannel = serverSocketChannel.accept();
        if (socketChannel != null) {
            // 配置为非阻塞
            socketChannel.configureBlocking(false);
            initReceive();
        }

    }

    private void initReceive() throws IOException {
        //初始化ReceivceHandler
        LeaderReceiveHandler leaderReceiveHandler = new LeaderReceiveHandler(selector, socketChannel);
        leaderReceiveHandler.init();
    }

}
