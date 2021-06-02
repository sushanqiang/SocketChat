package com.yzchat.socket.event;

public abstract class PLVMessageBaseEvent extends  PLVBaseEvent {
    public static final String LISTEN_EVENT = "message";

    public PLVMessageBaseEvent() {
    }

    public String getListenEvent() {
        return "message";
    }
}
