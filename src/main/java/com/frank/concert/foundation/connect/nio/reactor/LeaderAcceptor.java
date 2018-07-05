package com.frank.concert.foundation.connect.nio.reactor;

import com.frank.concert.foundation.connect.nio.handler.SocketReadHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

@Slf4j
public class LeaderAcceptor implements Runnable {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    public void init(ServerSocketChannel serverSocketChannel,Selector selector){
        this.serverSocketChannel = serverSocketChannel;
        this.selector = selector;
    }

    public void run() {
        try {
            log.debug("-->ready for accept!");
            SocketChannel c = serverSocketChannel.accept();
            if (c != null)
                //调用Handler来处理channel
                new SocketReadHandler(selector, c).init();
        } catch (IOException ex) {
            log.debug("accept stop!" + ex);
        }
    }

}
