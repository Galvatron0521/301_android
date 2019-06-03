package com.rilintech.fragment_301_huxike_android.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.rilintech.fragment_301_huxike_android.R;
import com.rilintech.fragment_301_huxike_android.bean.WeatherModel;
import com.rilintech.fragment_301_huxike_android.utils.DataUtil;
import com.rilintech.fragment_301_huxike_android.utils.HttpService;
import com.rilintech.fragment_301_huxike_android.utils.NetworkUtils;
import com.umeng.analytics.MobclickAgent;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class WeatherActivity extends Activity implements OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private EditText dialogCity;
    private String cityName;
    private boolean flag = true;
    //private ProgressDialog progressDialog;
    private LinearLayout ll_yes, ll_no;
    private TextView tv_city, tv_today, tv_attr1, tv_attr2, tv_attr3, tv_noresult;
    private TextView tv_date1, tv_date2, tv_wd1, tv_wd2;
    private ImageView ima, ima1, ima2;
    private Button  btn_other;
    private TextView tv_pm25,btn_return;
    private SwipeRefreshLayout mSwipeLayout;
    private WeatherModel info;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private RelativeLayout weather_bg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        initView();

        if (NetworkUtils.getNetworkState(this) == NetworkUtils.NETWORN_NONE) {
            Toast.makeText(this, R.string.net_wrok_error, Toast.LENGTH_SHORT).show();
            flag = true;
            return;
        }

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

    private void initLocation() {
        //设置请求参数
        LocationClientOption option = new LocationClientOption();
        //设置定位模式，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
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
                    cityName = city.substring(0, city.length() - 1);
                    QueryAsyncTask asyncTask = new QueryAsyncTask();
                    asyncTask.execute();
                } else {
                    Toast.makeText(WeatherActivity.this, "请打开定位权限，无法查询天气", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    /**
     * 方法名：initView
     * 功能：初始化控件
     * 参数：
     */
    private void initView() {

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        weather_bg=(RelativeLayout)findViewById(R.id.weather_bg);
        tv_pm25 = (TextView) findViewById(R.id.tv_pm25);

        tv_city = (TextView) this.findViewById(R.id.ws2_tv_city);
        tv_attr1 = (TextView) this.findViewById(R.id.ws2_tv_attr1);
        tv_attr2 = (TextView) this.findViewById(R.id.ws2_tv_attr2);
        tv_attr3 = (TextView) this.findViewById(R.id.ws2_tv_attr3);

        tv_date1 = (TextView) this.findViewById(R.id.ws2_tv_1_date);
        tv_date2 = (TextView) this.findViewById(R.id.ws2_tv_2_date);
        tv_wd1 = (TextView) this.findViewById(R.id.ws2_tv_1_wd);
        tv_wd2 = (TextView) this.findViewById(R.id.ws2_tv_2_wd);

        ima1 = (ImageView) this.findViewById(R.id.ws2_iv_1_image);
        ima2 = (ImageView) this.findViewById(R.id.ws2_iv_2_image);

        btn_return = (TextView)findViewById(R.id.ws2_btn_return);
        btn_return.setOnClickListener(this);

        try {
            FileInputStream stream = this.openFileInput("weather.s");
            ObjectInputStream ois = new ObjectInputStream(stream);
            info = (WeatherModel) ois.readObject();
            tv_city.setText(info.getCityName()+"市");

            todayParse(info);
            tommrowParse(info);
            thirddayParse(info);

        } catch (IOException e) {

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private class QueryAsyncTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object... params) {

            return HttpService.getWebWeather(cityName);
        }

        @Override
        protected void onPostExecute(Object result) {
            //progressDialog.dismiss();
            if (result != null) {

                todayParse((WeatherModel) result);
                tommrowParse((WeatherModel) result);
                thirddayParse((WeatherModel) result);

                //暂时缓存起来
                try {
                    FileOutputStream stream = openFileOutput("weather.s", MODE_PRIVATE);
                    ObjectOutputStream oos = new ObjectOutputStream(stream);
                    //td 是对象;
                    oos.writeObject(result);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                //      ll_yes.setVisibility(View.VISIBLE);
                tv_city.setText(cityName+"市");


            } else {
                DataUtil.Alert(WeatherActivity.this, "查无天气信息");
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

        tv_pm25.setText("PM2.5:  "+ weather.getPm25());
        tv_attr1.setText(weather.getTmp_now()+"℃");
        tv_attr2.setText(weather.getTxt_now());
        tv_attr3.setText(weather.getDir_now()+" "+weather.getSc_now()+"级");
        weather_bg.setBackgroundResource(imageResId(weather.getTxt_now()));

    }

    /**
     * 方法名：tommrowParse
     * 功能：明天天气
     *
     * @param weather
     */
    private void tommrowParse(WeatherModel weather) {

        tv_wd1.setText( weather.getMin_1() + "℃" + "~" + weather.getMax_1() + "℃");
        if (!weather.getTxt_d_1().equals(weather.getTxt_n_1())) {
            tv_date1.setText( weather.getTxt_d_1() + "转" + weather.getTxt_n_1());
        } else {
            tv_date1.setText( weather.getTxt_d_1());
        }
        ima1.setImageResource(imageResoId(weather.getCode_d_1()));


    }

    /**
     * 方法名：thirddayParse
     * 功能：获取后天天气
     *
     * @param weather
     */
    private void thirddayParse(WeatherModel weather) {
        tv_wd2.setText( weather.getMin_2() + "℃" + "~" + weather.getMax_2() + "℃");
        if (!weather.getTxt_d_2().equals(weather.getTxt_n_2())) {
            tv_date2.setText( weather.getTxt_d_2() + "转" + weather.getTxt_n_2());
        } else {
            tv_date2.setText( weather.getTxt_d_2());
        }
        ima2.setImageResource(imageResoId(weather.getCode_d_2()));
    }


    /**
     * 方法名：imageResoId
     * 功能：获取图片
     * @return
     */
    private int imageResoId(String code) {
        int res_id=R.drawable.w100;
        if(code.equals("100")){
            res_id= R.drawable.w100;
        }else if(code.equals("101")){
            res_id= R.drawable.w101;
        }else if(code.equals("102")){
            res_id= R.drawable.w102;
        }else if(code.equals("103")){
            res_id= R.drawable.w103;
        }else if(code.equals("104")){
            res_id= R.drawable.w104;
        }else if(code.equals("200")){
            res_id= R.drawable.w200;
        }else if(code.equals("201")){
            res_id= R.drawable.w201;
        }else if(code.equals("202")){
            res_id= R.drawable.w202;
        }else if(code.equals("203")){
            res_id= R.drawable.w203;
        }else if(code.equals("204")){
            res_id= R.drawable.w204;
        }else if(code.equals("205")){
            res_id= R.drawable.w205;
        }else if(code.equals("206")){
            res_id= R.drawable.w206;
        }else if(code.equals("207")){
            res_id= R.drawable.w207;
        }else if(code.equals("208")){
            res_id= R.drawable.w208;
        }else if(code.equals("209")){
            res_id= R.drawable.w209;
        }else if(code.equals("210")){
            res_id= R.drawable.w210;
        }else if(code.equals("211")){
            res_id= R.drawable.w211;
        }else if(code.equals("212")){
            res_id= R.drawable.w212;
        }else if(code.equals("213")){
            res_id= R.drawable.w213;
        }else if(code.equals("300")){
            res_id= R.drawable.w300;
        }else if(code.equals("301")){
            res_id= R.drawable.w301;
        }else if(code.equals("302")){
            res_id= R.drawable.w302;
        }else if(code.equals("303")){
            res_id= R.drawable.w303;
        }else if(code.equals("304")){
            res_id= R.drawable.w304;
        }else if(code.equals("305")){
            res_id= R.drawable.w305;
        }else if(code.equals("306")){
            res_id= R.drawable.w306;
        }else if(code.equals("307")){
            res_id= R.drawable.w307;
        }else if(code.equals("308")){
            res_id= R.drawable.w308;
        }else if(code.equals("309")){
            res_id= R.drawable.w309;
        }else if(code.equals("310")){
            res_id= R.drawable.w310;
        }else if(code.equals("311")){
            res_id= R.drawable.w311;
        }else if(code.equals("312")){
            res_id= R.drawable.w312;
        }else if(code.equals("313")){
            res_id= R.drawable.w313;
        }else if(code.equals("400")){
            res_id= R.drawable.w400;
        }else if(code.equals("401")){
            res_id= R.drawable.w401;
        }else if(code.equals("402")){
            res_id= R.drawable.w402;
        }else if(code.equals("403")){
            res_id= R.drawable.w403;
        }else if(code.equals("404")){
            res_id= R.drawable.w404;
        }else if(code.equals("405")){
            res_id= R.drawable.w405;
        }else if(code.equals("406")){
            res_id= R.drawable.w406;
        }else if(code.equals("407")){
            res_id= R.drawable.w407;
        }else if(code.equals("500")){
            res_id= R.drawable.w500;
        }else if(code.equals("501")){
            res_id= R.drawable.w501;
        }else if(code.equals("502")){
            res_id= R.drawable.w502;
        }else if(code.equals("503")){
            res_id= R.drawable.w503;
        }else if(code.equals("504")){
            res_id= R.drawable.w504;
        }else if(code.equals("507")){
            res_id= R.drawable.w507;
        }else if(code.equals("508")){
            res_id= R.drawable.w508;
        }else if(code.equals("900")){
            res_id= R.drawable.w900;
        }else if(code.equals("901")){
            res_id= R.drawable.w901;
        }else if(code.equals("999")){
            res_id= R.drawable.w999;
        }
        return res_id;
    }
    /**
     * 方法名：imageResoId
     * 功能：获取图片
     * @return
     */
    private int imageResId(String weather) {
        int resoid = R.drawable.bg_na;
        if (weather.indexOf("云") != -1) {
            resoid = R.drawable.bg_na;
        } else if (weather.indexOf("阴") != -1) {
            resoid = R.drawable.cloud_weather;
        } else if (weather.indexOf("雨") != -1) {
            resoid = R.drawable.rain;
        } else if (weather.indexOf("晴") != -1 ) {
            resoid = R.drawable.bg_fine_day;
        } else if ( weather.indexOf("雾") != -1) {
            resoid = R.drawable.fog;
        } else if (weather.indexOf("冰雹") != -1) {
            resoid = R.drawable.haze;
        } else if (weather.indexOf("雪") != -1) {
            resoid = R.drawable.bg_snow_night;
        } else if (weather.indexOf("扬沙") != -1) {
            resoid = R.drawable.haze;
        } else if (weather.indexOf("沙尘") != -1) {
            resoid = R.drawable.haze;
        } else if (weather.indexOf("霾") != -1) {
            resoid = R.drawable.haze;
        }
        return resoid;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ws2_btn_return:
                finish();
                break;
        }
    }

    /**
     * 方法名：showOtherCity
     * 功能：输入其他城市名称
     * 参数：
     */
    private void showOtherCity() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.weather_other_city, (ViewGroup) findViewById(R.id.ws_dialog));
        dialogCity = (EditText) layout.findViewById(R.id.ws_city_name);
        new AlertDialog.Builder(this).setTitle("请输入城市名称").setView(layout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        cityName = dialogCity.getText().toString();

                        if (cityName != null && cityName.length() > 0) {
                            //progressDialog = ProgressDialog.show(WeatherActivity.this, null, "天气查询中", true, true);
                            QueryAsyncTask asyncTask = new QueryAsyncTask();
                            asyncTask.execute();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(Menu.NONE, Menu.FIRST + 3, 0, "刷新天气");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Menu.FIRST + 1:
                showOtherCity();
                break;
            case Menu.FIRST + 2:

                flag = true;
                initBaiDuMap();

                break;
            case Menu.FIRST + 3:
                if (NetworkUtils.getNetworkState(this) == NetworkUtils.NETWORN_NONE) {
                    Toast.makeText(this, R.string.net_wrok_error, Toast.LENGTH_SHORT).show();

                    flag = true;
                } else {
                    if (cityName != null && cityName.length() > 0) {
                        QueryAsyncTask asyncTask = new QueryAsyncTask();
                        asyncTask.execute();
                    } else {


                    }
                }

                break;

            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                this.finish();
                break;
            default:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        if (NetworkUtils.getNetworkState(this) == NetworkUtils.NETWORN_NONE) {
            Toast.makeText(this, R.string.net_wrok_error, Toast.LENGTH_SHORT).show();
        } else {
            initBaiDuMap();
        }
        mSwipeLayout.setRefreshing(false);
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
