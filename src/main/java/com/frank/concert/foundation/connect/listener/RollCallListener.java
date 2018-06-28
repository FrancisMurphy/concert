package com.frank.concert.foundation.connect.listener;

import com.frank.concert.foundation.connect.keeper.LeaderKeeper;

/**
 * The linstener of roll call
 */
public interface RollCallListener
{

    void followerReply(LeaderKeeper leaderKeeper);

}
