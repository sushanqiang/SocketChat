package com.yzchat.socket.event;

import com.yzchat.socket.data.PLVChatQuoteVO;
import com.yzchat.socket.data.PLVSocketUserBean;

import java.util.List;



public class PLVSpeakEvent  extends PLVMessageBaseEvent   {
    public static final String EVENT = "SPEAK";
    private String id;
    private long time;
    private String status;
    private String message;
    private String value;
    private PLVSocketUserBean user;
    private List<String> values;
    private PLVChatQuoteVO quote;

    public PLVSpeakEvent() {
    }

    public PLVChatQuoteVO getQuote() {
        return this.quote;
    }

    public void setQuote(PLVChatQuoteVO var1) {
        this.quote = var1;
    }

    public String getEVENT() {
        return "SPEAK";
    }

    public String getId() {
        return this.id;
    }

    public void setId(String var1) {
        this.id = var1;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long var1) {
        this.time = var1;
    }

    public PLVSocketUserBean getUser() {
        return this.user;
    }

    public void setUser(PLVSocketUserBean var1) {
        this.user = var1;
    }

    public List<String> getValues() {
        return this.values;
    }

    public void setValues(List<String> var1) {
        this.values = var1;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String var1) {
        this.status = var1;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String var1) {
        this.message = var1;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String var1) {
        this.value = var1;
    }

    public String toString() {
        return "PLVSpeakEvent{EVENT='SPEAK', id='" + this.id + '\'' + ", time=" + this.time + ", user=" + this.user + ", values=" + this.values + '}';
    }
}
