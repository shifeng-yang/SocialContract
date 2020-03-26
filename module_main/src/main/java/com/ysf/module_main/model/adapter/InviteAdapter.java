package com.ysf.module_main.model.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ysf.module_main.R;
import com.ysf.module_main.model.bean.RvInviteBean;
import com.ysf.module_main.utils.SPUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InviteAdapter extends BaseQuickAdapter<RvInviteBean,BaseViewHolder> {

    public InviteAdapter(int layoutResId, @Nullable List<RvInviteBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RvInviteBean rvInviteBean) {
        baseViewHolder.setImageResource(R.id.iv_invite_icon,rvInviteBean.getIconUri());
        baseViewHolder.setText(R.id.tv_title,rvInviteBean.getTitle());
        View overlay = baseViewHolder.getView(R.id.iv_overlay);
        //好友/群邀请才考虑要不要显示红点
        if (rvInviteBean.getTitle().equals("好友/群邀请")) {
            boolean isNewInvite = (boolean) SPUtil.getParam(getContext(), SPUtil.IS_NEW_INVITE, false);
            if (isNewInvite) {
                overlay.setVisibility(View.VISIBLE);
            } else {
                overlay.setVisibility(View.GONE);
            }
        }

    }
}
