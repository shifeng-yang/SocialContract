package com.ysf.module_main.view.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ysf.module_main.model.MyModel;
import com.ysf.module_main.model.adapter.InviteDetailAdapter;

public class InviteStatusChangedReceiver extends BroadcastReceiver {
    private InviteDetailAdapter mAdapter;

    public InviteStatusChangedReceiver(InviteDetailAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ContractAddedReceiver", "邀请状态改变Receiver");
        //刷新适配器更新邀请结果
        mAdapter.replaceData(MyModel.getInstance().getContractAndinviteManage().getInviteDao().getInvite());
        mAdapter.notifyDataSetChanged();

    }
}
