package com.ysf.module_main.view.activity;

import android.text.TextUtils;
import android.widget.EditText;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.ysf.module_main.R;
import com.ysf.module_main.R2;
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
        String user = etUser.getText().toString();
        String psd = etPsd.getText().toString();
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(psd)) {
            ToastUtils.show(mContext,"输入不能为空!");
            return;
        }
        mDisposable = new CompositeDisposable(
                Observable.create((ObservableOnSubscribe<String>) emitter ->
                        EMClient.getInstance().login(user, psd, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        emitter.onNext("登录成功");
                    }

                    @Override
                    public void onError(int i, String s) {
                        emitter.onNext(s);
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
                            ToastUtils.show(mContext,s);
                        })
        );
    }

    @OnClick(R2.id.tv_sign_up)
    public void onClickSignUp() {
        String user = etUser.getText().toString();
        String psd = etPsd.getText().toString();
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(psd)) {
            ToastUtils.show(mContext,"输入不能为空!");
            return;
        }
        mDisposable = new CompositeDisposable(
                Observable.create((ObservableOnSubscribe<String>) emitter -> {
                    try {
                        EMClient.getInstance().createAccount(user,psd);
                        emitter.onNext("注册成功");
                    }catch (HyphenateException e) {
                        emitter.onNext(e.getDescription());
                        e.printStackTrace();
                    }
                })
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(s -> {
                            //数据异常,取消订阅
                            if (s.equals("-1") && mDisposable != null) {
                                mDisposable.dispose();
                                return;
                            }
                            ToastUtils.show(mContext,s);
                        })
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) mDisposable.dispose();
    }
}
