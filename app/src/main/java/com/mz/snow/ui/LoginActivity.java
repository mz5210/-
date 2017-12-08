package com.mz.snow.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mz.snow.R;
import com.mz.snow.base.BaseActivity;
import com.mz.snow.model.User;
import com.mz.snow.utils.LogX;
import com.mz.snow.utils.SPUtils;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.edit_username)
    EditText editUsername;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    QMUITipDialog tipDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        initViews();
    }
    void initViews(){
//        状态栏黑色字体
        QMUIStatusBarHelper.setStatusBarLightMode(this);
//        回填账号密码，没有登录过回填""
        editUsername.setText(SPUtils.getUserName(LoginActivity.this));
        editPassword.setText(SPUtils.getPassword(LoginActivity.this));

    }
    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        final String username = editUsername.getText().toString();
        final String password = editPassword.getText().toString();
        if (username.equals("") || username == null || password.equals("") || password == null) {
            Toast.makeText(this, "账号或密码输入不完整！", Toast.LENGTH_SHORT).show();
        } else {
            tipDialog = new QMUITipDialog.Builder(this)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord(getResources().getString(R.string.logining))
                    .create();
            tipDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    login(username, password);
                }
            }, 1000);

        }

    }

    //    bmob查询方法
    void login(final String username, final String password) {
        User bu2 = new User();
        bu2.setUsername(username);
        bu2.setPassword(password);
        bu2.login(new SaveListener<User>() {

            @Override
            public void done(User bmobUser, BmobException e) {
                if (e == null) {
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    tipDialog.dismiss();
                    SPUtils.setUserName(LoginActivity.this,username);
                    SPUtils.setPassword(LoginActivity.this,password);
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                    //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                    //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                } else {
//                    code=101 :账号或密码不正确
                    if (e.getErrorCode() == 101) {
                        Toast.makeText(LoginActivity.this, "账号或密码不正确！请检查重新输入", Toast.LENGTH_SHORT).show();
                    }
                    tipDialog.dismiss();
                    LogX.v("e:" + e.getErrorCode());

                }
            }
        });
    }

    /**
     * 设置返回键不关闭应用,回到桌面
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //启动一个意图,回到桌面
            Intent backHome = new Intent(Intent.ACTION_MAIN);
            backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            backHome.addCategory(Intent.CATEGORY_HOME);
            startActivity(backHome);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
