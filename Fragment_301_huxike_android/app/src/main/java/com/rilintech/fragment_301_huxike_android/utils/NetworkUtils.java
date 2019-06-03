package com.rilintech.fragment_301_huxike_android.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.util.Log;

public class NetworkUtils {

    public static final int NETWORN_NONE = 0;
    public static final int NETWORN_WIFI = 1;
    public static final int NETWORN_MOBILE = 2;
    public static final int NETWORN_ON = 3;

    public static int getNetworkState(Context context) {

        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);


        // 获取代表联网状态的NetWorkInfo对象
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        // 获取当前的网络连接是否可用
        if (null == networkInfo) {
            //Toast.makeText(context, "当前的网络连接不可用", Toast.LENGTH_SHORT).show();
            //当网络不可用时，跳转到网络设置页面
            //((Activity) context).startActivityForResult(new Intent(
            //		android.provider.Settings.ACTION_WIRELESS_SETTINGS), 1);
            return NETWORN_NONE;

        } else {
            boolean available = networkInfo.isAvailable();
            if (available) {
                Log.i("通知", "当前的网络连接可用");

                // Wifi
                State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                        .getState();
                if (state == State.CONNECTED || state == State.CONNECTING) {
                    return NETWORN_WIFI;
                }

                // 3G
                /*state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                        .getState();

                if (state == State.CONNECTED || state == State.CONNECTING) {
                    return NETWORN_MOBILE;
                }*/
                return NETWORN_ON;


                // Toast.makeText(context, "当前的网络连接可用", Toast.LENGTH_SHORT).show();
            } else {
                Log.i("通知", "当前的网络连接不可用");
                // Toast.makeText(context, "当前的网络连接不可用", Toast.LENGTH_SHORT).show();
                return NETWORN_NONE;
            }
        }

    }

}