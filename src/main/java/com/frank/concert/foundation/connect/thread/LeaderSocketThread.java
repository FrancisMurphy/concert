package com.frank.concert.foundation.connect.thread;

import com.frank.concert.foundation.connect.model.LeaderSocket;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

@Slf4j
public class LeaderSocketThread extends BaseSocketThread {

    public LeaderSocketThread(Socket socket){
        this.socket = socket;
    }

    public void init() {

        log.debug("###Init SocketThread [LeaderName:{} FollowerName:{}]...",
                socket.getLocalAddress().getHostName(),socket.getInetAddress().getHostName());

        super.init();
    }

    @Override
    public void run() {

    }
}
