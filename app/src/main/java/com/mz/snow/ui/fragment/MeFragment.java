package com.mz.snow.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mz.snow.R;
import com.mz.snow.model.User;
import com.mz.snow.ui.LoginActivity;
import com.mz.snow.ui.activity.MyFavoriteActivity;
import com.mz.snow.ui.activity.PersonalDataActivity;
import com.mz.snow.ui.activity.SettingActivity;
import com.mz.snow.utils.MyTipDialog;
import com.mz.snow.utils.SPUtils;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {


    @BindView(R.id.my_background)
    ImageView myBackground;
    @BindView(R.id.me_user_img)
    QMUIRadiusImageView meUserImg;
    @BindView(R.id.user_text)
    TextView userText;
    @BindView(R.id.me_group_list1)
    QMUIGroupListView mGroupListView1;
    @BindView(R.id.me_group_list2)
    QMUIGroupListView mGroupListView2;

    Unbinder unbinder;

    QMUITipDialog qmuiTipDialog;

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);

        unbinder = ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //        获取当前用户信息
        User userInfo = BmobUser.getCurrentUser(User.class);
//        设置用户昵称（缓存获取）
        userText.setText(userInfo.getNickname());
    }

    void initViews() {



//        个人资料item创建
        QMUICommonListItemView PersonalData = mGroupListView1.createItemView(getResources().getText(R.string.personal_data));
        PersonalData.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
//        我的收藏item创建
        QMUICommonListItemView MyFavorite = mGroupListView1.createItemView(getResources().getText(R.string.my_favorite));
        MyFavorite.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
//        设置item创建
        QMUICommonListItemView setting = mGroupListView2.createItemView(getResources().getText(R.string.setting));
        setting.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
//        关于item创建
        QMUICommonListItemView about = mGroupListView2.createItemView(getResources().getText(R.string.about));
//        退出账号item创建
        QMUICommonListItemView exit = mGroupListView2.createItemView(getResources().getText(R.string.log_out));
//        添加item到列表1
        QMUIGroupListView
                .newSection(getContext())
                .addItemView(PersonalData, PersonalDataOnClickListener)
                .addItemView(MyFavorite, MyFavoriteOnClickListener)
                .addTo(mGroupListView1);
//        添加item到列表2
        QMUIGroupListView
                .newSection(getContext())
                .setUseTitleViewForSectionSpace(false)
                .addItemView(setting, SettingOnClickListener)
                .addItemView(about, AboutOnClickListener)
                .addItemView(exit, ExitOnClickListener)
                .addTo(mGroupListView2);

    }

    //    个人资料item按钮点击监听
    View.OnClickListener PersonalDataOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(getActivity(), PersonalDataActivity.class));
        }
    };
    //    我的收藏item按钮点击监听
    View.OnClickListener MyFavoriteOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(getActivity(), MyFavoriteActivity.class));
        }
    };
    //    设置item按钮点击监听
    View.OnClickListener SettingOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(getActivity(), SettingActivity.class));
        }
    };
    //    关于item点击监听
    View.OnClickListener AboutOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showAboutDialog();
        }
    };
    //    退出item点击监听
    View.OnClickListener ExitOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showExitMessageDialog();
        }
    };


    //关于dialog
    private void showAboutDialog() {
        new QMUIDialog.MessageDialogBuilder(getActivity())
                .setTitle("关于")
                .setMessage("点开我发现什么都没有是不是很失望,扎心吗兄弟?")
                .addAction("关闭", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    //    退出账号dialog
    private void showExitMessageDialog() {
        final QMUIDialog.CheckBoxMessageDialogBuilder checkBoxMessageDialogBuilder = new QMUIDialog.CheckBoxMessageDialogBuilder(getActivity());
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
                            SPUtils.deleteUserAndPassword(getActivity());
                        }
                        exit();
                    }
                })
                .show();
    }

    //    退出账号方法
    void exit() {
        //            正在退出弹框创建
//        qmuiTipDialog = new QMUITipDialog.Builder(getContext())
//                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
//                .setTipWord(getResources().getString(R.string.exit_ing))
//                .create();
////            正在退出弹框显示
//        qmuiTipDialog.show();
        MyTipDialog.showLodingDailog(getContext(),getResources().getString(R.string.exit_ing));

//            延迟1秒显示退出成功弹窗、清除用户缓存、跳转登录界面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
        MyTipDialog.showSuccessDialog(getContext(),getResources().getString(R.string.exit_success));
//                    清除缓存用户对象
                BmobUser.logOut();
//                    跳转到登录界面
                startActivity(new Intent(getActivity(), LoginActivity.class));
//                    关闭当前activity
                getActivity().finish();
            }
        }, 1000);
    }

    //    点击事件合集
    @OnClick({R.id.my_background, R.id.me_user_img, R.id.user_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_background:
                break;
            case R.id.me_user_img:
                break;
            case R.id.user_text:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
