package com.mz.snow.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import com.mz.snow.R;
import com.mz.snow.base.BaseActivity;
import com.mz.snow.ui.HomeActivity;
import com.mz.snow.ui.LoginActivity;
import com.mz.snow.utils.FinishActivityManager;
import com.mz.snow.utils.MyTipDialog;
import com.mz.snow.utils.SPUtils;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopBar;

    @BindView(R.id.star_view)
    View starView;
    @BindView(R.id.setting_group_list1)
    QMUIGroupListView settingGroupList1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        initViews();
        initTopBar(getResources().getString(R.string.setting));
    }

    private void initTopBar(String titleText) {
        starView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, QMUIStatusBarHelper.getStatusbarHeight(this)));
        starView.setBackgroundColor(getResources().getColor(R.color.like_blue));
        mTopBar.setBackgroundColor(getResources().getColor(R.color.like_blue));
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTopBar.setTitle(titleText);
    }

    void initViews() {
//        退出账号item创建
        QMUICommonListItemView exit = settingGroupList1.createItemView(getResources().getText(R.string.log_out));
//        添加item到列表2
        QMUIGroupListView
                .newSection(SettingActivity.this)
                .setUseTitleViewForSectionSpace(false)
                .addItemView(exit, ExitOnClickListener)
                .addTo(settingGroupList1);
    }

    //    退出item点击监听
    View.OnClickListener ExitOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showExitMessageDialog();
        }
    };

    //    退出账号dialog
    private void showExitMessageDialog() {
        final QMUIDialog.CheckBoxMessageDialogBuilder checkBoxMessageDialogBuilder = new QMUIDialog.CheckBoxMessageDialogBuilder(SettingActivity.this);
        checkBoxMessageDialogBuilder.setTitle("退出后是否删除账号信息?")
                .setMessage("删除账号信息").setChecked(true)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("退出", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        if (checkBoxMessageDialogBuilder.isChecked()) {
                            SPUtils.deleteUserAndPassword(SettingActivity.this);
                        }
                        exit();
                    }
                })
                .show();
    }

    //    退出账号方法
    void exit() {
//       显示退出成功弹窗、清除用户缓存、跳转登录界面
        MyTipDialog.showSuccessDialog(SettingActivity.this, getResources().getString(R.string.exit_success));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

//                    清除缓存用户对象
                BmobUser.logOut();
//                    跳转到登录界面
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
//                    关闭当前activity
                FinishActivityManager.getManager().finishActivity();
                FinishActivityManager.getManager().finishActivity(HomeActivity.class);

            }
        }, 1000);
    }
}
