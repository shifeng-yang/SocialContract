package com.ysf.module_main.view.activity;

import android.content.Intent;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
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
            String user_id = inviteList.get(position).getUserInfo().getHx_id();
            if (view.getId() == R.id.bt_acceptt) {
                try {
                    EMClient.getInstance().contactManager().acceptInvitation(user_id);
                    MyModel.getInstance().getContractAndinviteManage().getInviteDao().updateInviteStatus(user_id,InviteBean.InvitationStatus.INVITE_ACCEPT);
                    ToastUtils.show(mContext,"添加成功");
                    //成功处理了这条消息隐藏红点
                    SPUtil.setParam(mContext,SPUtil.IS_NEW_INVITE,false);
                } catch (HyphenateException e) {
                    ToastUtils.show(mContext,e.getDescription());
                    e.printStackTrace();
                }
            } else if (view.getId() == R.id.bt_refuse) {
                try {
                    EMClient.getInstance().contactManager().declineInvitation(inviteList.get(position).getUserInfo().getHx_id());
                    MyModel.getInstance().getContractAndinviteManage().getInviteDao().updateInviteStatus(user_id,InviteBean.InvitationStatus.INVITE_DECLINED);
                    ToastUtils.show(mContext,"拒绝成功");
                    //成功处理了这条消息隐藏红点
                    SPUtil.setParam(mContext,SPUtil.IS_NEW_INVITE,false);
                    //HxEventListener里没有监听的方法,这里手动发送一条广播刷新本页面和红点显示
                    mBroadcastManager.sendBroadcast(new Intent(MyConstans.INVITE_CHANGED));
                } catch (HyphenateException e) {
                    ToastUtils.show(mContext,e.getDescription());
                    e.printStackTrace();
                }
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
