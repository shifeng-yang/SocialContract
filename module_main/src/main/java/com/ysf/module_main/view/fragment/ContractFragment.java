package com.ysf.module_main.view.fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.ImageButton;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.michael.easydialog.EasyDialog;
import com.ysf.module_main.R;
import com.ysf.module_main.R2;
import com.ysf.module_main.model.adapter.InviteAdapter;
import com.ysf.module_main.model.bean.RvInviteBean;
import com.ysf.module_main.utils.MyConstans;
import com.ysf.module_main.utils.MyPopupDialogUtils;
import com.ysf.module_main.utils.SPUtil;
import com.ysf.module_main.view.activity.InviteDetilActivity;
import com.ysf.module_main.view.broadcast.ContractInviteChangedReceiver;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ContractFragment extends BaseFragment {
    @BindView(R2.id.ib_add)
    ImageButton ibAdd;
    @BindView(R2.id.rv_invite)
    RecyclerView rvInvite;
    private List<RvInviteBean> mRvInviteBeanList = new ArrayList<>();
    private LocalBroadcastManager mBroadcastManager;
    private ContractInviteChangedReceiver mReceiver;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contract;
    }

    @Override
    protected void initEventData() {
        initToolBar("联系人");
        //显示右侧加号
        ibAdd.setVisibility(View.VISIBLE);
        rvInvite.setLayoutManager(new LinearLayoutManager(mContext));
        rvInvite.addItemDecoration(new DividerItemDecoration(mContext, RecyclerView.VERTICAL));
        mRvInviteBeanList.add(new RvInviteBean(R.drawable.icon_friend_deep, "好友请求"));
        mRvInviteBeanList.add(new RvInviteBean(R.drawable.icon_group_deep, "群组"));
        InviteAdapter inviteAdapter = new InviteAdapter(R.layout.item_invite, mRvInviteBeanList);
        inviteAdapter.setOnItemClickListener((adapter, view, position) -> {
            switch (position) {
                case 0:
                    //查看了邀请信息则隐藏红点
                    SPUtil.setParam(mContext, SPUtil.IS_NEW_INVITE, false);
                    inviteAdapter.notifyDataSetChanged();
                    startActivity(new Intent(mContext, InviteDetilActivity.class));
                    break;
                case 1:
                    break;
                default:
            }

        });
        rvInvite.setAdapter(inviteAdapter);
        mBroadcastManager = LocalBroadcastManager.getInstance(mContext);
        mReceiver = new ContractInviteChangedReceiver(inviteAdapter);
        mBroadcastManager.registerReceiver(mReceiver, new IntentFilter(MyConstans.INVITE_CHANGED));
    }

    @OnClick(R2.id.ib_add)
    public void onClick() {
        MyPopupDialogUtils.popupWindowContract(mActivity, ibAdd, EasyDialog.GRAVITY_BOTTOM);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mBroadcastManager.unregisterReceiver(mReceiver);
    }
}
