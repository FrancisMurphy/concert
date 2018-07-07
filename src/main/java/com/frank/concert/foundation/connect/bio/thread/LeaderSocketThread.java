package com.frank.concert.foundation.connect.bio.thread;

import lombok.extern.slf4j.Slf4j;

import java.net.Socket;

@Slf4j
public class LeaderSocketThread extends BaseSocketThread {

    public LeaderSocketThread(Socket socket) {
        this.socket = socket;
    }

    public void init() {
        log.debug("###Init SocketThread [LeaderName:{} FollowerName:{}]...",
                socket.getLocalAddress().getHostName(), socket.getInetAddress().getHostName());

        baseInit();
    }

    @Override
    public String receiveMsg(String msg) {
        log.info("@@@ Receivce msg form follower , msg:{}", msg);
        String testMsg = "This is test msg from Leader...";
        return testMsg;
    }
}
