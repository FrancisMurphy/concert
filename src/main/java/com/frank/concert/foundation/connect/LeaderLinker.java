package com.frank.concert.foundation.connect;

import com.frank.concert.foundation.constants.SocketConstants;
import com.frank.concert.foundation.tools.IdTool;

import java.net.Socket;

/**
 * Leader 服务器使用单例模式
 */
public class LeaderLinker extends BaseLinker{

    private Socket leaderSocket;
    private String leaderIp;
    private String leaderPort;

    //第一步：获得leader socket service实例的 uuid
    public LeaderLinker(){
        linkerId = SocketConstants.LeaderPrefix + IdTool.getUUID();
    }

    //第二步：初始化建立leader所用的ip与端口号
    public void init(String leaderIp,String leaderPort){

        Log.debug("###Initing leaderLinker socket service [ip:{},port:{}]...",leaderIp,leaderPort);

        this.leaderIp = leaderIp;
        this.leaderPort = leaderPort;




    }

    private Socket initSocketService(){
        return null;
    }

    private static class SinglerHolder{
        private static final LeaderLinker INSTANCE = new LeaderLinker();
    }

    public static final LeaderLinker getInstance() {
        return SinglerHolder.INSTANCE;
    }

}
