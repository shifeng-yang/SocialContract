package com.ysf.module_main.view.fragment;

import android.view.View;
import android.widget.ImageButton;
import com.ysf.module_main.R;
import com.ysf.module_main.R2;
import butterknife.BindView;
import butterknife.OnClick;

public class ContractFragment extends BaseFragment {
    @BindView(R2.id.ib_add)
    ImageButton ibAdd;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contract;
    }

    @Override
    protected void initEventData() {
        initToolBar("联系人");
        ibAdd.setVisibility(View.VISIBLE);
    }

    @OnClick(R2.id.ib_add)
    public void onClick() {
    }
}
