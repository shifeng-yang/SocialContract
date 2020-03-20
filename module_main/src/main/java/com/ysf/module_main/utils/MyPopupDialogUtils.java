package com.ysf.module_main.utils;

import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.michael.easydialog.EasyDialog;
import com.ysf.module_main.R;

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
                .setGravity(EasyDialog.GRAVITY_BOTTOM)
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

    public static void popupWindow(Fragment fragment, View attachView, String content) {
        if (fragment.getContext() == null) return;
        View bgView = fragment.getLayoutInflater().inflate(R.layout.bg_popupwindow, null);
        TextView tvDes = bgView.findViewById(R.id.tv_des);
        tvDes.setMovementMethod(ScrollingMovementMethod.getInstance());
        tvDes.setText(content);
        EasyDialog easyDialog = new EasyDialog(fragment.getContext())
                .setLayout(bgView)
                .setBackgroundColor(fragment.getResources().getColor(R.color.background_color_black))
                .setLocationByAttachedView(attachView)
                .setGravity(EasyDialog.GRAVITY_BOTTOM)
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
}
