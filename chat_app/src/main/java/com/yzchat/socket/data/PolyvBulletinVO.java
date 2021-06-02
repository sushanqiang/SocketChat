package com.yzchat.socket.data;

public class PolyvBulletinVO {
    private String EVENT = "BULLETIN";
    private String content;

    public PolyvBulletinVO() {
    }

    public String getEVENT() {
        return this.EVENT;
    }

    public void setEVENT(String var1) {
        this.EVENT = var1;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String var1) {
        this.content = var1;
    }
}
