package com.ysf.module_main.model;

import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.ysf.module_main.model.bean.InviteBean;
import com.ysf.module_main.model.bean.UserBean;
import com.ysf.module_main.utils.MyConstans;
import com.ysf.module_main.utils.SPUtil;

public class HxEventListen {
    private final LocalBroadcastManager mBroadcastManager;
    private Context mContext;

    public HxEventListen(Context context) {
        mContext = context;
        mBroadcastManager = LocalBroadcastManager.getInstance(mContext);
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {
            @Override
            public void onContactAdded(String s) {
                //增加了联系人时回调此方法
                UserBean userBean = new UserBean();
                userBean.setHx_id(s);
                userBean.setName(s);
                userBean.setNick(s);
                userBean.setImgUrl(s);
                MyModel.getInstance().getContractAndinviteManage().getContractDao().addContract(userBean,true);
                //发送联系人变化广播
                mBroadcastManager.sendBroadcast(new Intent(MyConstans.CONTRACT_CHANGED));
            }

            @Override
            public void onContactDeleted(String s) {
                //被删除时回调此方法
                MyModel.getInstance().getContractAndinviteManage().getContractDao().deletContract(s);
                MyModel.getInstance().getContractAndinviteManage().getInviteDao().deleteInvite(s);
                //发送联系人变化广播
                mBroadcastManager.sendBroadcast(new Intent(MyConstans.CONTRACT_CHANGED));
            }

            @Override
            public void onContactInvited(String s, String reason) {
                //收到好友邀请
                InviteBean inviteBean = new InviteBean();
                UserBean userBean = new UserBean();
                userBean.setHx_id(s);
                userBean.setName(s);
                userBean.setNick(s);
                userBean.setImgUrl(s);
                inviteBean.setUserInfo(userBean);
                inviteBean.setReson(reason);
                inviteBean.setStatus(InviteBean.InvitationStatus.NEW_INVITE);
                MyModel.getInstance().getContractAndinviteManage().getInviteDao().addInvite(inviteBean);
                //有新邀请显示红点
                SPUtil.setParam(mContext,SPUtil.IS_NEW_INVITE,true);
                //发送邀请变化的广播
                mBroadcastManager.sendBroadcast(new Intent(MyConstans.INVITE_CHANGED));
            }

            @Override
            public void onFriendRequestAccepted(String s) {
                //请求接受
                InviteBean inviteBean = new InviteBean();
                UserBean userBean = new UserBean();
                userBean.setHx_id(s);
                userBean.setName(s);
                userBean.setNick(s);
                userBean.setImgUrl(s);
                inviteBean.setUserInfo(userBean);
                inviteBean.setStatus(InviteBean.InvitationStatus.INVITE_ACCEPT_BY_PEER);
                MyModel.getInstance().getContractAndinviteManage().getInviteDao().addInvite(inviteBean);
                //邀请被接受隐藏红点
                SPUtil.setParam(mContext,SPUtil.IS_NEW_INVITE,false);
                //发送邀请变化的广播
                mBroadcastManager.sendBroadcast(new Intent(MyConstans.INVITE_CHANGED));
            }

            @Override
            public void onFriendRequestDeclined(String s) {
                //邀请被接受隐藏红点
                SPUtil.setParam(mContext,SPUtil.IS_NEW_INVITE,false);
                //发送邀请变化的广播
                mBroadcastManager.sendBroadcast(new Intent(MyConstans.INVITE_CHANGED));
            }
        });
    }
}