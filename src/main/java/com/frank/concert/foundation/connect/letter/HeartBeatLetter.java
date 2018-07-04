package com.frank.concert.foundation.connect.letter;

import lombok.Data;

import java.io.Serializable;

/**
 * 心跳专用包，为继承com.frank.concert.foundation.connect.letter.BasePackage的空包
 */
@Data
public class HeartBeatLetter extends BaseLetter implements Serializable {

    private static final long serialVersionUID = 1L;

}
