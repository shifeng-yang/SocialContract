package com.ysf.module_main.view.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public abstract class BaseActivity extends SwipeBackActivity {
    protected Context mContext;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        mContext = this;
        //设置导航栏透明
        setImageStatus();
        //处理事件和业务逻辑
        iniEventData();
        //设置边沿滑动的范围
        getSwipeBackLayout().setEdgeSize(360);
        mUnbinder = ButterKnife.bind(this);
    }

    protected abstract int getLayoutID();

    protected abstract void iniEventData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) mUnbinder.unbind();
    }

    private void setImageStatus(){
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //字体设置为浅黑色
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

    }

}
