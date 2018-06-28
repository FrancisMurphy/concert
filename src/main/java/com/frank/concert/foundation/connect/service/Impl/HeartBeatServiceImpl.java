package com.frank.concert.foundation.connect.service.Impl;

import com.frank.concert.foundation.connect.model.HeartBeatPkg;
import com.frank.concert.foundation.connect.service.HeartBeatService;
import com.frank.concert.foundation.connect.utils.SerializeUtil;
import com.frank.concert.foundation.connect.utils.TimeUtil;

import java.net.Socket;

public class HeartBeatServiceImpl implements HeartBeatService {

    @Override
    public byte[] getHeartBeatPkg(Socket socket) {
        HeartBeatPkg heartBeatPkg = new HeartBeatPkg();
        heartBeatPkg.setFrom(socket.getLocalAddress().getHostAddress());
        heartBeatPkg.setTo(socket.getInetAddress().getHostAddress());
        heartBeatPkg.setTimeStamp(TimeUtil.getCurDate());
        //TODO:暂时写死
        heartBeatPkg.setVersion("Test_1");
        byte[] pkgByteArray = SerializeUtil.serialize(heartBeatPkg);
        return pkgByteArray;
    }

    @Override
    public HeartBeatPkg getHeartBeatPkg(byte[] heartBeatPkgByteArray) {
        HeartBeatPkg pkg = (HeartBeatPkg)SerializeUtil.deserialize(heartBeatPkgByteArray);
        return pkg;
    }

}
