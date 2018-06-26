package com.frank.concert.foundation.connect;

import com.frank.concert.foundation.connect.model.FollowerSocket;
import com.frank.concert.foundation.connect.thread.FollowerSocketThread;
import com.frank.concert.foundation.constants.SocketConstants;
import com.frank.concert.foundation.tools.IdTool;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;

@Slf4j
public class FollowerLinker extends BaseLinker
{

    //The relationship between leader and follower that has been registed by follower...
    private static FollowerSocket followerSocket;

    private static Thread registerFollowerThread;

    private static String leaderIp;
    private static int leaderPort;

    public FollowerLinker()
    {
        linkerId = SocketConstants.FollerPrefix + IdTool.getUUID();
    }

    public void init(String leaderIp, int leaderPort)
    {

        this.leaderIp = leaderIp;
        this.leaderPort = leaderPort;

        try{
            registerNewLeader(leaderIp,leaderPort);
        }catch (IOException e){
            log.error("###The serviceSocket of leader init fail, IOException: {}", e.getMessage());
        }
        log.debug("###Init leaderLinker socket service success!");
    }

    private static synchronized void registerNewLeader(String leaderIp, int leaderPort) throws IOException
    {
        //init the important member
        Socket socket = new Socket(leaderIp, leaderPort);
        socket.setKeepAlive(true);
        socket.setSoTimeout(50 * 1000);

        FollowerSocketThread followerSocketThread = new FollowerSocketThread(socket);
        followerSocketThread.init();
        //registerNewLeader
        followerSocket = new FollowerSocket(socket,followerSocketThread,
                socket.getInetAddress().getHostName(),leaderIp,leaderPort);

        registerFollowerThread = new Thread(followerSocket.getFollowerSocketThread(),linkerId);
        registerFollowerThread.start();
    }


    private static class FollowerHolder
    {
        private static final FollowerLinker INSTANCE = new FollowerLinker();
    }

    public static final FollowerLinker getInstance()
    {
        return FollowerHolder.INSTANCE;
    }

    public static void main(String[] args)
    {
        FollowerLinker followerLinker = FollowerLinker.getInstance();
        followerLinker.init("127.0.0.1",10000);
    }

}
