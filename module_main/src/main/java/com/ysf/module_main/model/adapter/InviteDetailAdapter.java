package com.ysf.module_main.model.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ysf.module_main.R;
import com.ysf.module_main.model.bean.InviteBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InviteDetailAdapter extends BaseQuickAdapter<InviteBean,BaseViewHolder> {

    public InviteDetailAdapter(int layoutResId, @Nullable List<InviteBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, InviteBean rvInviteBean) {
        baseViewHolder.setText(R.id.tv_title,rvInviteBean.getUserInfo().getName());
        if (rvInviteBean.getStatus() == InviteBean.InvitationStatus.NEW_INVITE) {
            baseViewHolder.getView(R.id.bt_acceptt).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.bt_refuse).setVisibility(View.VISIBLE);
            baseViewHolder.setText(R.id.tv_des,rvInviteBean.getReson());
        } else if (rvInviteBean.getStatus() == InviteBean.InvitationStatus.INVITE_BY_ACCEPT) {
            baseViewHolder.setText(R.id.tv_des,"添加好友成功");
        } else if (rvInviteBean.getStatus() == InviteBean.InvitationStatus.INVITE_BY_DECLINED) {
            baseViewHolder.setText(R.id.tv_des,"因为长得太丑被拒绝了");
        }else if (rvInviteBean.getStatus() == InviteBean.InvitationStatus.INVITE_ACCEPT) {
            baseViewHolder.setText(R.id.tv_des,"添加好友成功");
        }else if (rvInviteBean.getStatus() == InviteBean.InvitationStatus.INVITE_DECLINED) {
            baseViewHolder.setText(R.id.tv_des,"您拒绝了添加好友");
        }
    }
}
