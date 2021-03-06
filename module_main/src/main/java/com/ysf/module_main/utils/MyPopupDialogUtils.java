package com.ysf.module_main.utils;

import android.app.Activity;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.michael.easydialog.EasyDialog;
import com.ysf.module_main.R;
import com.ysf.module_main.view.activity.AddFriendActivity;

/**
 * 气泡对话框
 */
public class MyPopupDialogUtils {

    public static void popupWindow(Activity activity,View attachView,String content,int orientation) {
        View bgView = activity.getLayoutInflater().inflate(R.layout.bg_popupwindow, null);
        TextView tvDes = bgView.findViewById(R.id.tv_des);
        tvDes.setMovementMethod(ScrollingMovementMethod.getInstance());
        tvDes.setText(content);
        EasyDialog easyDialog = new EasyDialog(activity)
                .setLayout(bgView)
                .setBackgroundColor(activity.getResources().getColor(R.color.background_color_black))
                .setLocationByAttachedView(attachView)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_X, 1000, -600, 100, -50, 50, 0)
                .setAnimationAlphaShow(1000, 0.3f, 1.0f)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_X, 500, -50, 800)
                .setAnimationAlphaDismiss(500, 1.0f, 0.0f)
                .setTouchOutsideDismiss(true)
                .setGravity(orientation)
                .setMatchParent(false)
                .setMarginLeftAndRight(24, 24)
                .setOutsideColor(activity.getResources().getColor(R.color.transparent))
                .show();

        Button dismiss = bgView.findViewById(R.id.bt_dismiss);
        dismiss.setOnClickListener(v -> {
            if (easyDialog != null) {
                easyDialog.dismiss();
            }
        });
    }

    public static void popupWindow(Fragment fragment, View attachView, String content,int orientation) {
        if (fragment.getContext() == null) return;
        View bgView = fragment.getLayoutInflater().inflate(R.layout.bg_popupwindow, null);
        TextView tvDes = bgView.findViewById(R.id.tv_des);
        tvDes.setMovementMethod(ScrollingMovementMethod.getInstance());
        tvDes.setText(content);
        EasyDialog easyDialog = new EasyDialog(fragment.getContext())
                .setLayout(bgView)
                .setBackgroundColor(fragment.getResources().getColor(R.color.background_color_black))
                .setGravity(orientation)
                .setLocationByAttachedView(attachView)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_X, 1000, -600, 100, -50, 50, 0)
                .setAnimationAlphaShow(1000, 0.3f, 1.0f)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_X, 500, -50, 800)
                .setAnimationAlphaDismiss(500, 1.0f, 0.0f)
                .setTouchOutsideDismiss(true)
                .setMatchParent(true)
                .setMarginLeftAndRight(24, 24)
                .setOutsideColor(fragment.getResources().getColor(R.color.transparent))
                .show();

        Button dismiss = bgView.findViewById(R.id.bt_dismiss);
        dismiss.setOnClickListener(v -> {
            if (easyDialog != null) {
                easyDialog.dismiss();
            }
        });
    }

    public static void popupWindowContract(Activity activity,View attachView,int orientation) {
        View bgView = activity.getLayoutInflater().inflate(R.layout.bg_popup_contract, null);
        EasyDialog easyDialog = new EasyDialog(activity)
                .setLayout(bgView)
                .setBackgroundColor(activity.getResources().getColor(R.color.background_color_black))
                .setLocationByAttachedView(attachView)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_X, 1000, -600, 100, -50, 50, 0)
                .setAnimationAlphaShow(1000, 0.3f, 1.0f)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_X, 500, -50, 800)
                .setAnimationAlphaDismiss(500, 1.0f, 0.0f)
                .setTouchOutsideDismiss(true)
                .setGravity(orientation)
                .setMatchParent(false)
                .setMarginLeftAndRight(24, 24)
                .setOutsideColor(activity.getResources().getColor(R.color.transparent))
                .show();
        //添加好友
        bgView.findViewById(R.id.tv_friend).setOnClickListener(view -> {
            easyDialog.dismiss();
            Intent intent = new Intent(activity, AddFriendActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        });
        //添加群
        bgView.findViewById(R.id.tv_group).setOnClickListener(view -> {

        });
        //创建群
        bgView.findViewById(R.id.tv_create_group).setOnClickListener(view -> {

        });
    }

    public static void popupWindowDelete(Activity activity, View attachView,int orientation) {
        View bgView = activity.getLayoutInflater().inflate(R.layout.bg_popup_delete, null);
        EasyDialog easyDialog = new EasyDialog(activity)
                .setLayout(bgView)
                .setBackgroundColor(activity.getResources().getColor(R.color.background_color_black))
                .setGravity(orientation)
                .setLocationByAttachedView(attachView.findViewById(R.id.tv_contract_name))
                .setAnimationTranslationShow(EasyDialog.DIRECTION_X, 1000, 600,-100,50,-50,0)
                .setAnimationAlphaShow(1000, 0.3f, 1.0f)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_X, 500, 50,-800)
                .setAnimationAlphaDismiss(500, 1.0f, 0.0f)
                .setTouchOutsideDismiss(true)
                .setMatchParent(false)
                .setMarginLeftAndRight(24, 24)
                .setOutsideColor(activity.getResources().getColor(R.color.transparent))
                .show();
        bgView.findViewById(R.id.tv_delete).setOnClickListener(view -> {
            easyDialog.dismiss();
            if (mListener == null) return;
            mListener.onDeleteClickListener();
        });
    }

    private static OnDeleteClickListener mListener;
    public static void setOnDeleteClickListener(OnDeleteClickListener listener) {
        mListener = listener;
    }

    public static interface OnDeleteClickListener {
        void onDeleteClickListener();
    }
}
