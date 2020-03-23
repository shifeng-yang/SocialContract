package com.ysf.module_main.view.activity;

import android.content.Intent;
import android.os.SystemClock;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.ysf.module_main.R;
import com.ysf.module_main.R2;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {
    @BindView(R2.id.tv_skip_time)
    TextView tvSkipTime;
    private CompositeDisposable mDisposable;
    int time = 2;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void iniEventData() {
        getSwipeBackLayout().setEnableGesture(false);
        mDisposable = new CompositeDisposable(
                Observable.create((ObservableOnSubscribe<String>) emitter -> {
                    while (time >= 0) {
                        SystemClock.sleep(1000);
                        emitter.onNext(time + "");
                        time--;
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
                            tvSkipTime.setText("跳过:" + s + "s");
                            if (s.equals("0")) {
                                if (EMClient.getInstance().isLoggedInBefore()) {
                                    startActivity(new Intent(mContext,MainActivity.class));
                                } else {
                                    startActivity(new Intent(mContext, LoginActivity.class));
                                }
                                finish();
                            }
                        })
        );
    }

    @OnClick(R2.id.rl_skip)
    public void onClick() {
        //点击了跳过则不再接收事件
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        if (EMClient.getInstance().isLoggedInBefore()) {
            startActivity(new Intent(mContext,MainActivity.class));
        } else {
            startActivity(new Intent(mContext, LoginActivity.class));
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) mDisposable.dispose();
    }
}
