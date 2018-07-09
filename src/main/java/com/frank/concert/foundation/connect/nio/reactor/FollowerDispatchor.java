package com.frank.concert.foundation.connect.nio.reactor;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class FollowerDispatchor implements Runnable{

    private ServerSocketChannel serverSocket;
    private Selector selector;

    //负责管理leander所有线程的线程池容器
    private static ExecutorService handlerThreadPool;

    public FollowerDispatchor(ServerSocketChannel serverSocket, Selector selector) {
        this.serverSocket = serverSocket;
        this.selector = selector;
    }

    public void init() {

        handlerThreadPool = Executors.newCachedThreadPool();

    }

    public void run() { // normally in a new Thread
        try {
            while (true) {
                selector.select();
                Set selected = selector.selectedKeys();
                Iterator iterator = selected.iterator();
                //Selector如果发现channel有OP_ACCEPT或READ事件发生，下列遍历就会进行。
                while (iterator.hasNext()){
                    //来一个事件 第一次触发一个accepter线程
                    //以后触发SocketReadHandler
                    dispatch((SelectionKey) (iterator.next()));
                    iterator.remove();
                }
                selected.clear();
            }
        } catch (IOException ex) {
            log.debug("reactor stop!" + ex);
        }
    }

    //运行对应的handler
    private void dispatch(SelectionKey selectionKey) {
        Runnable handler = (Runnable) (selectionKey.attachment());
        handlerThreadPool.execute(handler);
        selectionKey.cancel();

    }

}
