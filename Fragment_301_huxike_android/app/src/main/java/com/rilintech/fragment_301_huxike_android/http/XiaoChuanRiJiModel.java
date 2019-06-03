package com.rilintech.fragment_301_huxike_android.http;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rilintech.fragment_301_huxike_android.activity.XiaoChuanRiJiEditActivity;
import com.rilintech.fragment_301_huxike_android.bean.XiaoChuanRiJi;
import com.rilintech.fragment_301_huxike_android.utils.SharedPrefsUtil;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mac on 16/4/18.
 */
public class XiaoChuanRiJiModel {

    private List<XiaoChuanRiJi> listPatientBeans;
    private XiaoChuanRiJiEditActivity patientActivity;
    private ResultDownCallBack resultDownCallBack;
    private ResultUploadCallBack resultUploadCallBack;

    public XiaoChuanRiJiModel() {
    }

    public void downloadIPatientModels(Context context, final ResultDownCallBack resultDownCallBack) {
        HttpClient.post(context, Url.DownXIAOCHUANRIJI + SharedPrefsUtil.getValue(context, "data", "userId", null), null, new BaseJsonHttpResponseHandler<List<XiaoChuanRiJi>>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, List<XiaoChuanRiJi> response) {
                listPatientBeans = response;
                resultDownCallBack.onDownSuccess(statusCode, headers, listPatientBeans);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, List<XiaoChuanRiJi> errorResponse) {
                resultDownCallBack.onDownError(statusCode, headers, rawJsonData);

            }

            @Override
            protected List<XiaoChuanRiJi> parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                List<XiaoChuanRiJi> list;

                Type type = new TypeToken<List<XiaoChuanRiJi>>() {
                }.getType();
                Gson gson = new Gson();
                list = gson.fromJson(rawJsonData, type);

                return list;
            }
        });
    }


    public void upLoadData(Context context, XiaoChuanRiJi xiaoChuanRiJi, final ResultUploadCallBack resultUploadCallBack) {

        Gson gson = new Gson();
        List<XiaoChuanRiJi> list = new ArrayList<XiaoChuanRiJi>();
        list.add(xiaoChuanRiJi);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        Calendar cal = Calendar.getInstance();
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间

        cal.setTime(curDate);
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        System.out.println("要计算日期为:" + sdf.format(cal.getTime())); //输出要计算日期
        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        String imptimeBegin = sdf.format(cal.getTime());
        System.out.println("所在周星期一的日期：" + imptimeBegin);

        String stringJson = gson.toJson(list);
        RequestParams params = new RequestParams();
        params.put("mondayDate", imptimeBegin);
        params.put("diaryArray", stringJson);
        String url = "";
       // if(("1").equals(xiaoChuanRiJi.getWeek())){
            url = "/patient_infos/create_online/";
//        }else {
//            url = "/patient_infos/update_online/";
//        }
        HttpClient.post(context, url + SharedPrefsUtil.getValue(context, "data", "userId", null), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i("oo", "111");
                resultUploadCallBack.onSuccess(statusCode, headers, responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("oo", "122");
            }
        });
    }

    public void updateData(Context context, List<XiaoChuanRiJi> viewList, final XiaoChuanRiJiModel.ResultUploadCallBack resultUploadCallBack ) {

        Gson gson = new Gson();

        String stringJson = gson.toJson(viewList);

        RequestParams params = new RequestParams();
        params.put("diaryArray", stringJson);
        HttpClient.post(context, "/patient_infos/update_online/" + SharedPrefsUtil.getValue(context, "data", "userId", null), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i("oo", "111");
                resultUploadCallBack.onSuccess(statusCode, headers, responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("oo", "122");
                resultUploadCallBack.onError(statusCode, headers, responseBody);
            }
        });
    }

    public interface ResultUploadCallBack {
        void onSuccess(int statusCode, Header[] headers, Object responseString);

        void onError(int statusCode, Header[] headers, Object responseString);

    }

    public interface ResultDownCallBack {
        void onDownSuccess(int statusCode, Header[] headers, Object responseString);

        void onDownError(int statusCode, Header[] headers, Object responseString);
    }


}
