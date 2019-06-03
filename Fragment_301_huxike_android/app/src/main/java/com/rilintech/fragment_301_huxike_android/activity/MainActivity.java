package com.rilintech.fragment_301_huxike_android.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.rilintech.fragment_301_huxike_android.R;
import com.rilintech.fragment_301_huxike_android.bean.WeatherModel;
import com.rilintech.fragment_301_huxike_android.fragment.FragmentHome;
import com.rilintech.fragment_301_huxike_android.fragment.PersonCenterFragment;
import com.rilintech.fragment_301_huxike_android.http.UserModel;
import com.rilintech.fragment_301_huxike_android.utils.BitmapHelper;
import com.rilintech.fragment_301_huxike_android.utils.DateUtil;
import com.rilintech.fragment_301_huxike_android.utils.HttpService;
import com.rilintech.fragment_301_huxike_android.utils.ProgressUtil;
import com.rilintech.fragment_301_huxike_android.utils.SharedPrefsUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import me.iwf.photopicker.PhotoPickerActivity;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    //个人中心
    private LinearLayout personalCenter;
    private ImageView iv_personal_center;
    private TextView tv_personal_center;
    private PersonCenterFragment fragmentPersonalCenter;
    //首页
    private LinearLayout home;
    private ImageView iv_home;
    private TextView tv_home;
    private FragmentHome fragmentHome;
    //当前选中页标记
    public static int INTERFACE = 0;
    //标题
    private TextView tv_title;
    //返回键
    private ImageView iv_back;
    //上滑进入首页
    private TextView tvHint;
    //返回键按下时间
    private long mExitTime;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();


    private String pm25;
    private String mLocation;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //友盟更新服务
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);

        setContentView(R.layout.activity_main);
        initView();

        //传送pm2.5
        date = DateUtil.getDateFormat(new Date());
        upload();
    }

    private void upload() {
        initBaiDuMap();
    }

    /**
     * 方法名：initBaiDuMap
     * 功能：初始化百度地图
     * 参数：
     */
    private void initBaiDuMap() {
        //声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        //注册监听函数
        mLocationClient.registerLocationListener(myListener);
        //设置请求参数
        initLocation();
        //启动请求
        mLocationClient.start();

        if (mLocationClient != null && mLocationClient.isStarted())
            mLocationClient.requestLocation();
        else
            Log.d("Location", "locClient is null or not started");
    }


    private void initView() {

        //设置pull_door_text
        tvHint = (TextView) findViewById(R.id.tv_hint);
        Animation ani = new AlphaAnimation(0f, 1f);
        ani.setDuration(1500);
        ani.setRepeatMode(Animation.REVERSE);
        ani.setRepeatCount(Animation.INFINITE);
        tvHint.startAnimation(ani);

        personalCenter = (LinearLayout) findViewById(R.id.ll_personal_center);
        iv_personal_center = (ImageView) findViewById(R.id.iv_personal_center);
        tv_personal_center = (TextView) findViewById(R.id.tv_personal_center);
        home = (LinearLayout) findViewById(R.id.ll_home);
        iv_home = (ImageView) findViewById(R.id.iv_home);
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_title = (TextView) findViewById(R.id.layout_title).findViewById(R.id.tv_title);
        iv_back = (ImageView) findViewById(R.id.layout_title).findViewById(R.id.iv_back);
        iv_back.setVisibility(View.GONE);
        personalCenter.setOnClickListener(this);
        home.setOnClickListener(this);

        setDefaultFragment();
    }

    /**
     * 设置默认的显示界面
     */
    private void setDefaultFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragmentHome = new FragmentHome();
        transaction.add(R.id.id_content, fragmentHome);
        INTERFACE = 1;
        setBottomBarStyle(INTERFACE);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 隐藏所有Fragment
        hideFragment(transaction);
        switch (v.getId()) {
            case R.id.ll_personal_center:
                if (fragmentPersonalCenter == null) {
                    fragmentPersonalCenter = new PersonCenterFragment();
                    transaction.add(R.id.id_content, fragmentPersonalCenter);
                } else {
                    transaction.show(fragmentPersonalCenter);
                }
                INTERFACE = 2;
                setBottomBarStyle(INTERFACE);
                break;
            case R.id.ll_home:
                if (fragmentHome == null) {
                    fragmentHome = new FragmentHome();
                    transaction.add(R.id.id_content, fragmentHome);
                } else {
                    transaction.show(fragmentHome);
                }

                INTERFACE = 1;
                setBottomBarStyle(INTERFACE);
                break;
            case R.id.iv_back:

                break;
        }
        transaction.commit();
    }


    //隐藏Fragment
    private void hideFragment(FragmentTransaction transaction) {
        if (fragmentPersonalCenter != null) {
            transaction.hide(fragmentPersonalCenter);
        } else if (fragmentHome != null) {
            transaction.hide(fragmentHome);
        }
    }

    /**
     * 改变标题和bottomBar选中效果
     *
     * @param b
     */
    private void setBottomBarStyle(int b) {

        switch (b) {
            case 1:
                iv_home.setImageResource(R.drawable.ic_home_pressed);
                tv_home.setTextColor(getResources().getColor(R.color.mBlue));
                iv_personal_center.setImageResource(R.drawable.ic_personal_center_normal);
                tv_personal_center.setTextColor(getResources().getColor(R.color.subscribe_item_normal_stroke));
                tv_title.setText(getResources().getString(R.string.app_name));
                break;
            case 2:
                iv_home.setImageResource(R.drawable.ic_home_normal);
                tv_home.setTextColor(getResources().getColor(R.color.subscribe_item_normal_stroke));
                iv_personal_center.setImageResource(R.drawable.ic_personal_center_pressed);
                tv_personal_center.setTextColor(getResources().getColor(R.color.mBlue));
                tv_title.setText(getResources().getString(R.string.personal_center));
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "在按一次退出",
                        Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                this.finish();
            }
            return true;
        }
        //拦截MENU按钮点击事件，让他无任何操作
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && requestCode == 200) {
            if (data != null) {
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                final Bitmap imageBitmap = BitmapHelper.compressBitmap(photos.get(0), 100, 100);
                UserModel userModel = new UserModel();
                userModel.UploadHeadImage(activity, SharedPrefsUtil.getValue(activity, "data", "userId", null), photos.get(0), new UserModel.ResultEventCallBack() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fragmentPersonalCenter.headImageView.setImageBitmap(imageBitmap);
                            }
                        });
                    }

                    @Override
                    public void onError(int statusCode, Header[] headers, String responseString) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ProgressUtil.getInstance().showUnDoneProgress(activity, "网络出错");
                            }
                        });
                    }
                });

            }
        }
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

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                return;
            } else {
                //接收返回的结果参数
                StringBuffer sb = new StringBuffer(256);
                sb.append(location.getCity());
                String city = sb.toString();
                Log.i("weather", city);
                if (city != null) {
                    String cityName = city.substring(0, city.length() - 1);
                    mLocation = cityName;

                    QueryAsyncTask asyncTask = new QueryAsyncTask();
                    asyncTask.execute();

                } else {
                   ;
                }
            }
        }
    }

    /**
     * dingwei
     */
    private void initLocation() {
        //设置请求参数
        LocationClientOption option = new LocationClientOption();
        //设置定位模式，默认高精度，设置定位模式，高精度，低功耗，仅设备
        //option.setLocationMode(LocationMode.Hight_Accuracy);
        //返回的定位结果是百度经纬度，默认gcj02，设置返回的定位结果坐标系（bd09、bd09ll、gcj02）
        option.setCoorType("bd09ll");
        //可选，设置发起定位请求的间隔时间为5000ms，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setScanSpan(0);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //返回的定位结果包含手机机头的方向
        option.setNeedDeviceDirect(true);
        //可选，默认false,设置是否使用gps
        option.setOpenGps(false);
        //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setLocationNotify(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);
        //可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.setIgnoreKillProcess(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setEnableSimulateGps(false);

        mLocationClient.setLocOption(option);
    }


    private class QueryAsyncTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object... params) {

            return HttpService.getWebWeather(mLocation);
        }

        @Override
        protected void onPostExecute(Object result) {

            if (result != null) {

                todayParse((WeatherModel) result);

            } else {

            }
            super.onPostExecute(result);
        }
    }

    /**
     * 方法名：todayParse
     * 功能：今天天气
     *
     * @param weather
     */
    private void todayParse(WeatherModel weather) {
        this.pm25 = weather.getPm25();

        UserModel userModel = new UserModel();
        userModel.uploadPm(activity, mLocation, pm25);

    }
}
