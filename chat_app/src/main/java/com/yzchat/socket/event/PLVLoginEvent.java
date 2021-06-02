package com.yzchat.socket.event;


import com.yzchat.socket.data.PLVSocketUserBean;

public class PLVLoginEvent extends PLVMessageBaseEvent {
    public static final String EVENT = "LOGIN";
    private int onlineUserNumber;
    private long timeStamp;
    private PLVSocketUserBean user;

    public PLVLoginEvent() {
    }

    public String getEVENT() {
        return "LOGIN";
    }

    public int getOnlineUserNumber() {
        return this.onlineUserNumber;
    }

    public void setOnlineUserNumber(int var1) {
        this.onlineUserNumber = var1;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(long var1) {
        this.timeStamp = var1;
    }

    public PLVSocketUserBean getUser() {
        return this.user;
    }

    public void setUser(PLVSocketUserBean var1) {
        this.user = var1;
    }

    public String toString() {
        return "PLVLoginEvent{EVENT='LOGIN', onlineUserNumber=" + this.onlineUserNumber + ", timeStamp=" + this.timeStamp + ", user=" + this.user + '}';
    }
}
