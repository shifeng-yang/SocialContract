package com.ysf.module_main.model.bean;

public class MyUserManage {
    private String name;
    private String hx_id;
    private String nick;
    private String imgUrl;

  /*  public static MyUserManage getInstance() {
        if (mMyUserManange == null) {
            synchronized (MyUserManage.class) {
                if (mMyUserManange == null) {
                    mMyUserManange = new MyUserManage();
                }
            }
        }
        return mMyUserManange;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHx_id() {
        return hx_id;
    }

    public void setHx_id(String hx_id) {
        this.hx_id = hx_id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
