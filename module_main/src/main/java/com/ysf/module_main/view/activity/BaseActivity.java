package com.ysf.module_main.view.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.ysf.module_main.R;

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
        //必须在iniEventData()之前初始化 不然控件会报空指针
        mUnbinder = ButterKnife.bind(this);
        mContext = this;
        //设置导航栏透明
        setImageStatus();
        //处理事件和业务逻辑
        iniEventData();
        //设置边沿滑动的范围
        getSwipeBackLayout().setEdgeSize(360);
    }

    protected abstract int getLayoutID();

    protected abstract void iniEventData();

    protected void initToolBar(String title) {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(title);
        findViewById(R.id.ib_back).setOnClickListener(view -> finish());
    }

    protected View getEmptyView() {
        return LayoutInflater.from(mContext).inflate(R.layout.empty_view, null);
    }

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
