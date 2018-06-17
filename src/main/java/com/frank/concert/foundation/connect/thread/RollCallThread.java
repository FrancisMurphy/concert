package com.frank.concert.foundation.connect.thread;

import com.frank.concert.foundation.connect.listener.RollCallListener;
import com.frank.concert.foundation.connect.model.LeaderSocket;
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
        //TODO 点名线程暂时写死，停止及重新拉起机制等待后续完善
        while (true) {
            try {
                Socket socket = leaderServiceSocket.accept();
                InetAddress followerInetAddress = socket.getInetAddress();
                String followerHostName = followerInetAddress.getHostName();
                String followerHostIp = followerInetAddress.getHostAddress();
                int followerHostPort = socket.getPort();
                LeaderSocket leaderSocket = new LeaderSocket(socket,
                        followerHostName, followerHostIp, followerHostPort);
                rollCallListener.followerReply(leaderSocket);
            } catch (IOException e) {
                log.error("###ERROR-IO### Occur IOException when roll call, detail: {}", e.getMessage());
            }
        }
    }

}
