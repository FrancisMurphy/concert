package com.frank.concert.foundation.connect.bio.thread;

import com.frank.concert.foundation.connect.bio.keeper.LeaderKeeper;
import com.frank.concert.foundation.connect.bio.listener.RollCallListener;
import com.frank.concert.foundation.constants.LogConstants;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class RollCallThread implements Runnable {

    public ServerSocket leaderServiceSocket;
    public RollCallListener rollCallListener;

    public RollCallThread(ServerSocket leaderServiceSocket, RollCallListener rollCallListener) {
        this.leaderServiceSocket = leaderServiceSocket;
        this.rollCallListener = rollCallListener;
    }

    @Override
    public void run() {
        //TODO 点名线程暂时循环写死，停止及重新拉起机制等待后续完善
        while (true) {
            try {
                log.info("###Roll call thread is waiting the touch from follower...");
                Socket socket = leaderServiceSocket.accept();
                log.info("###There is a follower[hostIp:{}] comming!"
                        , socket.getInetAddress().getHostAddress());
                InetAddress followerInetAddress = socket.getInetAddress();
                String followerHostName = followerInetAddress.getHostName();
                String followerHostIp = followerInetAddress.getHostAddress();
                int followerHostPort = socket.getPort();
                LeaderSocketThread leaderSocketThread = new LeaderSocketThread(socket);
                leaderSocketThread.init();
                LeaderKeeper leaderKeeper = new LeaderKeeper(socket, leaderSocketThread,
                        followerHostName, followerHostIp, followerHostPort);
                rollCallListener.followerReply(leaderKeeper);
            } catch (IOException e) {
                log.error(LogConstants.EX_ERROR + "Occur IOException when roll call, detail: {}", e.getMessage());
            }
        }
    }

}
