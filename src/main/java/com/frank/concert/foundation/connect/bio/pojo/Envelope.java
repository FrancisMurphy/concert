package com.frank.concert.foundation.connect.bio.pojo;

import lombok.Data;

@Data
public class Envelope {

    private int eventType;
    private int contentLength;
    private Object letter;
}
