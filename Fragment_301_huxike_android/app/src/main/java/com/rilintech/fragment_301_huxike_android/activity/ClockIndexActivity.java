package com.rilintech.fragment_301_huxike_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.rilintech.fragment_301_huxike_android.R;
import com.rilintech.fragment_301_huxike_android.adapter.ClockIndexListViewAdapter;
import com.rilintech.fragment_301_huxike_android.bean.ClockModel;
import com.rilintech.fragment_301_huxike_android.db.ClockDBUtil;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by YANG on 15/10/21.
 */
public class ClockIndexActivity extends BaseActivity implements View.OnClickListener {

    //标题文字
    private TextView tv_title;
    //添加闹铃
    private Button mAddClock;
    //所有闹铃列表
    private ListView mListView;
    //所有闹铃集合
    private List<ClockModel> mListClock;
    //时间集合
    private List<String> mListTime;
    //标签集合
    private List<String> mListTag;
    //开关集合
    private List<String> mListSwitch;
    //mListClock加载器
    private ClockIndexListViewAdapter adapter;
    //记录当前的ListView的位置
    private int CURRENT_LISTVIEW_ITEM_POSITION = 0;
    //日期
    private TextView tv_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_index);

        getAllClock();
        initView();


    }

    private void initView() {
        tv_date = (TextView) findViewById(R.id.tv_date);
        getSysDate();

        tv_title = (TextView) findViewById(R.id.layout_title).findViewById(R.id.tv_title);
        tv_title.setText(getResources().getString(R.string.clock));

        mAddClock = (Button) findViewById(R.id.add_clock);
        mAddClock.setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.clock_listview);
        adapter = new ClockIndexListViewAdapter(this,
                mListTime,
                mListTag,
                mListSwitch);
        mListView.setAdapter(adapter);

    }

    /**
     * 获取所有的闹铃,并拿到时间/标签/开关的集合
     */
    private void getAllClock() {
        ClockDBUtil clockDBUtil = new ClockDBUtil(getApplicationContext());
        clockDBUtil.openDataBase();
        mListClock = clockDBUtil.getAllClock();
        clockDBUtil.closeDataBase();

        mListTime = new ArrayList<>();
        mListTag = new ArrayList<>();
        mListSwitch = new ArrayList<>();

        for (ClockModel info : mListClock) {
            mListTime.add(info.getTime());
            mListTag.add(info.getTag());
            mListSwitch.add(info.getmSwitch());
        }

    }


    /**
     * 添加闹钟
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_clock:
                Intent intent = new Intent(this, ClockInfoActivity.class);
                intent.putExtra("isAdd", true);
                startActivity(intent);
                break;
            case R.id.iv_back:
                this.finish();
                break;

            default:

                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        getAllClock();
        adapter = new ClockIndexListViewAdapter(this,
                mListTime,
                mListTag,
                mListSwitch);
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //回到原来的位置
        mListView.setSelection(CURRENT_LISTVIEW_ITEM_POSITION);

        MobclickAgent.onResume(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        //得到当前ListView可见部分的第一个位置
        CURRENT_LISTVIEW_ITEM_POSITION = mListView.getFirstVisiblePosition();
        MobclickAgent.onPause(this);
    }

    private void getSysDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String date = format.format(new Date());
        tv_date.setText(date);
    }


}
