package com.mz.snow.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mz.snow.R;
import com.mz.snow.model.User;
import com.mz.snow.ui.activity.PersonalDataActivity;
import com.mz.snow.utils.MyTipDialog;
import com.mz.snow.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.luck.picture.lib.tools.DebugUtil.log;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1Fragment extends Fragment {


    @BindView(R.id.jifen)
    EditText jifen;
    @BindView(R.id.button)
    Button button;
    Unbinder unbinder;

    public Tab1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
//        User user = BmobUser.getCurrentUser(User.class);
//        user.setIntegral(Integer.parseInt(jifen.getText().toString()));

        fetchUserInfo(Integer.parseInt(jifen.getText().toString()));
    }
//    更新单项方法
    private void fetchUserInfo(int a) {
        User userInfo = BmobUser.getCurrentUser(User.class);
        userInfo.setIntegral(a);
        userInfo.update(userInfo.getObjectId(), new UpdateListener() {
            @Override
            public void done(final BmobException e) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (e == null) {


                        } else {
//                         toast("更新用户信息失败:" + e.getMessage());
                        }
                    }
                }, 1000);

            }
        });
    }
}
