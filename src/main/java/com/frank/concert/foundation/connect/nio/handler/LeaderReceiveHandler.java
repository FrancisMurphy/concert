package com.frank.concert.foundation.connect.nio.handler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class LeaderReceiveHandler implements Runnable{

    private SocketChannel socketChannel;
    private SelectionKey sk;
    private Selector sel;

    private static final int READING = 0;
    private static final int SENDING  = 1;

    public LeaderReceiveHandler(Selector sel, SocketChannel socketChannel){
        this.socketChannel = socketChannel;
        this.sel = sel;
    }

    public void init()throws IOException {

        socketChannel.configureBlocking(false);
        sk = socketChannel.register(sel,0);

        //将SelectionKey绑定为本Handler 下一步有事件触发时，将调用本类的run方法。
        //参看dispatch(SelectionKey k)
        sk.attach(this);

        //同时将SelectionKey标记为可读，以便读取。
        sk.interestOps(SelectionKey.OP_READ);
        sel.wakeup();
    }

    @Override
    public void run() {

        //read

    }

    /**
     * 处理读取data
     *
     * @throws Exception
     */
    private void readRequest() throws Exception {

        ByteBuffer input = ByteBuffer.allocate(1024);
        input.clear();

    }
}
