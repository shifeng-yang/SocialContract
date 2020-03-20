package com.ysf.module_main.view.fragment;

import com.ysf.module_main.R;

public class MessageFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initEventData() {
        initToolBar("消息");
    }
}
