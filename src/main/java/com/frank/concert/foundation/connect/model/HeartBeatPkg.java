package com.frank.concert.foundation.connect.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Data
public class HeartBeatPkg implements Serializable {

    private static final long serialVersionUID = 1L;

    //时间戳 格式统一为 yyyy-MM-dd HH:mm:ss
    private String timeStamp;
    //来自？
    private String from;
    //去往？
    private String to;
    //版本，暂时占坑
    private String version;

}
