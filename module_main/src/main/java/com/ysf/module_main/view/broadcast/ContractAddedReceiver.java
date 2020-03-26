package com.ysf.module_main.view.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ysf.module_main.model.MyModel;
import com.ysf.module_main.model.adapter.ContractAdapter;

public class ContractAddedReceiver extends BroadcastReceiver {
    private ContractAdapter mAdapter;

    public ContractAddedReceiver(ContractAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ContractAddedReceiver", "联系人增加了Receiver");
        //刷新适配器显示刚添加的联系人
        mAdapter.replaceData(MyModel.getInstance().getContractAndinviteManage().getContractDao().getContracts());
        mAdapter.notifyDataSetChanged();

    }
}
