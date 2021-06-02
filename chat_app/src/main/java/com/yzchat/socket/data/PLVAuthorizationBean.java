package com.yzchat.socket.data;

import android.graphics.Color;

import java.io.Serializable;

public class PLVAuthorizationBean implements Serializable {
    public static final String FCOLOR_DEFAULT = "#ffffff";
    public static final String BGCOLOR_DEFAULT = "#4A90E2";
    private String actor;
    private String fColor;
    private String bgColor;

    public PLVAuthorizationBean(String var1) {
        this(var1, "#ffffff", "#4A90E2");
    }

    public PLVAuthorizationBean(String var1, String var2, String var3) {
        this.actor = var1;
        this.fColor = var2;
        this.bgColor = var3;
    }

    public String getActor() {
        return this.actor;
    }

    public void setActor(String var1) {
        this.actor = var1;
    }

    public String getfColor() {
        return fitfColor(this.fColor);
    }

    public void setfColor(String var1) {
        this.fColor = var1;
    }

    public String getBgColor() {
        return fitBgColor(this.bgColor);
    }

    public void setBgColor(String var1) {
        this.bgColor = var1;
    }

    public static String fitfColor(String var0) {
        try {
            Color.parseColor(var0);
            return var0;
        } catch (Exception var2) {
            return "#ffffff";
        }
    }

    public static String fitBgColor(String var0) {
        try {
            Color.parseColor(var0);
            return var0;
        } catch (Exception var2) {
            return "#4A90E2";
        }
    }

    public String toString() {
        return "PLVAuthorizationBean{actor='" + this.actor + '\'' + ", fColor='" + this.fColor + '\'' + ", bgColor='" + this.bgColor + '\'' + '}';
    }
}
