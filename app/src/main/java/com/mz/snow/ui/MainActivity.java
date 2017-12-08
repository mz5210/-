package com.mz.snow.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mz.snow.R;
import com.mz.snow.base.BaseActivity;
import com.mz.snow.model.User;
import com.mz.snow.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

public class MainActivity extends BaseActivity {

    @BindView(R.id.spl_img)
    ImageView splImg;
    @BindView(R.id.spl_back)
    LinearLayout splBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        0  girl      1  ---
        if (SPUtils.getUserType(this) == 0) {
            splBack.setBackgroundResource(R.color.pink);
            splImg.setImageResource(R.drawable.pink_back);
        } else {
            splBack.setBackgroundResource(R.color.like_blue);
            splImg.setImageResource(R.drawable.blue_back);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                User userInfo = BmobUser.getCurrentUser(User.class);
                if(userInfo != null){
                    // 允许用户使用应用
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                }else{
                    //缓存用户对象为空时， 打开登录页面
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }

                finish();
            }
        }, 500);


    }

    @Override
    public void onBackPressed() {

    }
}
