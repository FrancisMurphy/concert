package com.frank.concert.foundation.tools;

import java.util.UUID;

public class IdTool {

    /**
     * Generating 32 bit UUid
     *
     * @return UUid
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
