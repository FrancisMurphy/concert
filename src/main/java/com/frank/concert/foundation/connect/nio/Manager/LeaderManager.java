package com.frank.concert.foundation.connect.nio.Manager;

import com.frank.concert.foundation.connect.nio.reactor.LeaderAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class LeaderManager {

    private ServerSocketChannel serverSocket;
    private Selector selector;

    public void init(){
        try {
            initSocket();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initSocket() throws IOException {
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(7777),1024);
        // 设置为非阻塞模式, 这个非常重要
        serverSocket.configureBlocking(false);
    }

    private void initSelector() throws IOException {
        selector = Selector.open();
        //向selector注册该channel
        SelectionKey sk =serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        sk.attach(new LeaderAcceptor());
    }

}
