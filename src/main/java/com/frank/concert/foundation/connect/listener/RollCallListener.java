package com.frank.concert.foundation.connect.listener;

import com.frank.concert.foundation.connect.model.LeaderSocket;

/**
 * The linstener of roll call
 */
public interface RollCallListener {

    /**
     */
    void followerReply(LeaderSocket leaderSocket);

}
