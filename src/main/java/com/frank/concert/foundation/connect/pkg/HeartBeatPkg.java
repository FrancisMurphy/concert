package com.frank.concert.foundation.connect.pkg;

import lombok.Data;

import java.io.Serializable;

/**
 * 心跳专用包，为继承com.frank.concert.foundation.connect.pkg.BasePackage的空包
 */
@Data
public class HeartBeatPkg extends BasePkg implements Serializable {

    private static final long serialVersionUID = 1L;

}
