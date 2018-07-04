package com.frank.concert.foundation.connect.service.Impl;

import com.frank.concert.foundation.Agreement.AgreeInterpreter;
import com.frank.concert.foundation.connect.letter.HeartBeatLetter;
import com.frank.concert.foundation.connect.service.HeartBeatService;
import com.frank.concert.foundation.tools.SerializeTool;
import com.frank.concert.foundation.tools.TimeTool;

import java.net.Socket;

public class HeartBeatServiceImpl implements HeartBeatService {

    @Override
    public byte[] getHeartBeatPkg(Socket socket) {

        HeartBeatLetter heartBeatPkg = new HeartBeatLetter();
        heartBeatPkg.setFrom(socket.getLocalAddress().getHostAddress());
        heartBeatPkg.setTo(socket.getInetAddress().getHostAddress());
        heartBeatPkg.setTimeStamp(TimeTool.getCurDate());
        //TODO:版本暂时写死
        heartBeatPkg.setVersion("Test_1");
        byte[] pkgByteArray = SerializeTool.serialize(heartBeatPkg);

        return AgreeInterpreter.getHeartBeatPkgBa(pkgByteArray);
    }

    @Override
    public HeartBeatLetter getHeartBeatPkg(byte[] heartBeatPkgByteArray) {
        HeartBeatLetter pkg = (HeartBeatLetter) AgreeInterpreter.unsealCriteriaPkg(heartBeatPkgByteArray);
        return pkg;
    }

}
