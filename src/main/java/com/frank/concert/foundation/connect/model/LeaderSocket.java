package com.frank.concert.foundation.connect.model;

import com.frank.concert.foundation.connect.thread.LeaderSocketThread;
import lombok.Getter;
import lombok.Setter;

import java.net.Socket;

@Setter
@Getter
public class LeaderSocket
{

    private Socket leaderSocket;

    private LeaderSocketThread leaderSocketThread;

    private String followerHostName;

    private String followerHostIp;

    private int followerHostPort;

    public LeaderSocket(Socket leaderSocket,LeaderSocketThread leaderSocketThread,
                        String followerHostName, String followerHostIp, int followerHostPort)
    {
        this.followerHostIp = followerHostIp;
        this.followerHostPort = followerHostPort;
        this.leaderSocket = leaderSocket;
        this.followerHostName = followerHostName;
        this.leaderSocketThread = leaderSocketThread;
    }

}
