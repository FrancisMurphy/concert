package com.frank.concert.foundation.connect.bio.thread;

import lombok.extern.slf4j.Slf4j;

import java.net.Socket;

@Slf4j
public class FollowerSocketThread extends BaseSocketThread {

    public FollowerSocketThread(Socket socket) {
        this.socket = socket;
    }

    public void init() {

        log.debug("###Init SocketThread [FollowerName:{} LeaderName:{}]...",
                socket.getLocalAddress().getHostName(), socket.getInetAddress().getHostName());

        baseInit();
    }

    @Override
    public String receiveMsg(String msg) {
        log.info("@@@ Receivce msg form leader , msg:{}", msg);
        String testMsg = "This is test msg from Follower...";
        return testMsg;
    }
}
