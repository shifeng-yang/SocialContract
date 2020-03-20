package com.ysf.module_main.utils;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;

import com.ysf.module_main.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 为什么有了TipDialog还要SweetDialog?
 * 因为ta很甜..┗( ▔, ▔ )┛
 */
public class SweetDialogUtils {
    private static SweetDialogUtils mSweetDialogUtils;
    private SweetAlertDialog mSweetAlertDialog;

    public static SweetDialogUtils getSingleInstance() {
        if (mSweetDialogUtils == null) {
            synchronized (SweetDialogUtils.class) {
                if (mSweetDialogUtils == null) {
                    mSweetDialogUtils = new SweetDialogUtils();
                }
            }
        }
        return mSweetDialogUtils;
    }

    public void loadingDialog(Activity context) {
        dismiss();
        mSweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Loading");
        mSweetAlertDialog.setCanceledOnTouchOutside(false);
        mSweetAlertDialog.show();
        final int[] i = {-1};
        CountDownTimer countDownTimer = new CountDownTimer(800 * 40, 800) {
            @Override
            public void onTick(long millisUntilFinished) {
                i[0]++;
                switch (i[0]) {
                    case 0:
                        mSweetAlertDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.blue_btn_bg_color));
                        break;
                    case 1:
                        mSweetAlertDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.material_deep_teal_50));
                        break;
                    case 2:
                        mSweetAlertDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.success_stroke_color));
                        break;
                    case 3:
                        mSweetAlertDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.material_deep_teal_20));
                        break;
                    case 4:
                        mSweetAlertDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.material_blue_grey_80));
                        break;
                    case 5:
                        mSweetAlertDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.warning_stroke_color));
                        break;
                    case 6:
                        mSweetAlertDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.success_stroke_color));
                        //重新开始
                        i[0] = -1;
                        break;
                    default:
                }
            }

            @Override
            public void onFinish() {
                i[0] = -1;
            }
        }.start();

        mSweetAlertDialog.setOnCancelListener(dialog -> {
            countDownTimer.cancel();
            dismiss();
            context.finish();
        });
    }

    public void confirmCancel(Context context, String title, String content) {
        dismiss();
        mSweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setCancelText("再想想")
                .setConfirmText("我确定")
                .showCancelButton(true)
                 .setCancelClickListener(sDialog -> {
                     sDialog.setTitleText("Cancelled!")
                             .setContentText("Your imaginary file is safe :)")
                             .setConfirmText("OK")
                             .showCancelButton(false)
                             .setCancelClickListener(null)
                             .setConfirmClickListener(null)
                             .changeAlertType(SweetAlertDialog.ERROR_TYPE);

                            /* sDialog.dismiss();
                            new SweetAlertDialog(SampleActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Cancelled!")
                                    .setContentText("Your imaginary file is safe :)")
                                    .setConfirmText("OK")
                                    .show();*/
                })
                .setConfirmClickListener(sDialog -> sDialog.setTitleText("Deleted!")
                        .setContentText("Your imaginary file has been deleted!")
                        .setConfirmText("OK")
                        .showCancelButton(false)
                        .setCancelClickListener(null)
                        .setConfirmClickListener(null)
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE));
        mSweetAlertDialog.show();
    }

    public void error(Context context, String content) {
        dismiss();
        mSweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("错误")
                .setContentText(content)
                .setConfirmText("确定");
        mSweetAlertDialog.show();
    }

    public void success(Context context) {
        dismiss();
        mSweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("成功")
                .showContentText(false);
        mSweetAlertDialog.show();
        new Handler().postDelayed(() -> {
            if (mSweetAlertDialog.isShowing()) {
                mSweetAlertDialog.dismiss();
            }
        }, 800);
    }

    public void dismiss() {
        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing()) {
            mSweetAlertDialog.dismiss();
        }
    }
}
