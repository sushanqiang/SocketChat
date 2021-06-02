package com.yzchat.socket.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;

public class Utils {
    @SuppressLint({"StaticFieldLeak"})
    private static Context sApplication;

    protected Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(@NonNull Context var0) {
        sApplication = var0;
     }

    public static Context getApp() {
        if (sApplication != null) {
            return sApplication;
        } else {
            throw new NullPointerException("u should init first");
        }
    }


}
