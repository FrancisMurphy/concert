package com.frank.concert.foundation.connect.thread;

import lombok.extern.slf4j.Slf4j;

import java.net.Socket;

@Slf4j
public class FollowerSocketThread extends BaseSocketThread {

    public FollowerSocketThread(Socket socket){
        this.socket = socket;
    }

    public void init(){

        log.debug("###Init SocketThread [FollowerName:{} LeaderName:{}]...",
                socket.getLocalAddress().getHostName(),socket.getInetAddress().getHostName());

        super.init();
    }

    @Override
    public void run() {

    }

}
