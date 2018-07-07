package com.frank.concert.foundation.connect.nio.Manager;

import com.frank.concert.foundation.connect.nio.handler.LeaderAcceptHandler;
import com.frank.concert.foundation.connect.nio.reactor.LeaderDispatchor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LeaderManager {

    private static ServerSocketChannel serverSocketChannel;
    private static Selector selector;

    //负责管理leander所有线程的线程池容器
    private static ExecutorService leaderThreadPool;

    //Reactor
    private static LeaderDispatchor leaderDispatchor;
    private static LeaderAcceptHandler leaderAcceptHandler;

    public void init() {
        try {
            //初始化Manager所需的线程池，此线程池负责管理所有Leader涉及的所有线程
            leaderThreadPool = Executors.newCachedThreadPool();

            initSocket();
            initReactor();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initSocket() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(7777), 1024);
        // 设置为非阻塞模式, 这个非常重要
        serverSocketChannel.configureBlocking(false);
    }

    /**
     * 初始化selector并且启动监听的主Reactor
     *
     * @throws IOException
     */
    private void initReactor() throws IOException {
        selector = Selector.open();
        //向selector注册该channel
        SelectionKey sk = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //初始化accepthandler
        leaderAcceptHandler = new LeaderAcceptHandler(serverSocketChannel, selector);
        sk.attach(leaderAcceptHandler);

        //启动reactor
        leaderDispatchor = new LeaderDispatchor(serverSocketChannel, selector);
        leaderDispatchor.init();
        leaderThreadPool.execute(leaderDispatchor);
    }


}
