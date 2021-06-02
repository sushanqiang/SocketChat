package com.yzchat.socket.event;


public class SocketEvent {
    int type;
    int status;
    Object data;

    public Object getData() {
        return data;
    }

    public SocketEvent setObject(Object object) {
        this.data = object;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public SocketEvent setStatus(int status) {
        this.status = status;
        return this;
    }

    String msg;

    public int getType() {
        return type;
    }

    public SocketEvent setType(int type) {
        this.type = type;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public SocketEvent setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
