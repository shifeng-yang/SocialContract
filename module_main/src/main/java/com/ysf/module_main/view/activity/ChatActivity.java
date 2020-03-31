package com.ysf.module_main.view.activity;

import android.Manifest;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.ysf.module_main.R;
import com.ysf.module_main.R2;
import com.ysf.module_main.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;

public class ChatActivity extends BaseActivity {
    @BindView(R2.id.fl_container)
    FrameLayout flContainer;
    @BindView(R2.id.iv_delete)
    ImageView ivDelete;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_chat;
    }

    @Override
    protected void iniEventData() {
        initPermission();
        initData();
    }

    private void initData() {
        String username = getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);
        initToolBar(username);
        EaseChatFragment chatFragment = new EaseChatFragment();
        chatFragment.setRightImgClick(ivDelete);
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_container, chatFragment)
                .commit();
    }

    private void initPermission() {
        if (!AndPermission.hasPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)) {
            //申请权限
            AndPermission.with(this)
                    .requestCode(100)
                    .permission(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA)
                    .callback(new PermissionListener() {
                        @Override
                        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {

                        }

                        @Override
                        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                            // 权限申请失败回调。可提示
                            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
                            if (AndPermission.hasAlwaysDeniedPermission(ChatActivity.this, deniedPermissions)) {
                                //用默认的提示语。
                                AndPermission.defaultSettingDialog(ChatActivity.this, 1).show();
                                ToastUtils.show(ChatActivity.this, "请设置相关权限");
                            }
                        }
                    })
                    .start();
        }
    }

}
