package com.frank.concert.foundation.connect.nio.reactor;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class LeaderReactor implements Runnable {

    private ServerSocketChannel serverSocket;
    private Selector selector;

    public LeaderReactor(ServerSocketChannel serverSocket, Selector selector) {
        this.serverSocket = serverSocket;
        this.selector = selector;
    }

    public void run() { // normally in a new Thread
        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set selected = selector.selectedKeys();
                Iterator it = selected.iterator();
                //Selector如果发现channel有OP_ACCEPT或READ事件发生，下列遍历就会进行。
                while (it.hasNext())
                    //来一个事件 第一次触发一个accepter线程
                    //以后触发SocketReadHandler
                    dispatch((SelectionKey) (it.next()));
                selected.clear();
            }
        } catch (IOException ex) {
            log.debug("reactor stop!" + ex);
        }
    }

    //运行Acceptor或SocketReadHandler
    private void dispatch(SelectionKey k) {
        Runnable r = (Runnable) (k.attachment());
        if (r != null) {
        // r.run();
        }
    }

}
