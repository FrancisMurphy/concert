package com.frank.concert.foundation.connect.model;

import lombok.Getter;
import lombok.Setter;

import java.net.Socket;

@Setter
@Getter
public class LeaderSocket {

    private Socket leaderSocket;

    private String followerHostName;
    private String followerHostIp;
    private int followerHostPort;

    public LeaderSocket(Socket leaderSocket, String followerHostName, String followerHostIp, int followerHostPort) {
        this.followerHostIp = followerHostIp;
        this.followerHostPort = followerHostPort;
        this.leaderSocket = leaderSocket;
        this.followerHostName = followerHostName;
    }

}
