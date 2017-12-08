package com.mz.snow.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.mz.snow.R;
import com.mz.snow.base.BaseActivity;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFavoriteActivity extends BaseActivity {
    @BindView(R.id.topbar)
    QMUITopBar mTopBar;

    @BindView(R.id.star_view)
    View starView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite);
        ButterKnife.bind(this);

        initViews();
        initTopBar(getResources().getString(R.string.my_favorite));
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

    }
}
