package com.mz.snow.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.mz.snow.R;
import com.mz.snow.model.User;
import com.mz.snow.ui.activity.MyFavoriteActivity;
import com.mz.snow.ui.activity.PersonalDataActivity;
import com.mz.snow.ui.activity.SettingActivity;
import com.mz.snow.utils.Constance;
import com.mz.snow.utils.LogX;
import com.mz.snow.utils.MyTipDialog;
import com.mz.snow.utils.SPUtils;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import static android.app.Activity.RESULT_OK;

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

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    User userInfo;
    @BindView(R.id.member)
    ImageView member;

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
        setData();
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
                .addTo(mGroupListView2);

    }

    void setData() {
//        获取当前用户信息
        userInfo = BmobUser.getCurrentUser(User.class);
//        设置用户头像
        RequestOptions optionsUser = new RequestOptions();
        optionsUser.placeholder(R.drawable.default_user_img)
                .error(R.drawable.default_user_img);
        Glide.with(MeFragment.this)
                .load(userInfo.getTouXiangPath())
                .apply(optionsUser)
                .into(meUserImg);
        //        设置用户背景
        RequestOptions optionsBack = new RequestOptions();
        optionsBack.placeholder(R.drawable.me_back_default)
                .error(R.drawable.me_back_default);
        Glide.with(MeFragment.this)
                .load(userInfo.getBackgroudPath())
                .apply(optionsBack)
                .into(myBackground);
//        设置用户昵称（缓存获取）
        userText.setText(userInfo.getNickname());
//        设置用户积分 等级

        if (userInfo.getIntegral() >= 600) {
            Glide.with(MeFragment.this).load(R.mipmap.ic_user_level_6).into(member);
        } else if (userInfo.getIntegral() >= 500) {
            Glide.with(MeFragment.this).load(R.mipmap.ic_user_level_5).into(member);
        } else if (userInfo.getIntegral() >= 400) {
            Glide.with(MeFragment.this).load(R.mipmap.ic_user_level_4).into(member);
        } else if (userInfo.getIntegral() >= 300) {
            Glide.with(MeFragment.this).load(R.mipmap.ic_user_level_3).into(member);
        } else if (userInfo.getIntegral() >= 200) {
            Glide.with(MeFragment.this).load(R.mipmap.ic_user_level_2).into(member);
        } else if (userInfo.getIntegral() >= 100) {
            Glide.with(MeFragment.this).load(R.mipmap.ic_user_level_1).into(member);
        }
    }

    //    从服务器获取数据进行更新
    void refreshUser() {
        User.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    refreshLayout.finishRefresh();
                    SPUtils.setUser(getContext(), s);
                    setData();

                } else {
                    LogX.v(e.toString());
                }
            }

        });
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


    //    点击事件合集
    @OnClick({R.id.my_background, R.id.me_user_img, R.id.user_text, R.id.member})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_background:
                showPhotoBottomSheetGrid(54, 35, Constance.TYPE_BACKGROUD);
                break;
            case R.id.me_user_img:
                showPhotoBottomSheetGrid(1, 1, Constance.TYPE_TOUXIANG);
                break;
            case R.id.user_text:
                break;
            case R.id.member:
                Toast.makeText(getActivity(), "跳转积分界面", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //    显示拍照 图库选择 保存 查看大图的底部选择框
    private void showPhotoBottomSheetGrid(final int jiancai_x, final int jiancai_y, final int Code) {


        final int TAG_paizhao = 0;
        final int TAG_xiangce_xuanze = 1;
        final int TAG_chakandatu = 2;
//        final int TAG_baocun = 3;
        QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(getActivity());
        builder.addItem(R.mipmap.camera, "拍照", TAG_paizhao, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.picture, "从相册选择图片", TAG_xiangce_xuanze, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.eye, "查看大图", TAG_chakandatu, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
//                .addItem(R.mipmap.lower, "保存到本地", TAG_baocun, QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE)
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView) {
                        dialog.dismiss();
                        int tag = (int) itemView.getTag();
                        switch (tag) {
                            case TAG_paizhao:
                                Toast.makeText(getActivity(), "拍照", Toast.LENGTH_SHORT).show();
                                PictureSelector.create(MeFragment.this)
                                        .openCamera(PictureMimeType.ofImage())
                                        .enableCrop(true)// 是否裁剪 true or false
                                        .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                                        .withAspectRatio(jiancai_x, jiancai_y)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                                        .scaleEnabled(true) // 裁剪是否可放大缩小图片 true or false
                                        .compress(true)// 是否压缩 true or false
                                        .forResult(Code);
                                break;
                            case TAG_xiangce_xuanze:
                                Toast.makeText(getActivity(), "从相册选择图片", Toast.LENGTH_SHORT).show();
                                // 进入相册 以下是例子：用不到的api可以不写
                                PictureSelector.create(MeFragment.this)
                                        .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                                        .isCamera(false)// 是否显示拍照按钮 true or false
                                        .enableCrop(true)// 是否裁剪 true or false
                                        .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                                        .withAspectRatio(jiancai_x, jiancai_y)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                                        .scaleEnabled(true) // 裁剪是否可放大缩小图片 true or false
                                        .compress(true)// 是否压缩 true or false
                                        .forResult(Code);//结果回调onActivityResult code

                                break;
                            case TAG_chakandatu:
                                Toast.makeText(getActivity(), "长按图片保存", Toast.LENGTH_SHORT).show();
                                List photoList = new ArrayList();
                                LocalMedia localMedia = new LocalMedia();
                                if (Code == Constance.TYPE_TOUXIANG) {
                                    localMedia.setPath(userInfo.getTouXiangPath());
                                } else {
                                    localMedia.setPath(userInfo.getBackgroudPath());
                                }

                                photoList.add(localMedia);
                                PictureSelector.create(MeFragment.this).externalPicturePreview(0, Constance.IMAGEPATH, photoList);
                                break;
//
//                            case TAG_baocun:
//                                Toast.makeText(getActivity(), "保存到本地", Toast.LENGTH_SHORT).show();
//                                break;
                        }
                    }
                }).build().show();


    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String PicturePath = "";//头像选择之后的文件路径
        if (resultCode == RESULT_OK) {
            // 图片选择结果回调
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            // 例如 LocalMedia 里面返回三种path
            // 1.media.getPath(); 为原图path
            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
            // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
            if (selectList.get(0).isCompressed()) {
                Log.d("MeFragment", "压缩后或裁剪并压缩后：" + selectList.get(0).getCompressPath());
                PicturePath = selectList.get(0).getCompressPath();
            } else if (selectList.get(0).isCut()) {
                Log.d("MeFragment", "裁剪后：" + selectList.get(0).getCutPath());
                PicturePath = selectList.get(0).getCutPath();
            } else {
                Log.d("MeFragment", "原图：" + selectList.get(0).getPath());
                PicturePath = selectList.get(0).getPath();
            }
//            显示正在上传弹框
            MyTipDialog.showLodingDailog(getActivity(), "正在上传");
            final BmobFile bmobFile = new BmobFile(new File(PicturePath));


//            上传头像到bmob素材存储
            bmobFile.uploadblock(new UploadFileListener() {

                @Override
                public void done(BmobException e) {
                    if (e == null) {
//                        没有错误代表上传成功
                        //bmobFile.getFileUrl()--返回的上传文件的完整地址
                        switch (requestCode) {
                            case Constance.TYPE_TOUXIANG:
                                userInfo.setTouXiangPath(bmobFile.getFileUrl());
                                break;
                            case Constance.TYPE_BACKGROUD:
                                userInfo.setBackgroudPath(bmobFile.getFileUrl());
                                break;
                        }
//                        更新用户信息
                        userInfo.update(userInfo.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(final BmobException e) {
                                if (e == null) {
//                                    没有错误代表上传成功
//                                    设置图片
                                    if (requestCode == Constance.TYPE_TOUXIANG) {
                                        Glide.with(MeFragment.this).load(userInfo.getTouXiangPath()).into(meUserImg);
                                    } else if (requestCode == Constance.TYPE_BACKGROUD) {
                                        Glide.with(MeFragment.this).load(userInfo.getBackgroudPath()).into(myBackground);
                                    }

//                                    显示上传成功弹窗
                                    MyTipDialog.showSuccessDialog(getActivity(), "上传成功");
                                    //包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统sd卡权限
                                    PictureFileUtils.deleteCacheDirFile(getContext());
                                } else {
//                                                toast("更新用户信息失败:" + e.getMessage());
                                }
                            }
                        });
                    } else {
//                        有错误  上传失败  e.getMessage()
                        MyTipDialog.showErrorDialog(getActivity(), "上传失败");
                    }

                }

                @Override
                public void onProgress(Integer value) {
                    // 返回的上传进度（百分比）
                }
            });
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
