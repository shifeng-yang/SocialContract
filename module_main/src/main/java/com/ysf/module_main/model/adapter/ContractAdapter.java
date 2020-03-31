package com.ysf.module_main.model.adapter;

import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ysf.module_main.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ContractAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public ContractAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String data) {
        baseViewHolder.setText(R.id.tv_contract_name,data);
    }
}
