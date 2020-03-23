package com.ysf.module_main.view.activity;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ysf.module_main.R;
import com.ysf.module_main.R2;
import butterknife.BindView;

public class InviteDetilActivity extends BaseActivity {
    @BindView(R2.id.rv_invite_detail)
    RecyclerView rvInviteDetail;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_invite_detail;
    }

    @Override
    protected void iniEventData() {
        initToolBar("好友申请");
        rvInviteDetail.setLayoutManager(new LinearLayoutManager(mContext));
        rvInviteDetail.addItemDecoration(new DividerItemDecoration(mContext,RecyclerView.VERTICAL));

    }

}
