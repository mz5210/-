package com.mz.snow.utils;

import android.content.Context;
import android.os.Handler;

import com.mz.snow.ui.activity.PersonalDataActivity;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;



/**
 * Created by Administrator on 2017/12/8.
 */

public class MyTipDialog {
    static QMUITipDialog qmuiTipDialog;

    public static void showLodingDailog(Context context, String text) {
        if (qmuiTipDialog != null) {
            qmuiTipDialog.dismiss();
        }
        //            弹框创建
        qmuiTipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(text)
                .create();
//            弹框显示
        qmuiTipDialog.show();
    }

    public static void showSuccessDialog(final Context context, final String text) {
        if (qmuiTipDialog != null) {
            qmuiTipDialog.dismiss();
        }
//                    弹窗创建
        qmuiTipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(text)
                .create();
//                    弹窗显示
        qmuiTipDialog.show();
        closeAllDialog(500);
    }
    public static void showErrorDialog(final Context context, final String text) {
        if (qmuiTipDialog != null) {
            qmuiTipDialog.dismiss();
        }
//                    弹窗创建
        qmuiTipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord(text)
                .create();
//                    弹窗显示
        qmuiTipDialog.show();
        closeAllDialog(500);
    }
    static void closeAllDialog(int time){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (qmuiTipDialog != null) {
                    qmuiTipDialog.dismiss();
                }
            }
        },time);
    }
}
