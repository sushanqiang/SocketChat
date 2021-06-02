package com.yzchat.socket.data;

import android.text.TextUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class PolyvLocalMessage  extends PolyvBaseHolder {
    private String id;
    public static final int SENDVALUE_ILLEGALPARAM = -7;
    public static final int SENDVALUE_BANIP = -6;
    public static final int SENDVALUE_CLOSEROOM = -4;
    public static final int SENDVALUE_NOONLINE = -3;
    public static final int SENDVALUE_EXCEPTION = -2;
    public static final int SENDVALUE_PARAMNULL = -1;
    public static final int SENDVALUE_SUCCESS = 1;


    private String speakMessage;
    private int chatType=0;//聊天类型 0：默认聊天室 1：私聊
    /**
     * userId : 354
     * msgType : 2
     * phoneModel : android
     * msg : 积极
     * roomId : 147
     */

    private String userId;
    private String msgType;
    private String phoneModel;
    private String msg;
    private String roomId;

    public String getMsg() {
        return msg;
    }

    public PolyvLocalMessage setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public static final String sendValueToDescribe(int var0) {
        String var1 = "unknown";
        switch(var0) {
            case -7:
                var1 = "非法参数";
                break;
            case -6:
                var1 = "已被禁言";
            case -5:
            case 0:
            default:
                break;
            case -4:
                var1 = "房间已关闭";
                break;
            case -3:
                var1 = "已离线";
                break;
            case -2:
                var1 = "内部异常";
                break;
            case -1:
                var1 = "参数异常";
                break;
            case 1:
                var1 = "成功";
        }

        return var1;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String var1) {
        this.id = var1;
    }

    public PolyvLocalMessage(String var1) {
        this.speakMessage = var1;
    }

    public int getChatType() {
        return chatType;
    }

    public PolyvLocalMessage setChatType(int chatType) {
        this.chatType = chatType;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public PolyvLocalMessage setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getMsgType() {
        return msgType;
    }

    public PolyvLocalMessage setMsgType(String msgType) {
        this.msgType = msgType;
        return this;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public PolyvLocalMessage setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
        return this;
    }

    public String getRoomId() {
        return roomId;
    }

    public PolyvLocalMessage setRoomId(String roomId) {
        this.roomId = roomId;
        return this;
    }

    public String getSpeakMessage() {
        return this.speakMessage;
    }
    public String getMessage() {
        return  (TextUtils.isEmpty(this.speakMessage))?this.msg:this.speakMessage;
    }
    public void setSpeakMessage(String var1) {
        this.speakMessage = var1;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SendValue {
    }
}
