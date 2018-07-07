package com.frank.concert.foundation.connect.bio.listener;

import com.frank.concert.foundation.connect.bio.keeper.LeaderKeeper;

/**
 * The linstener of roll call
 */
public interface RollCallListener {

    void followerReply(LeaderKeeper leaderKeeper);

}
