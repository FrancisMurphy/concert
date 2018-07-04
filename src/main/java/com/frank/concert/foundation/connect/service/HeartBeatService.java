package com.frank.concert.foundation.connect.service;

import com.frank.concert.foundation.connect.letter.HeartBeatLetter;

import java.net.Socket;

public interface HeartBeatService {

    /**
     * 心跳发起者获取心跳VO
     * @param socket 用于发送心跳的socket
     * @return 心跳VO
     */
    public byte[] getHeartBeatPkg(Socket socket);

    /**
     * 心跳接收方解析心跳字节获取心跳包VO
     */
    public HeartBeatLetter getHeartBeatPkg(byte[] heartBeatPkgByteArray);

}
