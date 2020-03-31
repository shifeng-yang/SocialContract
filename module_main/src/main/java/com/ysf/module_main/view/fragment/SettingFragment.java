package com.ysf.module_main.view.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.widget.NormalDialog;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.michael.easydialog.EasyDialog;
import com.ysf.module_main.R;
import com.ysf.module_main.R2;
import com.ysf.module_main.model.MyModel;
import com.ysf.module_main.model.adapter.SettingAdapter;
import com.ysf.module_main.model.bean.SettingBean;
import com.ysf.module_main.utils.MyPopupDialogUtils;
import com.ysf.module_main.utils.SweetDialogUtils;
import com.ysf.module_main.view.activity.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingFragment extends BaseFragment {
    @BindView(R2.id.ib_exit)
    ImageButton ibExit;
    @BindView(R2.id.tv_user)
    TextView tvUser;
    @BindView(R2.id.rv_setting)
    RecyclerView rvSetting;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initEventData() {
        initToolBar("设置");
        ibExit.setVisibility(View.VISIBLE);
        tvUser.setText(EMClient.getInstance().getCurrentUser());
        rvSetting.setLayoutManager(new LinearLayoutManager(mContext));
        List<SettingBean> list = new ArrayList<>();
        list.add(new SettingBean(R.drawable.voice,"通知声音"));
        list.add(new SettingBean(R.drawable.black_name,"黑名单"));
        SettingAdapter settingAdapter = new SettingAdapter(R.layout.item_setting, list);
        rvSetting.setAdapter(settingAdapter);
    }

    @OnClick(R2.id.ib_exit)
    public void onClick() {
        NormalDialog normalDialog = new NormalDialog(mContext);
        normalDialog
                .title("提示")
                .content("你真的真的..要离开吗?")
                .btnText("再想想", "残忍离开!")
                .showAnim(new BounceTopEnter())
                .dismissAnim(new SlideBottomExit())
                .style(NormalDialog.STYLE_TWO)
                .show();
        normalDialog.setOnBtnClickL(
                //取消
                normalDialog::dismiss,
                //确定
                () -> {
                    normalDialog.dismiss();
                    SweetDialogUtils.getSingleInstance().loadingDialog(mActivity);
                    EMClient.getInstance().logout(false, new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            SweetDialogUtils.getSingleInstance().dismiss();
                            //关闭联系人和邀请的数据库
                            MyModel.getInstance().getContractAndinviteManage().close();
                            startActivity(new Intent(mContext, LoginActivity.class));
                            mActivity.finish();
                        }

                        @Override
                        public void onError(int i, String s) {
                            SweetDialogUtils.getSingleInstance().dismiss();
                            MyPopupDialogUtils.popupWindow(SettingFragment.this, ibExit, "都跟你说别退出了...  " + s + "!", EasyDialog.GRAVITY_BOTTOM);
                        }

                        @Override
                        public void onProgress(int i, String s) {

                        }
                    });
                }
        );
    }
}
