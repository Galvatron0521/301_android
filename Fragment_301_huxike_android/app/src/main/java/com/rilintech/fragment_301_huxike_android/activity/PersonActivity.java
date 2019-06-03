package com.rilintech.fragment_301_huxike_android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.nostra13.universalimageloader.utils.L;
import com.rilintech.fragment_301_huxike_android.R;
import com.rilintech.fragment_301_huxike_android.adapter.MyListViewAdapter;
import com.rilintech.fragment_301_huxike_android.bean.KeyValue;
import com.rilintech.fragment_301_huxike_android.bean.UserBean;
import com.rilintech.fragment_301_huxike_android.http.UserModel;
import com.rilintech.fragment_301_huxike_android.utils.ProgressUtil;
import com.rilintech.fragment_301_huxike_android.utils.SharedPrefsUtil;
import com.rilintech.fragment_301_huxike_android.utils.StringUtil;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by rilintech on 16/4/13.
 */
public class PersonActivity extends BaseActivity{

    private ListView listView;
    private MyListViewAdapter myListViewAdapter;
    private List<KeyValue> keyValueList = new ArrayList<>();
    private String[] left = new String[]{"姓名", "性别","出生日期", "年龄", "住址", "首次录入日期", "身高", "体重", "BMI", "电话1", "电话2", "家庭电话", "门诊号", "住院号", "确定诊断日期", "病程", "诊断"};
    private String result_value;
    private int position;
    private ImageView commitImageView;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        activity = this;
        initView();
        initData();
    }

    private void initView(){
        listView = (ListView)findViewById(R.id.list_view);
    }

    private void  initData(){

        final UserModel userModel = new UserModel();
        userModel.downLoadUserDetail(this, SharedPrefsUtil.getValue(this, "data", "userId", null), new UserModel.ResultDownCallBack() {

            @Override
            public void onDownSuccess(int statusCode, Header[] headers, final Object responseString) {

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        UserBean userBean = (UserBean) responseString;
                        for (int i=0; i<left.length; i++){
                            KeyValue keyValue = new KeyValue();
                            keyValue.setKey(left[i]);
                            switch (i){
                                case 0:
                                    keyValue.setValue(userBean.getName());
                                    break;
                                case 1:
                                    keyValue.setValue(userBean.getSex());
                                    break;
                                case 2:
                                    keyValue.setValue(userBean.getBirthday());
                                    break;
                                case 3:
                                    keyValue.setValue(userBean.getAge());
                                    break;
                                case 4:
                                    keyValue.setValue(userBean.getAddress());
                                    break;
                                case 5:
                                    keyValue.setValue(userBean.getFirst_insp());
                                    break;
                                case 6:
                                    keyValue.setValue(userBean.getHeight());
                                    break;
                                case 7:
                                    keyValue.setValue(userBean.getWeight());
                                    break;
                                case 8:
                                    keyValue.setValue(userBean.getBsa());
                                    break;
                                case 9:
                                    keyValue.setValue(userBean.getPhone());
                                    break;
                                case 10:
                                    keyValue.setValue(userBean.getPhone1());
                                    break;
                                case 11:
                                    keyValue.setValue(userBean.getPhone2());
                                    break;
                                case 12:
                                    keyValue.setValue(userBean.getPatient_id());
                                    break;
                                case 13:
                                    keyValue.setValue(userBean.getHosp_id());
                                    break;
                                case 14:
                                    keyValue.setValue(userBean.getDeath_time());
                                    break;
                                case 15:
                                    keyValue.setValue(userBean.getPath_type());
                                    break;
                                case 16:
                                    keyValue.setValue(userBean.getMarital_state());
                                default:
                                    break;
                            }
                            keyValueList.add(keyValue);
                        }

                        setList();
                    }
                });

            }

            @Override
            public void onDownError(int statusCode, Header[] headers, Object responseString) {

            }
        });


        commitImageView = (ImageView)findViewById(R.id.iv_sure);
        commitImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserBean userBean = new UserBean();
                userBean.setName(keyValueList.get(0).getValue());
                userBean.setSex(keyValueList.get(1).getValue());
                userBean.setBirthday(keyValueList.get(2).getValue());
                userBean.setAge(keyValueList.get(3).getValue());
                userBean.setAddress(keyValueList.get(4).getValue());
                userBean.setFirst_insp(keyValueList.get(5).getValue());
                userBean.setHeight(keyValueList.get(6).getValue());
                userBean.setWeight(keyValueList.get(7).getValue());
                userBean.setBsa(keyValueList.get(8).getValue());
                userBean.setPhone(keyValueList.get(9).getValue());
                userBean.setPhone1(keyValueList.get(10).getValue());
                userBean.setPhone2(keyValueList.get(11).getValue());
                userBean.setPatient_id(keyValueList.get(12).getValue());
                userBean.setHosp_id(keyValueList.get(13).getValue());
                userBean.setDeath_time(keyValueList.get(14).getValue());
                userBean.setPath_type(keyValueList.get(15).getValue());
                userBean.setMarital_state(keyValueList.get(16).getValue());

                ProgressUtil.getInstance().showProgress(activity, "正在提交...");
                UserModel userModel1 = new UserModel();
                userModel1.updateUser(activity, SharedPrefsUtil.getValue(activity, "data", "userId", null), userBean, new UserModel.ResultEventCallBack() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, final String responseString) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject dataJson = new JSONObject(responseString);
                                    JSONObject errorJson = dataJson.getJSONObject("error");
                                    if (errorJson.getString("errorcode").equals("200")){
                                        ProgressUtil.getInstance().showSuccessProgress("更新成功");
                                        ProgressUtil.getInstance().showDoneProgress();
                                        finish();
                                    }else if(errorJson.getString("errorcode").equals("500")){
                                       ProgressUtil.getInstance().showErrorProgress("更新失败 ");
                                    }
                                }catch (Exception e){
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(int statusCode, Header[] headers, String responseString) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ProgressUtil.getInstance().showErrorProgress("更新失败");
                            }
                        });
                    }
                });
            }
        });

    }

    public void setList(){
        myListViewAdapter = new MyListViewAdapter(keyValueList, this, new MyListViewAdapter.CallBack() {
            @Override
            public void OnResult(int position) {
                Intent intent = new Intent(PersonActivity.this, PersonEditActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("tittle", keyValueList.get(position).getKey());
                intent.putExtra("value", keyValueList.get(position).getValue());
                if(position == 0 || position == 4 || position == 6 || position == 7 || position == 9 || position == 10 || position == 11 || position == 12 || position == 13 || position == 15){
                    intent.putExtra("type", "text");
                    activity.startActivityForResult(intent, 100);
                }else if(position == 1){
                    intent.putExtra("type", "sex");
                    activity.startActivityForResult(intent, 100);
                }else if(position == 2 || position == 5 || position == 14){
                    intent.putExtra("type", "date");
                    activity.startActivityForResult(intent, 100);
                }else if(position == 3 || position == 8){
                    return;
                }else if(position == 16){
                    intent.putExtra("type", "诊断");
                    activity.startActivityForResult(intent, 100);
                }
            }
        });
        listView.setAdapter(myListViewAdapter);
    }


    /**
     * 返回键
     * @param view
     */
    public void onClick(View view){
        this.finish();
    }

    /**
     * 所有的Activity对象的返回值都是由这个方法来接收
     * requestCode: 表示的是启动一个Activity时传过去的requestCode值
     * resultCode：表示的是启动后的Activity回传值时的resultCode值
     * data：表示的是启动后的Activity回传过来的Intent对象
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == 101)
        {
            result_value = data.getStringExtra("result");
            position = data.getIntExtra("position", -1);
            keyValueList.get(position).setValue(result_value);

            if(position == 2){
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
                String timeString = format.format(date);

                String time =  timeString.substring(0, 4);
                String endTime = result_value.substring(0,4);

                int age = Integer.parseInt(time) - Integer.parseInt(endTime);
                keyValueList.get(position+1).setValue(String.valueOf(age));
            }else if(position == 6 || position == 7){
                if(keyValueList.get(6).getValue() != null && keyValueList.get(6).getValue().length()>0 && keyValueList.get(7).getValue() != null && keyValueList.get(7).getValue().length()>0){

                    String height = keyValueList.get(6).getValue();
                    String weight = keyValueList.get(7).getValue();

                    if (StringUtil.isNumber(height) && StringUtil.isNumber(weight)){
                        DecimalFormat decimalFormat = new DecimalFormat("0.00");

                        double h1 = Double.parseDouble(height)/100;
                        double w1 = Double.parseDouble(weight);
                        double b = w1/(h1*h1);

                        String bmi =  decimalFormat.format(b);
                        keyValueList.get(8).setValue(bmi);
                    }
                }
            }
            myListViewAdapter.notifyDataSetChanged();

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

}
