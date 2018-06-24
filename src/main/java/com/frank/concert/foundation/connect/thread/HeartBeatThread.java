package com.frank.concert.foundation.connect.thread;

import com.frank.concert.foundation.constants.SocketConstants;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

/**
 * 封装了心跳处理的线程，断连重连机制后续完善
 */
@Slf4j
public class HeartBeatThread implements Runnable{

    private Socket localSocket;
    private Thread localThread;
    private String localId;

    public HeartBeatThread(){
    }

    public void init(Socket socket, String linkId){
        this.localSocket = socket;
        this.localId = SocketConstants.HeartBeatPrefix + linkId;
    }


    //开始心跳线程
    public void startHeartBeat() throws SocketException {

        if(validSocket())
            return;
        log.info("###Starting the heart beat thread[id:{}] for socket[Localhost:{} TargetHost:{}]",
                localId,localSocket.getLocalAddress().getHostAddress(),localSocket.getInetAddress().getHostAddress());

        localThread = new Thread(this);
        localThread.start();

    }

    @Override
    public void run() {
        try {
            sendHeartBeat();
        } catch (IOException e) {
            log.debug("###ERROR### There occur an IOException when send heart beat, Exception:{}", e.getMessage());
        } catch (InterruptedException e) {
            log.debug("###ERROR### There occur an InterruptedException when send heart beat, Exception:{}", e.getMessage());
        }

    }

    //判断用于发送心跳的socket是否为有效socket
    private boolean validSocket() throws SocketException{

        if(localSocket==null){
            log.error("###ERROR### The socket is null, can not start heart beat thread...");
            return false;
        }

        if(!localSocket.isConnected()) {
            log.error("###ERROR### The socket is not connected, can not start heart beat thread...");
            return false;
        }

        if(!localSocket.getKeepAlive()){
            localSocket.setKeepAlive(true);
        }

        return true;
    }

    /**
     * 通过间隔15秒的方式向socket中发送空包来保持长连接检测的机制
     * @throws IOException
     * @throws InterruptedException
     */
    private void sendHeartBeat() throws IOException, InterruptedException {

        while (true) {
            localSocket.sendUrgentData(0xFF); // 发送心跳包
            log.debug("### The connection of socket[{}] is regular...");
            Thread.sleep(15 * 1000);//线程睡眠3秒
        }

    }

    private static class HeartBeatHolder
    {
        private static final HeartBeatThread INSTANCE = new HeartBeatThread();
    }

    public static final HeartBeatThread getInstance() {
        return HeartBeatHolder.INSTANCE;
    }
}
