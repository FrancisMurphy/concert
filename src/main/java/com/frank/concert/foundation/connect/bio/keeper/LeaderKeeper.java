package com.frank.concert.foundation.connect.bio.keeper;

import com.frank.concert.foundation.connect.bio.thread.LeaderSocketThread;
import lombok.Getter;
import lombok.Setter;

import java.net.Socket;

@Setter
@Getter
public class LeaderKeeper {

    private Socket leaderSocket;

    private LeaderSocketThread leaderSocketThread;

    private String followerHostName;

    private String followerHostIp;

    private int followerHostPort;

    public LeaderKeeper(Socket leaderSocket, LeaderSocketThread leaderSocketThread,
                        String followerHostName, String followerHostIp, int followerHostPort) {
        this.followerHostIp = followerHostIp;
        this.followerHostPort = followerHostPort;
        this.leaderSocket = leaderSocket;
        this.followerHostName = followerHostName;
        this.leaderSocketThread = leaderSocketThread;
    }

}
