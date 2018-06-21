package com.frank.concert.foundation.connect.thread;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

@Slf4j
public abstract class BaseSocketThread implements Runnable {

    protected Socket socket;
    protected BufferedReader bufferedReader;
    protected PrintWriter printWriter;

    /**
     * Initialization the socket of this Thread
     */
    public void init(){

        InputStreamReader reader;
        OutputStreamWriter writer;

        try {
            reader = new InputStreamReader(socket.getInputStream());
            writer = new OutputStreamWriter(socket.getOutputStream());
            this.bufferedReader = new BufferedReader(reader);
            this.printWriter = new PrintWriter(writer);
        } catch (IOException e) {
            log.error("###ERROR### There is an IOException occur, exception info: {}", e.getMessage());
        }

        log.debug("###Init SocketThread success!");
    };

}
