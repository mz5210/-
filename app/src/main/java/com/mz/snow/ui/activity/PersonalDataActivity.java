package com.mz.snow.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mz.snow.R;
import com.mz.snow.base.BaseActivity;
import com.mz.snow.model.User;
import com.mz.snow.utils.LogX;
import com.mz.snow.utils.MyTipDialog;
import com.mz.snow.utils.SPUtils;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.UpdateListener;

public class PersonalDataActivity extends BaseActivity {
    @BindView(R.id.topbar)
    QMUITopBar mTopBar;

    @BindView(R.id.star_view)
    View starView;

    @BindView(R.id.personal_data_list1)
    QMUIGroupListView personalDataList1;

    @BindView(R.id.personal_data_list2)
    QMUIGroupListView personalDataList2;

    @BindView(R.id.personal_data_list3)
    QMUIGroupListView personalDataList3;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    //        昵称item
    QMUICommonListItemView nicknameItem;

    //        性别item创建
    QMUICommonListItemView gender;

    //        公历生日item创建
    QMUICommonListItemView birthday;
    //        农历生日item创建
    QMUICommonListItemView lunar_birthday;

    //        星座item创建
    QMUICommonListItemView constellation;
    //        年龄item创建
    QMUICommonListItemView age;
    //        邮箱item创建
    QMUICommonListItemView mailbox;
    //        手机号item创建
    QMUICommonListItemView mobilePhoneNumber;
    //    用户对象
    User userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        ButterKnife.bind(this);

        initViews();
        initTopBar(getResources().getString(R.string.personal_data));
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
        refreshLayout.setEnableLoadmore(false);//是否启用上拉加载功能
        refreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshUser();
            }
        });


//        昵称item创建
        nicknameItem = personalDataList1.createItemView(getResources().getText(R.string.nickname));
        nicknameItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
//        性别item创建
        gender = personalDataList1.createItemView(getResources().getText(R.string.gender));
        gender.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
//        公历生日item创建
        birthday = personalDataList2.createItemView(getResources().getText(R.string.birthday)+"(公历)");
        birthday.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        //        农历生日item创建
        lunar_birthday = personalDataList2.createItemView(getResources().getText(R.string.birthday)+"(农历)");
        lunar_birthday.setDetailText("十月廿三");
        lunar_birthday.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
//        星座item创建
        constellation = personalDataList2.createItemView(getResources().getText(R.string.constellation));
        constellation.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
//        年龄item创建
        age = personalDataList2.createItemView(getResources().getText(R.string.age));
        age.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
//        邮箱item创建
        mailbox = personalDataList3.createItemView(getResources().getText(R.string.mailbox));
        mailbox.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
//        手机号item创建
        mobilePhoneNumber = personalDataList3.createItemView(getResources().getText(R.string.phone_number));
        mobilePhoneNumber.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
//        设置item的文字
        setData();
//         添加item到列表1
        QMUIGroupListView
                .newSection(PersonalDataActivity.this)
                .addItemView(nicknameItem, nicknameItemOnClick)
                .addItemView(gender, genderItemOnClick)
                .addTo(personalDataList1);
//        添加item到列表2
        QMUIGroupListView
                .newSection(PersonalDataActivity.this)
                .setUseTitleViewForSectionSpace(false)
                .addItemView(birthday, null)
                .addItemView(lunar_birthday, null)
                .addItemView(constellation, null)
                .addItemView(age, null)
                .addTo(personalDataList2);
        //        添加item到列表3
        QMUIGroupListView
                .newSection(PersonalDataActivity.this)
                .setUseTitleViewForSectionSpace(false)
                .addItemView(mailbox, null)
                .addItemView(mobilePhoneNumber, null)
                .addTo(personalDataList3);
    }

    View.OnClickListener nicknameItemOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showEditTextDialog(nicknameItem.getText().toString(), nicknameItem.getDetailText().toString());
        }
    };
    View.OnClickListener genderItemOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showGenderDialog(gender.getDetailText().toString());
        }
    };

    //    设置item的文字
    void setData() {
//        获取当前用户信息
        userInfo = User.getCurrentUser(User.class);
        if (userInfo.getNickname() == null) {
            nicknameItem.setDetailText("未设置");
        } else {
            nicknameItem.setDetailText(userInfo.getNickname());
        }

        if (userInfo.getGender() == null) {
            gender.setDetailText("未设置");
        } else {
            gender.setDetailText(userInfo.getGender());
        }

        if (userInfo.getBirthday() == null) {
            birthday.setDetailText("未设置");
        } else {
            birthday.setDetailText(userInfo.getBirthday());
        }

        if (userInfo.getConstellation() == null) {
            constellation.setDetailText("未设置");
        } else {
            constellation.setDetailText(userInfo.getConstellation());
        }

        if (userInfo.getAge() == null) {
            age.setDetailText("未设置");
        } else {
            age.setDetailText(userInfo.getAge());
        }

        if (userInfo.getEmail() == null) {
            mailbox.setDetailText("未设置");
        } else {
            mailbox.setDetailText(userInfo.getEmail());
        }

        if (userInfo.getMobilePhoneNumber() == null) {
            mobilePhoneNumber.setDetailText("未设置");
        } else {
            mobilePhoneNumber.setDetailText(userInfo.getMobilePhoneNumber());
        }
    }

    private void showEditTextDialog(final String title, String text) {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(PersonalDataActivity.this);
        builder.setTitle("修改" + title)
                .setPlaceholder(text)
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CharSequence text = builder.getEditText().getText();
                        if (text != null && text.length() > 0) {
                            dialog.dismiss();
                            if (title.equals("昵称")) {
                                userInfo.setNickname(text.toString());
                            } else if (title.equals("邮箱")) {
                                userInfo.setEmail(text.toString());
                            } else if (title.equals("手机号")) {
                                userInfo.setMobilePhoneNumber(text.toString());
                            }
                            UpdateInfo();
                        } else {
                            Toast.makeText(PersonalDataActivity.this, "请填入昵称", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();


    }

    //    性别修改弹窗
    private void showGenderDialog(String gender) {
        int checkedIndex = gender.equals("男") ? 0 : 1;
        final String[] items = new String[]{"男", "女"};
        new QMUIDialog.CheckableDialogBuilder(PersonalDataActivity.this)
                .setCheckedIndex(checkedIndex)
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userInfo.setGender(items[which]);
                        UpdateInfo();
                        dialog.dismiss();
                    }
                })
                .show();
    }

    //    信息修改之后更新到bmob并显示更新弹窗 延迟1秒显示更新成功
    void UpdateInfo() {
        MyTipDialog.showLodingDailog(PersonalDataActivity.this, "正在更新");
        userInfo.update(userInfo.getObjectId(), new UpdateListener() {
            @Override
            public void done(final BmobException e) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (e == null) {
                            MyTipDialog.showSuccessDialog(PersonalDataActivity.this, "更新成功");
                            setData();
                        } else {
//                                                toast("更新用户信息失败:" + e.getMessage());
                        }
                    }
                }, 1000);

            }
        });
    }

    void refreshUser() {
        User.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    refreshLayout.finishRefresh();
                    SPUtils.setUser(PersonalDataActivity.this,s);
                    setData();

                } else {
                    LogX.v(e.toString());
                }
            }

        });
    }

}

