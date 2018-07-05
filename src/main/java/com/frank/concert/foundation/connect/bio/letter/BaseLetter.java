package com.frank.concert.foundation.connect.bio.letter;

import lombok.Data;

@Data
public class BaseLetter {

    //时间戳 格式统一为 yyyy-MM-dd HH:mm:ss
    private String timeStamp;
    //来自？
    private String from;
    //去往？
    private String to;
    //版本，暂时占坑
    private String version;

}
