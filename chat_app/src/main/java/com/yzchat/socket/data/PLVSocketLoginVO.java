package com.yzchat.socket.data;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class PLVSocketLoginVO implements Serializable {
    private static final String CHANNEL_ID = "channelId";
    private static final String USER_ID = "userId";
    private static final String NICK_NAME = "nickName";
    private static final String USER_TYPE = "userType";
    private static final String AVATAR_URL = "avatarUrl";
    private static final String VALUES = "values";
    private static final String ACTOR = "actor";
    private static final String BG_COLOR = "bgColor";
    private static final String F_COLOR = "fColor";
    private static final String AUTHORIZATION = "authorization";
    private static final String MIC_ID = "micId";
    private static final String ROOM_ID = "roomId";
    private static final String TYPE = "type";
    private static final String GET_CUP = "getCup";
    private String userId;
    private String channelId;
    private String nickName;
    private String avatarUrl;
    private String userType;
    private String actor;
    private PLVAuthorizationBean authorization;
    private String micId;

    private PLVSocketLoginVO() {
    }



    private static PLVSocketLoginVO create(String var0, String var1, String var2, String var3, String var4) {
        return create(var0, var1, var2, var3, "https://s1.videocc.net/face.png", var4);
    }

    private static PLVSocketLoginVO create(String var0, String var1, String var2, String var3, String var4, String var5) {
        return create(var0, var1, var2, var3, var4, (String) null, var5);
    }

    private static PLVSocketLoginVO create(String var0, String var1, String var2, String var3, String var4, String var5, String var6) {
        return create(var0, var1, var2, var3, var4, var5, (PLVAuthorizationBean) null, var6);
    }

    private static PLVSocketLoginVO create(String var0, String var1, String var2, String var3, String var4, String var5, PLVAuthorizationBean var6, String var7) {

        PLVSocketLoginVO var8 = new PLVSocketLoginVO();
        var8.setUserId(var0);
        var8.setChannelId(var1);
        var8.setNickName(TextUtils.isEmpty(var2) ? "学生" : var2);
        var8.setUserType(TextUtils.isEmpty(var3) ? "student" : var3);
        var8.setAvatarUrl(TextUtils.isEmpty(var4) ? "https://s1.videocc.net/face.png" : var4);
        var8.setActor(var5);
        var8.setAuthorization(var6);
        var8.setMicId(var7);
        return var8;
    }


    public String createLoginInfo(String var1) {
        try {
            JSONObject var2 = new JSONObject();
            JSONArray var3 = new JSONArray();

            var2.put("EVENT", "LOGIN");

            var3.put(0, this.nickName);
            var3.put(1, this.avatarUrl);
            var3.put(2, this.userId);
            var2.put("values", var3);
            if (this.authorization != null && !TextUtils.isEmpty(this.authorization.getActor())) {
                JSONObject var4 = new JSONObject();
                var4.put("actor", this.authorization.getActor());
                var4.put("bgColor", this.authorization.getBgColor());
                var4.put("fColor", this.authorization.getfColor());
                var2.put("authorization", var4);
            }

            if (!TextUtils.isEmpty(this.actor)) {
                var2.put("actor", this.actor);
            }

            var2.put("micId", this.micId);
            var2.put("roomId", var1);
            var2.put("channelId", this.channelId);
            var2.put("type", this.userType);
            var2.put("getCup", 1);
            return var2.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUserId() {
        return this.userId;
    }

    private void setUserId(String var1) {
        this.userId = var1;
    }

    public String getChannelId() {
        return this.channelId;
    }

    private void setChannelId(String var1) {
        this.channelId = var1;
    }

    public String getNickName() {
        return this.nickName;
    }

    private void setNickName(String var1) {
        this.nickName = var1;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    private void setAvatarUrl(String var1) {
        this.avatarUrl = var1;
    }

    public String getUserType() {
        return this.userType;
    }

    public void setUserType(String var1) {
        this.userType = var1;
    }

    public String getActor() {
        return this.actor;
    }

    private void setActor(String var1) {
        this.actor = var1;
    }

    public PLVAuthorizationBean getAuthorization() {
        return this.authorization;
    }

    public void setAuthorization(PLVAuthorizationBean var1) {
        this.authorization = var1;
    }

    public String getMicId() {
        return this.micId;
    }

    public void setMicId(String var1) {
        this.micId = var1;
    }

    public String toString() {
        return "PLVSocketLoginVO{userId='" + this.userId + '\'' + ", channelId='" + this.channelId + '\'' + ", nickName='" + this.nickName + '\'' + ", avatarUrl='" + this.avatarUrl + '\'' + ", userType='" + this.userType + '\'' + ", actor='" + this.actor + '\'' + ", authorization=" + this.authorization + '}';
    }
}
