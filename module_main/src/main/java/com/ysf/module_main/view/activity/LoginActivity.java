package com.ysf.module_main.view.activity;

import android.widget.EditText;
import android.widget.ImageView;

import com.ysf.module_main.R;
import com.ysf.module_main.R2;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R2.id.ci_logon)
    ImageView ciLogon;
    @BindView(R2.id.et_user)
    EditText etUser;
    @BindView(R2.id.et_psd)
    EditText etPsd;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void iniEventData() {

    }

    @OnClick(R2.id.bt_sign_in)
    public void onClickLogin() {

    }

    @OnClick(R2.id.tv_sign_up)
    public void onClickSignUp() {

    }
}
