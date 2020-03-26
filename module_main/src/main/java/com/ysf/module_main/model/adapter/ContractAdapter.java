package com.ysf.module_main.model.adapter;

import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ysf.module_main.R;
import com.ysf.module_main.model.bean.UserBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ContractAdapter extends BaseQuickAdapter<UserBean,BaseViewHolder> {

    public ContractAdapter(int layoutResId, @Nullable List<UserBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, UserBean data) {
        Log.d("ContractAdapter", data.getName());
        baseViewHolder.setText(R.id.tv_contract_name,data.getName());
    }
}
