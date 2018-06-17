package com.frank.concert.foundation.connect.thread;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

@Slf4j
public class LeaderSocketThread extends BaseSocketThread {

    @Override
    public void init(Socket socket) {

        log.debug("###Init LeaderSocketThread...");

        this.socket = socket;

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

        log.debug("###Init LeaderSocketThread success!");

    }

    public void run() {

    }
}
