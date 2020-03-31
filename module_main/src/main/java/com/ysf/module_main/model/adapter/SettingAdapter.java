package com.ysf.module_main.model.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ysf.module_main.R;
import com.ysf.module_main.model.bean.SettingBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SettingAdapter extends BaseQuickAdapter<SettingBean,BaseViewHolder> {

    public SettingAdapter(int layoutResId, @Nullable List<SettingBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SettingBean data) {
        baseViewHolder.setImageResource(R.id.iv_head,data.getImg());
        baseViewHolder.setText(R.id.tv_title,data.getTitle());
    }
}
