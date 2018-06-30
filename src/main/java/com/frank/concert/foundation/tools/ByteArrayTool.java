package com.frank.concert.foundation.tools;

/**
 * 字节数组工具
 */
public class ByteArrayTool {

    /**
     * 组装字节数组
     *
     * @param ba1
     * @param ba2
     * @param ba3
     * @return 拼接结果
     */
    public static byte[] assembleBytes(byte[] ba1, byte[] ba2, byte[] ba3) {
        byte[] targetBa = new byte[ba1.length + ba2.length + ba3.length];
        System.arraycopy(ba1, 0, targetBa, 0, ba1.length);
        System.arraycopy(ba2, 0, targetBa, ba1.length, ba2.length);
        System.arraycopy(ba3, 0, targetBa, ba1.length + ba2.length, ba3.length);
        return targetBa;
    }

    /*将int转为低字节在前，高字节在后的byte数组
     */
    public static byte[] IntToByteArray(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        return b;
    }

    //将低字节在前转为int，高字节在后的byte数组(与IntToByteArray1想对应)
    public static int ByteArrayToInt(byte[] bArr) {
        if (bArr.length != 4) {
            return -1;
        }
        return (int) ((((bArr[3] & 0xff) << 24)
                | ((bArr[2] & 0xff) << 16)
                | ((bArr[1] & 0xff) << 8)
                | ((bArr[0] & 0xff) << 0)));
    }
}
