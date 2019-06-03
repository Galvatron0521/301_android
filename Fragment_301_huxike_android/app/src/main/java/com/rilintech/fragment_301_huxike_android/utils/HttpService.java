package com.rilintech.fragment_301_huxike_android.utils;

import android.content.Context;
import android.util.Log;
import com.rilintech.fragment_301_huxike_android.bean.WeatherModel;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * 类名称：httpAsyncTask 类描述：
 */
public class HttpService {

    public static Context context;

    /**
     * 方法名：getWebWeather
     * 功能：天气查询
     *
     * @param city
     * @return
     */
    public static WeatherModel getWebWeather(String city) {

        // 获取城市代码的uri
        //String code_uri = "http://apistore.baidu.com/microservice/cityinfo?cityname=" + city;

        WeatherModel info = null;
        String httpUrl = null;
        try {
            httpUrl = "https://api.heweather.com/x3/weather?city=" + URLEncoder.encode(city, "UTF-8") +
                    "&key=999d68e889bf41a18f639c425f07b14a";

//            httpUrl = "https://api.heweather.com/x3/weather?city=" + URLEncoder.encode("北京", "UTF-8") +
//                    "&key=999d68e889bf41a18f639c425f07b14a";

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.d("weather","城市转码错误");
        }
        String result = request(httpUrl);
        Log.d("hu","result======"+result);
        Log.d("hu","city======"+city);

        try {
            if (null != result) {
                info = WeatherModel.parseJson(new JSONObject(result));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 联网请求天气数据
     *
     * @param httpUrl
     * @return
     */
    public static String request(String httpUrl) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        try {
            //URL url = new URL(new String(httpUrl.getBytes(), "ISO-8859-1"));
            URL url = new URL(new String(httpUrl.getBytes(), "UTF-8"));
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
