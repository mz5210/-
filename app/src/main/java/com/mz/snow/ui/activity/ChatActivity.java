package com.mz.snow.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.mz.snow.R;
import com.mz.snow.base.BaseActivity;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends BaseActivity {
    @BindView(R.id.topbar)
    QMUITopBar mTopBar;

    @BindView(R.id.star_view)
    View starView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        initViews();
        initTopBar("和**聊天");
    }

    private void initTopBar(String titleText) {
        starView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, QMUIStatusBarHelper.getStatusbarHeight(this)));
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
    }
}
