package com.yzchat.socket.data;

import java.io.Serializable;


public class PLVSocketUserBean implements Serializable {
    private PLVAuthorizationBean authorization;
    private String actor;
    private boolean banned;
    private String channelId;
    private String clientIp;
    private String nick;
    private String pic;
    private String roomId;
    private String uid;
    private String userId;
    private String userSource;
    private String userType;

    public PLVSocketUserBean() {
    }

    public PLVAuthorizationBean getAuthorization() {
        return this.authorization;
    }

    public void setAuthorization(PLVAuthorizationBean var1) {
        this.authorization = var1;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String var1) {
        this.actor = var1;
    }

    public boolean isBanned() {
        return this.banned;
    }

    public void setBanned(boolean var1) {
        this.banned = var1;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public void setChannelId(String var1) {
        this.channelId = var1;
    }

    public String getClientIp() {
        return this.clientIp;
    }

    public void setClientIp(String var1) {
        this.clientIp = var1;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String var1) {
        this.nick = var1;
    }

    public String getPic() {
        return  this.pic;
    }

    public void setPic(String var1) {
        this.pic = var1;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public void setRoomId(String var1) {
        this.roomId = var1;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String var1) {
        this.uid = var1;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String var1) {
        this.userId = var1;
    }

    public String getUserSource() {
        return this.userSource;
    }

    public void setUserSource(String var1) {
        this.userSource = var1;
    }

    public String getUserType() {
        return this.userType;
    }

    public void setUserType(String var1) {
        this.userType = var1;
    }

    public String toString() {
        return "PLVSocketUserBean{authorization=" + this.authorization + ", actor='" + this.actor + '\'' + ", banned=" + this.banned + ", channelId='" + this.channelId + '\'' + ", clientIp='" + this.clientIp + '\'' + ", nick='" + this.nick + '\'' + ", pic='" + this.pic + '\'' + ", roomId='" + this.roomId + '\'' + ", uid='" + this.uid + '\'' + ", userId='" + this.userId + '\'' + ", userSource='" + this.userSource + '\'' + ", userType='" + this.userType + '\'' + '}';
    }
}
