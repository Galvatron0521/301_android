package com.rilintech.fragment_301_huxike_android.http;

import android.content.Context;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.rilintech.fragment_301_huxike_android.bean.Survey;
import com.rilintech.fragment_301_huxike_android.utils.SharedPrefsUtil;

import cz.msebera.android.httpclient.Header;

/**
 * Created by rilintech on 16/4/21.
 */
public class WenJuanModel {

    private RequestCallBack requestCallBack;

    public RequestCallBack getRequestCallBack() {
        return requestCallBack;
    }

    public void setRequestCallBack(RequestCallBack requestCallBack) {
        this.requestCallBack = requestCallBack;
    }

    /**
     * 上传问卷调查信息
     * @param context
     * @param survey
     * @param requestCallBack
     */
    public void submitWenJuan(Context context, Survey survey, final RequestCallBack requestCallBack){

        Gson gson = new Gson();
        String json = gson.toJson(survey);
        RequestParams requestParams = new RequestParams();
        requestParams.put("survey", json);

        HttpClient.post(context, Url.UPLOADSURVEY+ SharedPrefsUtil.getValue(context, "data", "userId", null), requestParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                requestCallBack.onUploadError(statusCode, headers, responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                requestCallBack.onUploadSuccess(statusCode, headers, responseString);
            }
        });
    }

    public interface RequestCallBack{
        void onUploadSuccess(int statusCode, Header[] headers, String responseString);
        void onUploadError(int statusCode, Header[] headers, String responseString);
    }
}
