package com.rilintech.fragment_301_huxike_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.rilintech.fragment_301_huxike_android.R;
import com.rilintech.fragment_301_huxike_android.adapter.VoteSubmitAdapter;
import com.rilintech.fragment_301_huxike_android.bean.Survey;
import com.rilintech.fragment_301_huxike_android.bean.VoteSubmitItem;
import com.rilintech.fragment_301_huxike_android.http.WenJuanModel;
import com.rilintech.fragment_301_huxike_android.tool.DataLoader;
import com.rilintech.fragment_301_huxike_android.tool.ViewPagerScroller;
import com.rilintech.fragment_301_huxike_android.utils.DateUtil;
import com.rilintech.fragment_301_huxike_android.viewpager.VoteSubmitViewPager;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class QuestionnaireActivity extends BaseActivity {
	VoteSubmitViewPager viewPager;
	VoteSubmitAdapter pagerAdapter;
	List<View> viewItems = new ArrayList<View>();
	ArrayList<VoteSubmitItem> dataItems = new ArrayList<VoteSubmitItem>();
	ArrayList<Integer> scoresItems=new ArrayList<Integer>();

	private TextView tv_title;
	private int s=0;
	private int[]scoreArr;
	private Survey survey;

	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questionnaire);
		survey = new Survey();
		initBaiDuMap();
	//	initLocation();
		init();
	}
	/**
	 * 页面初始化
	 */
	private void init() {
		tv_title = (TextView) findViewById(R.id.layout_title).findViewById(R.id.tv_title);
		tv_title.setText(getResources().getString(R.string.questionnaire));

		dataItems = new DataLoader(this).getTestData();
		for (int i = 0; i < dataItems.size(); i++) {
			viewItems.add(getLayoutInflater().inflate(R.layout.vote_submit_viewpager_item, null));
		}
		scoreArr=new int[viewItems.size()];
		viewPager = (VoteSubmitViewPager) findViewById(R.id.vote_submit_viewpager);
		pagerAdapter = new VoteSubmitAdapter(this, viewItems, dataItems, scoresItems, new VoteSubmitAdapter.OnClickCallBack() {
			@Override
			public void OnClick(int position, Map<Integer, Integer> scoreMaps, Map<Integer, Boolean> map, String[] answer) {
				if(position<viewItems.size()-1) {
					setCurrentView(position+1);
				}
				if (position+1 == viewItems.size()) {
					//预留 没有点击的问题，返回该问题界面
					for(int i=0;i<5;i++){
						if(!map.containsKey(i)){
							Toast.makeText(QuestionnaireActivity.this, "请选择第"+i+1+"问题", Toast.LENGTH_SHORT).show();
							viewPager.setCurrentItem(i);
							return;
						}
					}
					if(scoreMaps.size()>=5){
						scoreArr[4]=scoreMaps.get(position);
					}
					Toast.makeText(QuestionnaireActivity.this, "感谢您完成问卷调查!", Toast.LENGTH_SHORT).show();
					for (int i=0;i<scoreArr.length;i++) {
						setCurrentScore(scoreArr[i]);
					}
					s=0;
					for(int i = 0;i<scoresItems.size();i++){
						s=s+scoresItems.get(i);
					}
					String string = "";

					for(int k=0; k<answer.length; k++){
						if (k ==0) {
							string = answer[k];
						}else if (k != answer.length-1){
							string = string + ","+ answer[k];
						}else{
							string = string + "," + answer[k];
						}
					}
					survey.setAnswer(string);
					survey.setDate(DateUtil.getDateFormat(new Date()));
					survey.setScore(String.valueOf(s));

					WenJuanModel wenJuanModel = new WenJuanModel();
					wenJuanModel.submitWenJuan(activity, survey, new WenJuanModel.RequestCallBack() {
						@Override
						public void onUploadSuccess(int statusCode, Header[] headers, String responseString) {
							activity.runOnUiThread(new Runnable() {
								@Override
								public void run() {

								}
							});
						}

						@Override
						public void onUploadError(int statusCode, Header[] headers, String responseString) {
							activity.runOnUiThread(new Runnable() {
								@Override
								public void run() {

								}
							});
						}
					});

					if(scoreMaps.size()>=5) {
						if (s == 25) {
							scoresItems.clear();
							Intent intent = new Intent(QuestionnaireActivity.this, MyAlertDialog.class);
							intent.putExtra("msg",  "评估分数：" + s + "\n" + "评估结果：控制良好 在过去4周内，您的哮喘已得到完全控制。您没有哮喘症状，您的生活也不受哮喘所限制。");
							intent.putExtra("title", "ACT问卷得分");
							startActivity(intent);

						} else if (20 <= s && s < 25) {
							scoresItems.clear();
							Intent intent = new Intent(QuestionnaireActivity.this, MyAlertDialog.class);
							intent.putExtra("msg",  "评估分数：" + s + "\n" + "评估结果：基本控制 在过去4周内，您的哮喘已得到良好控制，但还没有完全控制。您的医生也许可以帮助您得到完全控制。");
							intent.putExtra("title", "ACT问卷得分");
							startActivity(intent);
						} else if (s < 20) {
							scoresItems.clear();
							Intent intent = new Intent(QuestionnaireActivity.this, MyAlertDialog.class);
							intent.putExtra("msg",  "评估分数：" + s + "\n" + "评估结果：未得到控制 在过去4周内，您的哮喘可能没有得到控制。您的医生可以帮您制定一个哮喘管理计划帮助您改善哮喘控制。");
							intent.putExtra("title", "ACT问卷得分");
							startActivity(intent);
						}
					}
				} else {
					if(position+1==viewItems.size()-5){
						if(true){
							if(!map.containsKey(position)){
								Toast.makeText(QuestionnaireActivity.this, "请选择选项", Toast.LENGTH_SHORT).show();
								return;
							}
						}
					} else if(position+1==viewItems.size()-4){
						if(true){
							if(!map.containsKey(position)){
								Toast.makeText(QuestionnaireActivity.this, "请选择选项", Toast.LENGTH_SHORT).show();
								return;
							}
						}
						if(scoreMaps.size()>=1){
							scoreArr[0]=scoreMaps.get(position);
						}
					}else if(position+1==viewItems.size()-3){
						if(true){
							if(!map.containsKey(position)){
								Toast.makeText(QuestionnaireActivity.this, "请选择选项", Toast.LENGTH_SHORT).show();
								return;
							}
						}
						if(scoreMaps.size()>=2){
							scoreArr[1]=scoreMaps.get(position);
						}
					}else if(position+1==viewItems.size()-2){
						if(true){
							if(!map.containsKey(position)){
								Toast.makeText(QuestionnaireActivity.this, "请选择选项", Toast.LENGTH_SHORT).show();
								return;
							}
						}
						if(scoreMaps.size()>=3){
							scoreArr[2]=scoreMaps.get(position);
						}
					}else if(position+1==viewItems.size()-1){
						if(true){
							if(!map.containsKey(position)){
								Toast.makeText(QuestionnaireActivity.this, "请选择选项", Toast.LENGTH_SHORT).show();
								return;
							}
						}
						if(scoreMaps.size()>=4){
							scoreArr[3]=scoreMaps.get(position);
						}
					}
				}
			}
		});
		viewPager.setAdapter(pagerAdapter);
		viewPager.getParent().requestDisallowInterceptTouchEvent(false);
		//initViewPagerScroll();
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			/**
			 * 页面切换后调用 
			 * position  新的页面位置
			 */
			@Override
			public void onPageSelected(int position) {
				viewPager.setCurrentItem(position);

			}

			/**
			 * 页面正在滑动的时候，回调
			 */
			@Override
			public void onPageScrolled(int position, float positionOffset,
									   int positionOffsetPixels) {

			}

			/**
			 * 当页面状态发生变化的时候，回调
			 */
			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}
	/**
     * 设置ViewPager的滑动速度
     * 
     * */
    private void initViewPagerScroll( ){
    try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true); 
            ViewPagerScroller scroller = new ViewPagerScroller(viewPager.getContext());
            mScroller.set(viewPager, scroller);
        }catch(NoSuchFieldException e){
       
        }catch (IllegalArgumentException e){
       
        }catch (IllegalAccessException e){
       
        }
    }
	/**
	 * @param index
	 *            根据索引值切换页面
	 */
	public void setCurrentView(int index) {
		viewPager.setCurrentItem(index);

	}
	public void setCurrentScore(Integer score){
		scoresItems.add(score);
	}


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

	/**
	 *dingwei
 	 */
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
				    String cityName = city.substring(0, city.length() - 1);
					survey.setLocation(cityName);

				} else {

				}
			}
		}
	}

}
