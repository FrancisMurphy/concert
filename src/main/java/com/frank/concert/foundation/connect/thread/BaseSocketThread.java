package com.frank.concert.foundation.connect.thread;

import com.frank.concert.foundation.constants.LogConstants;
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
            //TODO: 中断点，明天继续
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
        try {
            while ((msg = bufferedReader.readLine()) != null) {
                resp = receiveMsg(msg);
                //TODO:为方便测试进行间隔延迟
                Thread.sleep(1000);
                sendRespMsg(resp);
            }

        } catch (IOException e) {
            log.error(LogConstants.EX_ERROR + "There is an IOException occur, exception info: {}", e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
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
        printWriter.println(msg);
        printWriter.flush();
    }

    /**
     * 通过接口的方式将底层消息的接收处理解耦出来
     *
     * @param msg 接收到消息
     * @return
     */
    public abstract String receiveMsg(String msg);

    public void sendMsg(String msg) {
        printWriter.println(msg);
        printWriter.flush();
    }

    /**
     * 解析Socket交互通用包
     * 解析思路：由于网络通信时可能采用的分包策略，dataInputStream.available返回的可用字节长度不能作为pkg包的整体长度，
     * 为保证原子性，对eventTypeBa、contentByteArrayLengthBa 和 contentObjectBa 分包进行独立的堵塞策略；
     * 转换约定：
     * 事件类型(eventType int->byte[4]) + 内容字节数组长度(contentByteArrayLength int->byte[4])
     * + 序列化为字节数组的VO(contentObject Object->byte[n])
     * @param dataInputStream 数据输入流
     * @return 解析完成的内容pkg字节数组
     */
    private byte[] readCriteriaPkg(DataInputStream dataInputStream) throws IOException {

        while(true){
            byte[] eventTypeBa = new byte[4];
            byte[] contentLengthBa = new byte[4];

            //pkgBaLength在此只起到一个参考作用，dataInputStream.available由于网络通信时可能采用的分包策略，为保证原子性，使用
            int pkgBaLength = 0;

            while (pkgBaLength == 0) {
                pkgBaLength = dataInputStream.available();
            }


            dataInputStream.readFully(b);


        }

    }
}
