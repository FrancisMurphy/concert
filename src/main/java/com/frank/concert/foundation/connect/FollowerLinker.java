package com.frank.concert.foundation.connect;

import com.frank.concert.foundation.connect.model.FollowerSocket;
import com.frank.concert.foundation.connect.thread.FollowerSocketThread;
import com.frank.concert.foundation.constants.SocketConstants;
import com.frank.concert.foundation.tools.IdTool;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;

@Slf4j
public class FollowerLinker extends BaseLinker{

    //The relationship between leader and follower that has been registed by follower...
    private static FollowerSocket followerSocket;

    private static Thread registerFollowerThread;

    private static String followerIp;
    private static int followerPort;

    public FollowerLinker(){
        linkerId = SocketConstants.FollerPrefix + IdTool.getUUID();
    }

    public void init(String followerIp, int followerPort){

        this.followerIp = followerIp;
        this.followerPort = followerPort;

        try{
            registerNewLeader(followerIp,followerPort);
        }catch (IOException e){
            log.error("###The serviceSocket of leader init fail, IOException: {}", e.getMessage());
        }
        log.debug("###Init leaderLinker socket service success!");
    }

    private static synchronized void registerNewLeader(String followerIp, int followerPort) throws IOException {
        //init the important member
        Socket socket = new Socket(followerIp, followerPort);

        FollowerSocketThread followerSocketThread = new FollowerSocketThread(socket);
        followerSocketThread.init();
        //registerNewLeader
        followerSocket = new FollowerSocket(socket,followerSocketThread,
                socket.getInetAddress().getHostName(),followerIp,followerPort);

        registerFollowerThread = new Thread(followerSocket.getFollowerSocketThread(),linkerId);
        registerFollowerThread.start();
    }


    private static class SinglerHolder
    {
        private static final FollowerLinker INSTANCE = new FollowerLinker();
    }

    public static final FollowerLinker getInstance() {
        return SinglerHolder.INSTANCE;
    }
}
