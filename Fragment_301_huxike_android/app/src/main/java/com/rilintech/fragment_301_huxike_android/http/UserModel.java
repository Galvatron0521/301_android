package com.rilintech.fragment_301_huxike_android.http;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.utils.L;
import com.rilintech.fragment_301_huxike_android.bean.UserBean;
import com.rilintech.fragment_301_huxike_android.utils.DateUtil;

import java.io.File;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

/**
 * Created by rilintech on 16/4/6.
 */
public class UserModel {

    private ResultEventCallBack resultEventCallBack;
    public void setResultEventCallBack(ResultEventCallBack resultEventCallBack) {
        this.resultEventCallBack = resultEventCallBack;
    }

    private ResultDownCallBack resultDownCallBack;

    public ResultDownCallBack getResultDownCallBack() {
        return resultDownCallBack;
    }

    public void setResultDownCallBack(ResultDownCallBack resultDownCallBack) {
        this.resultDownCallBack = resultDownCallBack;
    }

    /**

     * 登陆用户
     * @param context
     * @param username 用户名
     * @param password 密码
     */
    public void login(final Context context, String username, String password, final ResultEventCallBack resultEventCallBack){
        HttpClient.post(context, Url.LOGIN + "?username=" + username + "&password=" + password, null, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                 resultEventCallBack.onError(statusCode, headers, responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                resultEventCallBack.onSuccess(statusCode, headers, responseString);
            }
        });
    }

    /**
     * 注册用户
     * @param context
     * @param userBean
     */
    public void register(final Context context, UserBean userBean, final ResultEventCallBack resultEventCallBack){

        Gson gson = new Gson();
        String json = gson.toJson(userBean);
        RequestParams requestParams = new RequestParams();
        requestParams.add("user",json);

        HttpClient.post(context, Url.REGISTER_USER, requestParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
               resultEventCallBack.onError(statusCode, headers, responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                resultEventCallBack.onSuccess(statusCode, headers, responseString);
            }
        });
    }

    /**
     * 下载个人详细的信息
     * @param context
     * @param userId
     * @param
     */
    public void downLoadUserDetail(Context context,String userId, final ResultDownCallBack resultDownCallBack){
        HttpClient.post(context, Url.DOWNPRESONDETAIL+userId, null, new BaseJsonHttpResponseHandler<UserBean>(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, UserBean response) {
                resultDownCallBack.onDownSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, UserBean errorResponse) {
                resultDownCallBack.onDownError(statusCode, headers, errorResponse);
            }

            @Override
            protected UserBean parseResponse(String rawJsonData, boolean isFailure) throws Throwable {

                java.lang.reflect.Type type = new TypeToken<UserBean>() {}.getType();
                Gson gson = new Gson();
                UserBean  userBean = gson.fromJson(rawJsonData, type);

                return userBean;
            }
        });
    }

    /**
     * 更新个人信息
     * @param context
     * @param userId
     * @param resultEventCallBack
     */
    public void updateUser(Context context, String userId, UserBean userBean, final ResultEventCallBack resultEventCallBack){
        Gson gson = new Gson();
        String json = gson.toJson(userBean);
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_info", json);

        HttpClient.post(context, Url.UPDATE_PATIENTINFO + userId, requestParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                resultEventCallBack.onError(statusCode, headers, responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                resultEventCallBack.onSuccess(statusCode, headers, responseString);
            }
        });
    }

    public void UploadHeadImage(Context context, String userId, String url, final ResultEventCallBack resultEventCallBack){
        RequestParams requestParams = new RequestParams();
        try {
            File file = new File(url);
            requestParams.put("filedata", file);
        }catch (Exception e){
            L.d("==================="+"获取图片时出错", "ddd");
        }

        HttpClient.post(context, Url.UPLOADHEADIMAGEURL + userId, requestParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                resultEventCallBack.onError(statusCode, headers, responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                resultEventCallBack.onSuccess(statusCode, headers, responseString);
            }
        });

    }

    public void getImageUrl(Context context, String url, final ResultEventCallBack resultEventCallBack){
        HttpClient.post(context, url, null, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                resultEventCallBack.onError(statusCode,headers,responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                resultEventCallBack.onSuccess(statusCode, headers, responseString);
            }
        });
    }

    public void uploadPm(Context context,  String location, String pm25){

        HttpClient.post(context, Url.UPLOADPM25 + "?date="+DateUtil.getDateFormat(new Date())+"&location="+location+"&pm25="+pm25, null
                , new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

            }
        });
    }



    public ResultEventCallBack getResultEventCallBack() {
        return resultEventCallBack;
    }

    public interface ResultEventCallBack{
        void onSuccess(int statusCode, Header[] headers, String responseString);
        void onError(int statusCode, Header[] headers, String responseString);

    }

    public interface ResultDownCallBack{
        void onDownSuccess(int statusCode, Header[] headers, Object responseString);
        void onDownError(int statusCode, Header[] headers, Object responseString);
    }
}
