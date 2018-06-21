package com.frank.concert.foundation.connect.model;

import com.frank.concert.foundation.connect.thread.FollowerSocketThread;
import lombok.Getter;
import lombok.Setter;

import java.net.Socket;

@Setter
@Getter
public class FollowerSocket {

    private Socket followerSocket;

    private FollowerSocketThread followerSocketThread;

    private String leaderHostName;

    private String leaderHostIp;

    private int leaderHostPort;

    public FollowerSocket(Socket followerSocket, FollowerSocketThread followerSocketThread,
                          String leaderHostName, String leaderHostIp, int leaderHostPort) {
        this.followerSocket = followerSocket;
        this.followerSocketThread = followerSocketThread;
        this.leaderHostName = leaderHostName;
        this.leaderHostIp = leaderHostIp;
        this.leaderHostPort = leaderHostPort;
    }
}
