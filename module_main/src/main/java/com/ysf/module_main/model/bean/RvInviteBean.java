package com.ysf.module_main.model.bean;

public class RvInviteBean {
    private int iconUri;
    private String title;

    public RvInviteBean(int iconUri, String title) {
        this.iconUri = iconUri;
        this.title = title;
    }

    public int getIconUri() {
        return iconUri;
    }

    public void setIconUri(int iconUri) {
        this.iconUri = iconUri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
