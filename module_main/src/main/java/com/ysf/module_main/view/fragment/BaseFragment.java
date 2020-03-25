package com.ysf.module_main.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ysf.module_main.R;

import org.jetbrains.annotations.NotNull;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    private Unbinder mUnBinder;
    protected Activity mActivity;
    protected Context mContext;
    private View mView;
    private boolean isInited;

    @Override
    public void onAttach(@NonNull Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) mView = inflater.inflate(getLayoutId(), container,false);
        return mView;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        if (!isHidden()) {
            isInited = true;
            initEventData();
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isInited && !hidden) {
            isInited = true;
            initEventData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnBinder != null) mUnBinder.unbind();
    }

    protected void initToolBar(String title) {
        TextView tv_title = mView.findViewById(R.id.tv_title);
        tv_title.setText(title);
    }

    protected View getEmptyView() {
        return LayoutInflater.from(mContext).inflate(R.layout.empty_view, null);
    }

    protected abstract int getLayoutId();

    protected abstract void initEventData();
}
