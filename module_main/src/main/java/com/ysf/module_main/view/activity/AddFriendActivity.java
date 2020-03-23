package com.ysf.module_main.view.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.ysf.module_main.R;
import com.ysf.module_main.R2;
import com.ysf.module_main.utils.ToastUtils;
import butterknife.BindView;
import butterknife.OnClick;

public class AddFriendActivity extends BaseActivity {
    @BindView(R2.id.et_friend)
    EditText etFriend;
    @BindView(R2.id.tv_username)
    TextView tvUsername;
    @BindView(R2.id.ll_friend)
    LinearLayout llFriend;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_add_friend;
    }

    @Override
    protected void iniEventData() {
        initToolBar("添加好友");
        etFriend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")) {
                    llFriend.setVisibility(View.VISIBLE);
                    tvUsername.setText(charSequence.toString());
                } else {
                    llFriend.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @OnClick(R2.id.bt_add)
    public void onClick() {
        String username = etFriend.getText().toString();
        if (TextUtils.isEmpty(username)) return;
        try {
            EMClient.getInstance().contactManager().addContact(username,"加个好友别");
            ToastUtils.show(mContext,"请求已发送!");
        } catch (HyphenateException e) {
            ToastUtils.show(mContext,e.getDescription() + "!");
            e.printStackTrace();
        }
    }
}
