package com.ysf.module_main.view.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.ysf.module_main.model.adapter.InviteAdapter;
import com.ysf.module_main.utils.SPUtil;

public class ContractInviteChangedReceiver extends BroadcastReceiver {
    private InviteAdapter mAdapter;

    public ContractInviteChangedReceiver(InviteAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //刷新适配器显示红点
        mAdapter.notifyDataSetChanged();
    }
}
