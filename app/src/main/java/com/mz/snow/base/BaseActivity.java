package com.mz.snow.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mz.snow.utils.FinishActivityManager;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

/**
 * Created by Administrator on 2017/12/6.
 */

public class BaseActivity extends AppCompatActivity {
    static Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FinishActivityManager.getManager().addActivity(this);
        context = this;
        QMUIStatusBarHelper.translucent(this);
    }

    public static void addStatusBarView(final LinearLayout lineLayout) {
        View view = new View(context);
        // set 宽高
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                QMUIStatusBarHelper.getStatusbarHeight(context));
        view.setLayoutParams(params);
        lineLayout.addView(view);
    }
    public static void addStatusBarView(final RelativeLayout lineLayout) {
        View view = new View(context);
        // set 宽高
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                QMUIStatusBarHelper.getStatusbarHeight(context));
        view.setLayoutParams(params);
        lineLayout.addView(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        FinishActivityManager.getManager().finishActivity(this);

    }
}
