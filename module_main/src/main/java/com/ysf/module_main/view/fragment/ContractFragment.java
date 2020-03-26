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
import com.ysf.module_main.model.MyModel;
import com.ysf.module_main.model.adapter.ContractAdapter;
import com.ysf.module_main.model.adapter.InviteAdapter;
import com.ysf.module_main.model.bean.RvInviteBean;
import com.ysf.module_main.model.bean.UserBean;
import com.ysf.module_main.utils.MyConstans;
import com.ysf.module_main.utils.MyPopupDialogUtils;
import com.ysf.module_main.utils.SPUtil;
import com.ysf.module_main.view.activity.ChatActivity;
import com.ysf.module_main.view.activity.InviteDetilActivity;
import com.ysf.module_main.view.broadcast.ContractAddedReceiver;
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
    @BindView(R2.id.rv_contract)
    RecyclerView rvContract;
    private List<RvInviteBean> mRvInviteBeanList = new ArrayList<>();
    private LocalBroadcastManager mBroadcastManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contract;
    }

    @Override
    protected void initEventData() {
        initToolBar("联系人");
        //用来注册广播接收者
        mBroadcastManager = LocalBroadcastManager.getInstance(mContext);
        //显示右侧加号
        ibAdd.setVisibility(View.VISIBLE);
        rvInvite.setLayoutManager(new LinearLayoutManager(mContext));
        rvInvite.addItemDecoration(new DividerItemDecoration(mContext, RecyclerView.VERTICAL));
        mRvInviteBeanList.add(new RvInviteBean(R.drawable.icon_friend_deep, "好友/群邀请"));
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
        mBroadcastManager.registerReceiver(new ContractInviteChangedReceiver(inviteAdapter), new IntentFilter(MyConstans.INVITE_CHANGED));

        //联系人列表
        rvContract.setLayoutManager(new LinearLayoutManager(mContext));
        rvContract.addItemDecoration(new DividerItemDecoration(mContext,RecyclerView.VERTICAL));
        List<UserBean> contracts = MyModel.getInstance().getContractAndinviteManage().getContractDao().getContracts();
        ContractAdapter contractAdapter = new ContractAdapter(R.layout.item_contract, contracts);
        contractAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(mContext, ChatActivity.class);
            intent.putExtra("username",contracts.get(position).getName());
            startActivity(intent);
        });
        rvContract.setAdapter(contractAdapter);
        mBroadcastManager.registerReceiver(new ContractAddedReceiver(contractAdapter),new IntentFilter(MyConstans.CONTRACT_CHANGED));
    }

    @OnClick(R2.id.ib_add)
    public void onClick() {
        MyPopupDialogUtils.popupWindowContract(mActivity, ibAdd, EasyDialog.GRAVITY_BOTTOM);
    }

}
