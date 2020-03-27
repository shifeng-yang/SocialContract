package com.ysf.module_main.view.activity;

import android.content.Intent;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.ysf.module_main.R;
import com.ysf.module_main.R2;
import com.ysf.module_main.model.MyModel;
import com.ysf.module_main.model.adapter.InviteDetailAdapter;
import com.ysf.module_main.model.bean.InviteBean;
import com.ysf.module_main.utils.MyConstans;
import com.ysf.module_main.utils.SPUtil;
import com.ysf.module_main.utils.ToastUtils;
import com.ysf.module_main.view.broadcast.InviteStatusChangedReceiver;

import java.util.List;

import butterknife.BindView;

public class InviteDetilActivity extends BaseActivity {
    @BindView(R2.id.rv_invite_detail)
    RecyclerView rvInviteDetail;
    private LocalBroadcastManager mBroadcastManager;
    private InviteStatusChangedReceiver mReceiver;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_invite_detail;
    }

    @Override
    protected void iniEventData() {
        initToolBar("好友申请");
        mBroadcastManager = LocalBroadcastManager.getInstance(mContext);
        rvInviteDetail.setLayoutManager(new LinearLayoutManager(mContext));
        rvInviteDetail.addItemDecoration(new DividerItemDecoration(mContext,RecyclerView.VERTICAL));
        List<InviteBean> inviteList = MyModel.getInstance().getContractAndinviteManage().getInviteDao().getInvite();
        InviteDetailAdapter inviteDetailAdapter = new InviteDetailAdapter(R.layout.item_invite_detail, inviteList);
        inviteDetailAdapter.addChildClickViewIds(R.id.bt_acceptt,R.id.bt_refuse);
        inviteDetailAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            String username = inviteList.get(position).getUserInfo().getName();
            if (view.getId() == R.id.bt_acceptt) {
                EMClient.getInstance().contactManager().asyncAcceptInvitation(username, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        MyModel.getInstance().getContractAndinviteManage().getInviteDao().updateInviteStatus(username,InviteBean.InvitationStatus.INVITE_ACCEPT);
                        //成功处理了这条消息隐藏红点
                        SPUtil.setParam(mContext,SPUtil.IS_NEW_INVITE,false);
                    }

                    @Override
                    public void onError(int i, String s) {
                        runOnUiThread(() -> ToastUtils.show(mContext,s));
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });

            } else if (view.getId() == R.id.bt_refuse) {
                EMClient.getInstance().contactManager().asyncDeclineInvitation(username, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        MyModel.getInstance().getContractAndinviteManage().getInviteDao().updateInviteStatus(username,InviteBean.InvitationStatus.INVITE_DECLINED);
                        //成功处理了这条消息隐藏红点
                        SPUtil.setParam(mContext,SPUtil.IS_NEW_INVITE,false);
                        //HxEventListener里没有监听的方法,这里手动发送一条广播刷新本页面和红点显示
                        mBroadcastManager.sendBroadcast(new Intent(MyConstans.INVITE_CHANGED));
                    }

                    @Override
                    public void onError(int i, String s) {runOnUiThread(() -> ToastUtils.show(mContext,i + ": " + s));
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });

            }
        });
        inviteDetailAdapter.setEmptyView(getEmptyView());
        rvInviteDetail.setAdapter(inviteDetailAdapter);
        //注册邀请状态改变的广播
        mReceiver = new InviteStatusChangedReceiver(inviteDetailAdapter);
        mBroadcastManager.registerReceiver(mReceiver,new IntentFilter(MyConstans.INVITE_CHANGED));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //页面销毁了则没必要继续接受广播
        mBroadcastManager.unregisterReceiver(mReceiver);
    }
}
