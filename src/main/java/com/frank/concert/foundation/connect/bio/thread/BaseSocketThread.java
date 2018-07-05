package com.frank.concert.foundation.connect.bio.thread;

import com.frank.concert.foundation.connect.bio.pojo.Envelope;
import com.frank.concert.foundation.connect.bio.letter.HeartBeatLetter;
import com.frank.concert.foundation.constants.LogConstants;
import com.frank.concert.foundation.tools.ByteArrayTool;
import com.frank.concert.foundation.tools.SerializeTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;

/**
 * 用于处理Socket底层交互的抽象类，封装了心跳机制
 */
@Slf4j
public abstract class BaseSocketThread implements Runnable {

    protected Socket socket;
    protected DataInputStream dataInputStream;
    protected DataOutputStream dataOutputStream;
    protected HeartBeatThread heartBeatThread;

    /**
     * Initialization the socket of this Thread
     */
    public void baseInit() {

        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            //开始心跳
            //TODO:需要实现Thread中对对应Lind的信息注册，此处吧LinkId先行写死
            heartBeatThread = HeartBeatThread.getInstance();
            heartBeatThread.init(socket, "Test123");
            heartBeatThread.startHeartBeat();
        } catch (IOException e) {
            log.error(LogConstants.EX_ERROR + "There is an IOException occur, exception info: {}", e.getMessage());
            return;
        }

        log.debug("###Init SocketThread success!");
    }

    @Override
    public void run() {
        byte[] msg = null;
        String resp = null;
        while(true){
            try {
                Envelope envelope = readCriteriaPkg(dataInputStream);

                //获取pkg临时固定为com.frank.concert.foundation.connect.letter.HeartBeatLetter
                HeartBeatLetter hBLetter = (HeartBeatLetter)envelope.getLetter();
                String from = hBLetter.getFrom();
                log.info("### Receivce heart beat from {}",from);
                //临时延迟
                Thread.sleep(1000);
            } catch (IOException e) {
                log.error(LogConstants.EX_ERROR + "There is an IOException occur, exception info: {}", e.getMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 专用于发送回应的消息
     *
     * @param msg
     */
    private void sendRespMsg(String msg) {
        if (StringUtils.isEmpty(msg)) {
            log.error(LogConstants.EX_ERROR + "The resp msg is null which could not send...");
            return;
        }
    }

    /**
     * 通过接口的方式将底层消息的接收处理解耦出来
     *
     * @param msg 接收到消息
     * @return
     */
    public abstract String receiveMsg(String msg);

    public void sendMsg(String msg) {
    }

    /**
     * 解析Socket交互通用包
     * 解析思路：由于网络通信时可能采用的分包策略，dataInputStream.available返回的可用字节长度不能作为pkg包的整体长度，
     * 为保证原子性，对eventTypeBa、contentByteArrayLengthBa 和 contentObjectBa 分包进行独立的堵塞策略；
     * 转换约定：
     * 事件类型(eventType int->byte[4]) + 内容字节数组长度(contentByteArrayLength int->byte[4])
     * + 序列化为字节数组的VO(contentObject Object->byte[n])
     *
     * @param dataInputStream 数据输入流
     * @return 解析完成的内容pkg字节数组
     */
    private Envelope readCriteriaPkg(DataInputStream dataInputStream) throws IOException {

        byte[] eventTypeBa = new byte[4];
        byte[] letterLengthBa = new byte[4];

        /**
         * pkgBaLength在此只起到一个参考作用，dataInputStream.available由于网络通信时可能采用的半包策略，为保证原子性，在获取到长度之后
         * 对正文长度进行堵塞式获取；
         */
        int pkgBaLength = 0;

        while (pkgBaLength == 0) {
            pkgBaLength = dataInputStream.available();
        }

        dataInputStream.readFully(eventTypeBa);
        int eventType = ByteArrayTool.ByteArrayToInt(eventTypeBa);

        dataInputStream.readFully(letterLengthBa);
        int contentLength = ByteArrayTool.ByteArrayToInt(letterLengthBa);

        if(contentLength<=0){
            return null;
        }

        byte[] letterBa = new byte[contentLength];
        dataInputStream.readFully(letterBa);

        Envelope envelope = new Envelope();
        envelope.setEventType(eventType);
        envelope.setContentLength(contentLength);

        Object letter = SerializeTool.deserialize(letterBa);
        envelope.setLetter(letter);

        return envelope;
    }
}
