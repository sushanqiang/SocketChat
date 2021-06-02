package com.yzchat.socket.data;

public class PLVChatQuoteVO {
    private String userId;
    private String nick;
    private String content;
    private ImageBean image;
    public Object[] objects;

    public PLVChatQuoteVO() {
    }

    public boolean isSpeakMessage() {
        return null != this.content;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String var1) {
        this.userId = var1;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String var1) {
        this.nick = var1;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String var1) {
        this.content = var1;
    }

    public ImageBean getImage() {
        return this.image;
    }

    public void setImage(ImageBean var1) {
        this.image = var1;
    }

    public Object[] getObjects() {
        return this.objects;
    }

    public void setObjects(Object... var1) {
        this.objects = var1;
    }

    public String toString() {
        return "PLVChatQuoteVO{userId='" + this.userId + '\'' + ", content='" + this.content + '\'' + ", nick='" + this.nick + '\'' + ", image='" + this.image + '\'' + '}';
    }

    public static class ImageBean {
        private double height;
        private String url;
        private double width;

        public ImageBean() {
        }

        public double getHeight() {
            return this.height;
        }

        public void setHeight(double var1) {
            this.height = var1;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String var1) {
            this.url = var1;
        }

        public double getWidth() {
            return this.width;
        }

        public void setWidth(double var1) {
            this.width = var1;
        }
    }
}
