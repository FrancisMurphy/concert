package com.frank.concert.foundation.connect.thread;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class BaseSocketThread implements Runnable {

    protected Socket socket;
    protected BufferedReader bufferedReader;
    protected PrintWriter printWriter;

    /**
     * Initialization the socket of this Thread
     *
     * @param socket
     */
    public abstract void init(Socket socket);

}
