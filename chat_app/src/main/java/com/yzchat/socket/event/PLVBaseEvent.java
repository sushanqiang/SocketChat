package com.yzchat.socket.event;

import java.io.Serializable;

public   abstract class PLVBaseEvent implements Serializable {
    private Object obj1;
    private Object obj2;
    private Object obj3;
    private Object obj4;
    private Object obj5;
    private Object[] objects;

    public PLVBaseEvent() {
    }

    public Object[] getObjects() {
        return this.objects;
    }

    public void setObjects(Object... var1) {
        this.objects = var1;
    }

    public Object getObj1() {
        return this.obj1;
    }

    public void setObj1(Object var1) {
        this.obj1 = var1;
    }

    public Object getObj2() {
        return this.obj2;
    }

    public void setObj2(Object var1) {
        this.obj2 = var1;
    }

    public Object getObj3() {
        return this.obj3;
    }

    public void setObj3(Object var1) {
        this.obj3 = var1;
    }

    public Object getObj4() {
        return this.obj4;
    }

    public void setObj4(Object var1) {
        this.obj4 = var1;
    }

    public Object getObj5() {
        return this.obj5;
    }

    public void setObj5(Object var1) {
        this.obj5 = var1;
    }

    public abstract String getEVENT();

    public abstract String getListenEvent();
}
