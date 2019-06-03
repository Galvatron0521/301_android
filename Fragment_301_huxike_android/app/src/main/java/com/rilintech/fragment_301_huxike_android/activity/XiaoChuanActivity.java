package com.rilintech.fragment_301_huxike_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.rilintech.fragment_301_huxike_android.R;
import com.rilintech.fragment_301_huxike_android.adapter.MyViewPagerAdapter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rilintech on 16/4/9.
 */
public class XiaoChuanActivity extends BaseActivity{

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private List<View> viewList;
    private List<Integer> lists;
    private TextView numberTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiaochuan);
        numberTextView  = (TextView)findViewById(R.id.textnumber);
        viewList = new ArrayList<>();
        lists = new ArrayList<>();

        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        Intent intent = getIntent();
        if(intent.getStringExtra("info").equals("xiaochuanzhishi")) {
            lists.add(R.drawable.xiaochuantech1);
            lists.add(R.drawable.xiaochuantech2);
            lists.add(R.drawable.xiaochuantech3);
            lists.add(R.drawable.xiaochuantech4);
            lists.add(R.drawable.xiaochuantech5);
            lists.add(R.drawable.xiaochuantech6);
            lists.add(R.drawable.xiaochuantech7);
            lists.add(R.drawable.xiaochuantech8);
            lists.add(R.drawable.xiaochuantech9);
            lists.add(R.drawable.xiaochuantech10);

            for (int i = 0; i < 10; i++) {
                viewList.add(LayoutInflater.from(this).inflate(R.layout.pager_image1, null));
            }
        }else if(intent.getStringExtra("info").equals("xiaochuanjibingguanli")){
            lists.add(R.drawable.xiaochuantech11);
            lists.add(R.drawable.xiaochuantech12);

            for (int i = 0; i < 2; i++) {
                viewList.add(LayoutInflater.from(this).inflate(R.layout.pager_image1, null));
            }
        }else if(intent.getStringExtra("info").equals("xiaochuanyaowu")){
            lists.add(R.drawable.xiaochuantech13);
            lists.add(R.drawable.xiaochuantech14);

            for (int i = 0; i < 2; i++) {
                viewList.add(LayoutInflater.from(this).inflate(R.layout.pager_image1, null));
            }
        }

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        // 1.设置幕后item的缓存数目
        viewPager.setOffscreenPageLimit(2);
        myViewPagerAdapter = new MyViewPagerAdapter(viewList, lists);
        viewPager.setAdapter(myViewPagerAdapter);

        numberTextView.setText("1/"+String.valueOf(viewList.size()));

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                numberTextView.setText(String.valueOf(position+1)+"/"+String.valueOf(viewList.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 返回键
     * @param view
     */
    public void onClick(View view){
        this.finish();
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
