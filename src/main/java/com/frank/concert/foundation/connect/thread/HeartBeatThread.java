package com.frank.concert.foundation.connect.thread;

import com.frank.concert.foundation.connect.service.HeartBeatService;
import com.frank.concert.foundation.connect.service.Impl.HeartBeatServiceImpl;
import com.frank.concert.foundation.constants.LogConstants;
import com.frank.concert.foundation.constants.SocketConstants;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

/**
 * 封装了心跳处理的线程，断连重连机制后续完善
 */
@Slf4j
public class HeartBeatThread implements Runnable
{

    private Socket localSocket;

    private DataOutputStream localOutputStream;

    private DataInputStream localInputStream;

    private Thread localThread;

    private String localId;

    private HeartBeatService heartBeatService;

    public HeartBeatThread()
    {
    }

    public void init(Socket socket, String linkId) throws IOException {
        this.localSocket = socket;
        this.localId = SocketConstants.HeartBeatPrefix + linkId;
        heartBeatService = new HeartBeatServiceImpl();
        localOutputStream = new DataOutputStream(localSocket.getOutputStream());
        localInputStream = new DataInputStream(localSocket.getInputStream());
    }


    //开始心跳线程

    public void startHeartBeat() throws SocketException
    {

        if(!validSocket())
            return;
        log.info("###Starting the heart beat thread[id:{}] for socket[Localhost:{} TargetHost:{}]",
                localId,localSocket.getLocalAddress().getHostAddress(),localSocket.getInetAddress().getHostAddress());

        localThread = new Thread(this);
        localThread.start();

    }

    @Override
    public void run()
    {
        try
        {
            sendHeartBeat();
        }
        catch (IOException e)
        {
            log.error(LogConstants.EX_ERROR + "There occur an IOException when send heart beat, Exception:{}", e.getMessage());
        }
        catch (InterruptedException e)
        {
            log.error(LogConstants.EX_ERROR + "There occur an InterruptedException when send heart beat, Exception:{}", e.getMessage());
        }

    }

    //判断用于发送心跳的socket是否为有效socket
    private boolean validSocket() throws SocketException
    {

        if(localSocket==null)
        {
            log.error(LogConstants.EX_ERROR + "The socket is null, can not start heart beat thread...");
            return false;
        }

        if(!localSocket.isConnected())
        {
            log.error(LogConstants.EX_ERROR + "The socket is not connected, can not start heart beat thread...");
            return false;
        }

        if(!localSocket.getKeepAlive())
        {
            localSocket.setKeepAlive(true);
        }

        return true;
    }

    /**
     * 通过间隔10秒的方式向socket中发送空包来保持长连接检测的机制
     * 由于在windows上发送Urgent Data一定次数之后连接会被强行停止，故心跳机制调整至一个自定义数据包用于自行判断对方是否还处于连接中...
     *
     * @throws IOException
     * @throws InterruptedException
     */
    private void sendHeartBeat() throws IOException, InterruptedException
    {

        while (true)
        {
            //由于在windows上发送Urgent Data一定次数之后连接会被强行停止，故心跳机制调整至一个自定义数据包用于自行判断
            //localSocket.sendUrgentData(0xFF);
            //TODO:心跳包机制待完成，需要设计字节流的交互约定
            byte[] hBPkg = heartBeatService.getHeartBeatPkg(localSocket);
            localOutputStream.write(hBPkg);

            log.debug("### The connection of socket[{}] is regular...",localSocket.getInetAddress().getHostAddress());
            Thread.sleep(10 * 1000);
        }

    }

    /**
     * 单例模式
     */
    private static class HeartBeatHolder
    {
        private static final HeartBeatThread INSTANCE = new HeartBeatThread();
    }

    public static final HeartBeatThread getInstance()
    {
        return HeartBeatHolder.INSTANCE;
    }
}
