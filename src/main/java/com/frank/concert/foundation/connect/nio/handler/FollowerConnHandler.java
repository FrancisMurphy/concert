package com.frank.concert.foundation.connect.nio.handler;

import lombok.extern.slf4j.Slf4j;

import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

@Slf4j
public class FollowerConnHandler implements Runnable{

    private Selector selector;
    private SocketChannel socketChannel;

    public FollowerConnHandler(Selector selector, SocketChannel socketChannel) {
        this.selector = selector;
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {


    }
}
