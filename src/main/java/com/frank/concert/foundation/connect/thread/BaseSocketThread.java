package com.frank.concert.foundation.connect.thread;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

/**
 * 用于处理Socket底层交互的抽象类，封装了心跳机制
 */
@Slf4j
public abstract class BaseSocketThread implements Runnable {

    protected Socket socket;
    protected BufferedReader bufferedReader;
    protected PrintWriter printWriter;
    protected HeartBeatThread heartBeatThread;

    /**
     * Initialization the socket of this Thread
     */
    public void init(){

        //初始化输入输出流
        InputStreamReader reader;
        OutputStreamWriter writer;

        try {
            reader = new InputStreamReader(socket.getInputStream());
            writer = new OutputStreamWriter(socket.getOutputStream());
            this.bufferedReader = new BufferedReader(reader);
            this.printWriter = new PrintWriter(writer);

            //开始心跳
            //TODO:需要实现Thread中对对应Lind的信息注册，此处吧LinkId先行写死
            heartBeatThread = HeartBeatThread.getInstance();
            heartBeatThread.init(socket,"Test123");
            heartBeatThread.startHeartBeat();
        } catch (IOException e) {
            log.error("###ERROR### There is an IOException occur, exception info: {}", e.getMessage());
        }

        log.debug("###Init SocketThread success!");
    };

    @Override
    public void run() {




    }







}
