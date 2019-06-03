package com.rilintech.fragment_301_huxike_android.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.HttpEntity;

/**
 * Created by YangLei on 2016/1/18.
 */
public class HttpClient {

    private static AsyncHttpClient client = new AsyncHttpClient();

    private static String getAbsoluteUrl(String relativeUrl) {
        return Url.IP + relativeUrl;
    }


    public static void get(Context context, String url, RequestParams params,
                           AsyncHttpResponseHandler responseHandler) {
        client.get(context, getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(Context context, String url, RequestParams params,
                            AsyncHttpResponseHandler responseHandler) {

        client.post(context, getAbsoluteUrl(url), params, responseHandler);
    }


    /**
     * 可以直接将model通过GSon转化成Json字符串后，
     * 再转化 ByteArrayEntity entity = new ByteArrayEntity(Json.getBytes("UTF-8"));后直接提交
     * 不需要像 post(String url, RequestParams params,AsyncHttpResponseHandler responseHandler)中的
     * RequestParams参数还得拼接params.add(String key,String value);
     *
     * @param context
     * @param url
     * @param entity          ByteArrayEntity
     * @param contentType     "application/json"
     * @param responseHandler
     */
    public static void postModelAsJson(Context context, String url, HttpEntity entity, String contentType,
                                       AsyncHttpResponseHandler responseHandler) {
        client.post(context, getAbsoluteUrl(url), entity, contentType, responseHandler);
    }
}
