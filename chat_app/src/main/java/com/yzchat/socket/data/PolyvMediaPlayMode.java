package com.yzchat.socket.data;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class PolyvMediaPlayMode {
    public static final int MODE_VIDEO = 0;
    public static final int MODE_AUDIO = 1;
    private static final int MIN_MODE_VALUE = 0;
    private static final int MAX_MODE_VALUE = 1;

    public PolyvMediaPlayMode() {
    }

    public static int amendMode(int var0) {
        return var0 >= 0 && var0 <= 1 ? var0 : 0;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }
}
