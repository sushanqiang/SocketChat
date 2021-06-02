package com.yzchat.socket.data;

import java.util.Arrays;

public  abstract class PolyvBaseHolder {
    private Object[] objects;

    public PolyvBaseHolder() {
    }

    public Object[] getObjects() {
        return this.objects;
    }

    public void setObjects(Object... var1) {
        this.objects = var1;
    }

    public String toString() {
        return "PolyvBaseHolder{objects=" + Arrays.toString(this.objects) + '}';
    }
}