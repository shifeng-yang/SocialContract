package com.ysf.module_main.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.ysf.module_main.model.bean.InviteBean;
import com.ysf.module_main.model.bean.UserBean;
import com.ysf.module_main.utils.MyConstans;
import com.ysf.module_main.utils.SPUtil;

public class HxEventListener {
    private final LocalBroadcastManager mBroadcastManager;
    private Context mContext;

    public HxEventListener(Context context) {
        mContext = context;
        mBroadcastManager = LocalBroadcastManager.getInstance(mContext);
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {
            @Override
            public void onContactAdded(String s) {
                Log.d("HxEventListener", "掉用了add");
                //增加了联系人时回调此方法
                InviteBean inviteBean = new InviteBean();
                UserBean userBean = new UserBean();
                userBean.setHx_id(s);
                userBean.setName(s);
                userBean.setNick(s);
                userBean.setImgUrl(s);
                inviteBean.setUserInfo(userBean);
                inviteBean.setStatus(InviteBean.InvitationStatus.INVITE_ACCEPT);
                MyModel.getInstance().getContractAndinviteManage().getInviteDao().addInvite(inviteBean);
                MyModel.getInstance().getContractAndinviteManage().getContractDao().addContract(userBean,true);
                //发送联系人变化广播
                mBroadcastManager.sendBroadcast(new Intent(MyConstans.CONTRACT_CHANGED));
                //邀请状态改变显示红点
                SPUtil.setParam(mContext,SPUtil.IS_NEW_INVITE,true);
                //发送邀请状态改变的广播
                mBroadcastManager.sendBroadcast(new Intent(MyConstans.INVITE_CHANGED));
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
                Log.d("HxEventListener", "收到新邀请");
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
                Log.d("HxEventListener", "点了接受=====>" + s);
            }

            @Override
            public void onFriendRequestDeclined(String s) {
                //请求被拒接
                Log.d("HxEventListener", "请求被拒绝了");
                InviteBean inviteBean = new InviteBean();
                UserBean userBean = new UserBean();
                userBean.setHx_id(s);
                userBean.setName(s);
                userBean.setNick(s);
                userBean.setImgUrl(s);
                inviteBean.setUserInfo(userBean);
                inviteBean.setStatus(InviteBean.InvitationStatus.INVITE_BY_DECLINED);
                MyModel.getInstance().getContractAndinviteManage().getInviteDao().addInvite(inviteBean);
                //请求被拒绝
                SPUtil.setParam(mContext,SPUtil.IS_NEW_INVITE,true);
                //发送邀请变化的广播
                mBroadcastManager.sendBroadcast(new Intent(MyConstans.INVITE_CHANGED));
            }
        });
    }

}
