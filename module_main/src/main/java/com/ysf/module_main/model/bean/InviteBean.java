package com.ysf.module_main.model.bean;

public class InviteBean {
    private UserBean userInfo;
    private GroupBean groupInfo;
    private String reson;
    private InvitationStatus status;


    public enum InvitationStatus {
        DEFAULT,
        NEW_INVITE,
        INVITE_ACCEPT,
        INVITE_ACCEPT_BY_PEER
    }

    public UserBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserBean userInfo) {
        this.userInfo = userInfo;
    }

    public GroupBean getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(GroupBean groupInfo) {
        this.groupInfo = groupInfo;
    }

    public String getReson() {
        return reson;
    }

    public void setReson(String reson) {
        this.reson = reson;
    }

    public InvitationStatus getStatus() {
        return status;
    }

    public void setStatus(InvitationStatus status) {
        this.status = status;
    }
}
