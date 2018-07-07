package com.frank.concert.foundation.Agreement;

import com.frank.concert.foundation.tools.ByteArrayTool;
import com.frank.concert.foundation.tools.SerializeTool;

import com.frank.concert.foundation.constants.PkgEventContants;

/**
 * 底层所有涉及的自定义协议通用解释器
 * 主要作用为尽可能的将协议部分的逻辑处理分离出来
 * <p>
 * 简称定义：
 * Ba -> ByteArray
 */
public class AgreeInterpreter {

    /**
     * Common packager
     * <p>
     * 将心跳用的内容包包装为socket通讯的标准包(字节数组)
     * 转换约定：
     * 事件类型(eventType int->byte[4]) + 内容字节数组长度(contentByteArrayLength int->byte[4])
     * + 序列化为字节数组的VO(contentObject Object->byte[n])
     *
     * @param eventType    pkg的对应事件类型
     *                     {@link com.frank.concert.foundation.constants.PkgEventContants}
     * @param contentPkgBa 序列为字节数组的pkg内容VO
     * @return criteriaPkgBa 标准socket通信pkg
     */
    public static byte[] wrapCriteriaPkg(int eventType, byte[] contentPkgBa) {

        byte[] criteriaPkgBa;

        int contentBaLength = contentPkgBa.length;
        byte[] contentBaLengthBa = ByteArrayTool.IntToByteArray(contentBaLength);
        byte[] eventTypeBa = ByteArrayTool.IntToByteArray(eventType);
        criteriaPkgBa = ByteArrayTool.assembleBytes(eventTypeBa, contentBaLengthBa, contentPkgBa);

        return criteriaPkgBa;
    }

    /**
     * Common unpackager
     *
     * @param contentPkgBa 内容VO 字节数组
     * @param <T>          对应事件类型的内容VO类型
     * @return 对应事件类型的内容VO
     */
    public static <T> T unsealCriteriaPkg(byte[] contentPkgBa) {

        Object targetObjectTemp = SerializeTool.deserialize(contentPkgBa);
        T targetObject = (T) targetObjectTemp;

        return targetObject;
    }

    /**
     * Agreement
     * <p>
     * 获取心跳标准包(字节数组)
     *
     * @param contentPkgBa
     * @return
     */
    public static byte[] getHeartBeatPkgBa(byte[] contentPkgBa) {

        byte[] heartBeatCriteriaPkgBa;
        heartBeatCriteriaPkgBa = wrapCriteriaPkg(PkgEventContants.PKG_EVENT_SOCKET_HEART_BEAT, contentPkgBa);

        return heartBeatCriteriaPkgBa;
    }
}
