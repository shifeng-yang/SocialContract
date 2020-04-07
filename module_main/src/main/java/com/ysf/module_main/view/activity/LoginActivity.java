package com.ysf.module_main.view.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.tencent.bugly.crashreport.CrashReport;
import com.ysf.module_main.R;
import com.ysf.module_main.R2;
import com.ysf.module_main.model.MyModel;
import com.ysf.module_main.model.bean.UserBean;
import com.ysf.module_main.utils.SweetDialogUtils;
import com.ysf.module_main.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {
    @BindView(R2.id.et_user)
    EditText etUser;
    @BindView(R2.id.et_psd)
    EditText etPsd;
    private CompositeDisposable mDisposable;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void iniEventData() {
        //登录界面不响应滑动返回
        getSwipeBackLayout().setEnableGesture(false);
    }

    @OnClick(R2.id.bt_sign_in)
    public void onClickSignIn() {
        String user = etUser.getText().toString().toLowerCase();
        String psd = etPsd.getText().toString();
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(psd)) {
            ToastUtils.show(mContext, "输入不能为空!");
            return;
        }
        SweetDialogUtils.getSingleInstance().loadingDialog(this);
        mDisposable = new CompositeDisposable(
                Observable.create((ObservableOnSubscribe<String>) emitter ->
                        EMClient.getInstance().login(user, psd, new EMCallBack() {
                            @Override
                            public void onSuccess() {
                                SweetDialogUtils.getSingleInstance().dismiss();
                                //bugly上报的用户ID
                                CrashReport.setUserId(mContext,user);
                                EMClient.getInstance().chatManager().loadAllConversations();
                                EMClient.getInstance().groupManager().loadAllGroups();
                                //保存用户信息
                                UserBean userBean = new UserBean();
                                userBean.setHx_id(user);
                                userBean.setName(user);
                                userBean.setNick(user);
                                userBean.setImgUrl(user);
                                //登录成功创建联系人和邀请信息两张表
                                MyModel.getInstance().onSuccessLogin(userBean);
                                MyModel.getInstance().getUserAccountDao().addAccount(userBean);
                                emitter.onNext("欢迎使用!");
                            }

                            @Override
                            public void onError(int i, String s) {
                                SweetDialogUtils.getSingleInstance().dismiss();
                                emitter.onNext("出错了:" + i + "  " + s);
                            }

                            @Override
                            public void onProgress(int i, String s) {

                            }
                        }))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(s -> {
                            //数据异常,取消订阅
                            if (s.equals("-1") && mDisposable != null) {
                                mDisposable.dispose();
                                return;
                            }
                            ToastUtils.show(mContext, s);
                            if (s.equals("欢迎使用!")) {
                                startActivity(new Intent(mContext, MainActivity.class));
                                finish();
                            }
                        })
        );
    }

    @OnClick(R2.id.tv_sign_up)
    public void onClickSignUp() {
        startActivity(new Intent(mContext,RegisteActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) mDisposable.dispose();
    }
}
