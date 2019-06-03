package com.rilintech.fragment_301_huxike_android.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.rilintech.fragment_301_huxike_android.R;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by rilintech on 16/4/10.
 */
public class UserActivity extends BaseActivity{

    private TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        titleTextView = (TextView)findViewById(R.id.tv_title);
        titleTextView.setText("个人信息");
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
