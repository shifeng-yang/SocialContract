package com.ysf.module_main.model.bean;

public class InviteBean {
    private UserBean userInfo;
    private GroupBean groupInfo;
    private String reson;
    private InvitationStatus status;

    public enum InvitationStatus {
        //默认状态
        DEFAULT,
        //新邀请
        NEW_INVITE,
        //请求被拒绝
        INVITE_BY_DECLINED,
        //请求被接受
        INVITE_BY_ACCEPT,
        //拒绝了请求
        INVITE_DECLINED,
        //接受了请求
        INVITE_ACCEPT
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
