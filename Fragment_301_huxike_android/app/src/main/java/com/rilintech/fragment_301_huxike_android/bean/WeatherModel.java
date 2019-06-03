package com.rilintech.fragment_301_huxike_android.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by YANG on 15/10/14.
 */
public class WeatherModel implements Serializable {

    private String cityName;
    private String status;
    private String pm25;
    private String aqi;
    private String qlty;
    private String id;
    private String date_0;
    private String sr_0;
    private String ss_0;
    private String tmp_0;
    private String max_0;
    private String min_0;
    private String wind_0;
    private String spd_0;
    private String sc_0;
    private String deg_0;
    private String dir_0;
    private String cond_0;
    private String code_d_0;
    private String txt_d_0;
    private String code_n_0;
    private String txt_n_0;
    private String pcpn_0;
    private String pop_0;
    private String hum_0;
    private String pres_0;
    private String vis_0;

    private String date_1;
    private String sr_1;
    private String ss_1;
    private String tmp_1;
    private String max_1;
    private String min_1;
    private String wind_1;
    private String spd_1;
    private String sc_1;
    private String deg_1;
    private String dir_1;
    private String cond_1;
    private String code_d_1;
    private String txt_d_1;
    private String code_n_1;
    private String txt_n_1;
    private String pcpn_1;
    private String pop_1;
    private String hum_1;
    private String pres_1;
    private String vis_1;

    private String date_2;
    private String sr_2;
    private String ss_2;
    private String tmp_2;
    private String max_2;
    private String min_2;
    private String wind_2;
    private String spd_2;
    private String sc_2;
    private String deg_2;
    private String dir_2;
    private String cond_2;
    private String code_d_2;
    private String txt_d_2;
    private String code_n_2;
    private String txt_n_2;
    private String pcpn_2;
    private String pop_2;
    private String hum_2;
    private String pres_2;
    private String vis_2;

    private String fl_now;
    private String hum_now;
    private String pcpn_now;
    private String pres_now;
    private String tmp_now;
    private String vis_now;
    private String code_now;
    private String txt_now;
    private String deg_now;
    private String dir_now;
    private String sc_now;
    private String spd_now;


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getFl_now() {
        return fl_now;
    }

    public void setFl_now(String fl_now) {
        this.fl_now = fl_now;
    }

    public String getHum_now() {
        return hum_now;
    }

    public void setHum_now(String hum_now) {
        this.hum_now = hum_now;
    }

    public String getPcpn_now() {
        return pcpn_now;
    }

    public void setPcpn_now(String pcpn_now) {
        this.pcpn_now = pcpn_now;
    }

    public String getPres_now() {
        return pres_now;
    }

    public void setPres_now(String pres_now) {
        this.pres_now = pres_now;
    }

    public String getTmp_now() {
        return tmp_now;
    }

    public void setTmp_now(String tmp_now) {
        this.tmp_now = tmp_now;
    }

    public String getVis_now() {
        return vis_now;
    }

    public void setVis_now(String vis_now) {
        this.vis_now = vis_now;
    }

    public String getCode_now() {
        return code_now;
    }

    public void setCode_now(String code_now) {
        this.code_now = code_now;
    }

    public String getTxt_now() {
        return txt_now;
    }

    public void setTxt_now(String txt_now) {
        this.txt_now = txt_now;
    }

    public String getDeg_now() {
        return deg_now;
    }

    public void setDeg_now(String deg_now) {
        this.deg_now = deg_now;
    }

    public String getDir_now() {
        return dir_now;
    }

    public void setDir_now(String dir_now) {
        this.dir_now = dir_now;
    }

    public String getSc_now() {
        return sc_now;
    }

    public void setSc_now(String sc_now) {
        this.sc_now = sc_now;
    }

    public String getSpd_now() {
        return spd_now;
    }

    public void setSpd_now(String spd_now) {
        this.spd_now = spd_now;
    }

    public String getDate_1() {
        return date_1;
    }

    public void setDate_1(String date_1) {
        this.date_1 = date_1;
    }

    public String getSr_1() {
        return sr_1;
    }

    public void setSr_1(String sr_1) {
        this.sr_1 = sr_1;
    }

    public String getSs_1() {
        return ss_1;
    }

    public void setSs_1(String ss_1) {
        this.ss_1 = ss_1;
    }

    public String getTmp_1() {
        return tmp_1;
    }

    public void setTmp_1(String tmp_1) {
        this.tmp_1 = tmp_1;
    }

    public String getMax_1() {
        return max_1;
    }

    public void setMax_1(String max_1) {
        this.max_1 = max_1;
    }

    public String getMin_1() {
        return min_1;
    }

    public void setMin_1(String min_1) {
        this.min_1 = min_1;
    }

    public String getWind_1() {
        return wind_1;
    }

    public void setWind_1(String wind_1) {
        this.wind_1 = wind_1;
    }

    public String getSpd_1() {
        return spd_1;
    }

    public void setSpd_1(String spd_1) {
        this.spd_1 = spd_1;
    }

    public String getSc_1() {
        return sc_1;
    }

    public void setSc_1(String sc_1) {
        this.sc_1 = sc_1;
    }

    public String getDeg_1() {
        return deg_1;
    }

    public void setDeg_1(String deg_1) {
        this.deg_1 = deg_1;
    }

    public String getDir_1() {
        return dir_1;
    }

    public void setDir_1(String dir_1) {
        this.dir_1 = dir_1;
    }

    public String getCond_1() {
        return cond_1;
    }

    public void setCond_1(String cond_1) {
        this.cond_1 = cond_1;
    }

    public String getCode_d_1() {
        return code_d_1;
    }

    public void setCode_d_1(String code_d_1) {
        this.code_d_1 = code_d_1;
    }

    public String getTxt_d_1() {
        return txt_d_1;
    }

    public void setTxt_d_1(String txt_d_1) {
        this.txt_d_1 = txt_d_1;
    }

    public String getCode_n_1() {
        return code_n_1;
    }

    public void setCode_n_1(String code_n_1) {
        this.code_n_1 = code_n_1;
    }

    public String getTxt_n_1() {
        return txt_n_1;
    }

    public void setTxt_n_1(String txt_n_1) {
        this.txt_n_1 = txt_n_1;
    }

    public String getPcpn_1() {
        return pcpn_1;
    }

    public void setPcpn_1(String pcpn_1) {
        this.pcpn_1 = pcpn_1;
    }

    public String getPop_1() {
        return pop_1;
    }

    public void setPop_1(String pop_1) {
        this.pop_1 = pop_1;
    }

    public String getHum_1() {
        return hum_1;
    }

    public void setHum_1(String hum_1) {
        this.hum_1 = hum_1;
    }

    public String getPres_1() {
        return pres_1;
    }

    public void setPres_1(String pres_1) {
        this.pres_1 = pres_1;
    }

    public String getVis_1() {
        return vis_1;
    }

    public void setVis_1(String vis_1) {
        this.vis_1 = vis_1;
    }

    public String getDate_2() {
        return date_2;
    }

    public void setDate_2(String date_2) {
        this.date_2 = date_2;
    }

    public String getSr_2() {
        return sr_2;
    }

    public void setSr_2(String sr_2) {
        this.sr_2 = sr_2;
    }

    public String getSs_2() {
        return ss_2;
    }

    public void setSs_2(String ss_2) {
        this.ss_2 = ss_2;
    }

    public String getTmp_2() {
        return tmp_2;
    }

    public void setTmp_2(String tmp_2) {
        this.tmp_2 = tmp_2;
    }

    public String getMax_2() {
        return max_2;
    }

    public void setMax_2(String max_2) {
        this.max_2 = max_2;
    }

    public String getMin_2() {
        return min_2;
    }

    public void setMin_2(String min_2) {
        this.min_2 = min_2;
    }

    public String getWind_2() {
        return wind_2;
    }

    public void setWind_2(String wind_2) {
        this.wind_2 = wind_2;
    }

    public String getSpd_2() {
        return spd_2;
    }

    public void setSpd_2(String spd_2) {
        this.spd_2 = spd_2;
    }

    public String getSc_2() {
        return sc_2;
    }

    public void setSc_2(String sc_2) {
        this.sc_2 = sc_2;
    }

    public String getDeg_2() {
        return deg_2;
    }

    public void setDeg_2(String deg_2) {
        this.deg_2 = deg_2;
    }

    public String getDir_2() {
        return dir_2;
    }

    public void setDir_2(String dir_2) {
        this.dir_2 = dir_2;
    }

    public String getCond_2() {
        return cond_2;
    }

    public void setCond_2(String cond_2) {
        this.cond_2 = cond_2;
    }

    public String getCode_d_2() {
        return code_d_2;
    }

    public void setCode_d_2(String code_d_2) {
        this.code_d_2 = code_d_2;
    }

    public String getTxt_d_2() {
        return txt_d_2;
    }

    public void setTxt_d_2(String txt_d_2) {
        this.txt_d_2 = txt_d_2;
    }

    public String getCode_n_2() {
        return code_n_2;
    }

    public void setCode_n_2(String code_n_2) {
        this.code_n_2 = code_n_2;
    }

    public String getTxt_n_2() {
        return txt_n_2;
    }

    public void setTxt_n_2(String txt_n_2) {
        this.txt_n_2 = txt_n_2;
    }

    public String getPcpn_2() {
        return pcpn_2;
    }

    public void setPcpn_2(String pcpn_2) {
        this.pcpn_2 = pcpn_2;
    }

    public String getPop_2() {
        return pop_2;
    }

    public void setPop_2(String pop_2) {
        this.pop_2 = pop_2;
    }

    public String getHum_2() {
        return hum_2;
    }

    public void setHum_2(String hum_2) {
        this.hum_2 = hum_2;
    }

    public String getPres_2() {
        return pres_2;
    }

    public void setPres_2(String pres_2) {
        this.pres_2 = pres_2;
    }

    public String getVis_2() {
        return vis_2;
    }

    public void setVis_2(String vis_2) {
        this.vis_2 = vis_2;
    }

    public String getDate_0() {
        return date_0;
    }

    public void setDate_0(String date_0) {
        this.date_0 = date_0;
    }

    public String getSr_0() {
        return sr_0;
    }

    public void setSr_0(String sr_0) {
        this.sr_0 = sr_0;
    }

    public String getSs_0() {
        return ss_0;
    }

    public void setSs_0(String ss_0) {
        this.ss_0 = ss_0;
    }

    public String getTmp_0() {
        return tmp_0;
    }

    public void setTmp_0(String tmp_0) {
        this.tmp_0 = tmp_0;
    }

    public String getMax_0() {
        return max_0;
    }

    public void setMax_0(String max_0) {
        this.max_0 = max_0;
    }

    public String getMin_0() {
        return min_0;
    }

    public void setMin_0(String min_0) {
        this.min_0 = min_0;
    }

    public String getWind_0() {
        return wind_0;
    }

    public void setWind_0(String wind_0) {
        this.wind_0 = wind_0;
    }

    public String getSpd_0() {
        return spd_0;
    }

    public void setSpd_0(String spd_0) {
        this.spd_0 = spd_0;
    }

    public String getSc_0() {
        return sc_0;
    }

    public void setSc_0(String sc_0) {
        this.sc_0 = sc_0;
    }

    public String getDeg_0() {
        return deg_0;
    }

    public void setDeg_0(String deg_0) {
        this.deg_0 = deg_0;
    }

    public String getDir_0() {
        return dir_0;
    }

    public void setDir_0(String dir_0) {
        this.dir_0 = dir_0;
    }

    public String getCond_0() {
        return cond_0;
    }

    public void setCond_0(String cond_0) {
        this.cond_0 = cond_0;
    }

    public String getCode_d_0() {
        return code_d_0;
    }

    public void setCode_d_0(String code_d_0) {
        this.code_d_0 = code_d_0;
    }

    public String getTxt_d_0() {
        return txt_d_0;
    }

    public void setTxt_d_0(String txt_d_0) {
        this.txt_d_0 = txt_d_0;
    }

    public String getCode_n_0() {
        return code_n_0;
    }

    public void setCode_n_0(String code_n_0) {
        this.code_n_0 = code_n_0;
    }

    public String getTxt_n_0() {
        return txt_n_0;
    }

    public void setTxt_n_0(String txt_n_0) {
        this.txt_n_0 = txt_n_0;
    }

    public String getPcpn_0() {
        return pcpn_0;
    }

    public void setPcpn_0(String pcpn_0) {
        this.pcpn_0 = pcpn_0;
    }

    public String getPop_0() {
        return pop_0;
    }

    public void setPop_0(String pop_0) {
        this.pop_0 = pop_0;
    }

    public String getHum_0() {
        return hum_0;
    }

    public void setHum_0(String hum_0) {
        this.hum_0 = hum_0;
    }

    public String getPres_0() {
        return pres_0;
    }

    public void setPres_0(String pres_0) {
        this.pres_0 = pres_0;
    }

    public String getVis_0() {
        return vis_0;
    }

    public void setVis_0(String vis_0) {
        this.vis_0 = vis_0;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getQlty() {
        return qlty;
    }

    public void setQlty(String qlty) {
        this.qlty = qlty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 解析发出请求返回的数据
     * @param object
     * @return
     * @throws JSONException
     */
    public static WeatherModel parseJson(JSONObject object)throws JSONException {

        WeatherModel info = new WeatherModel();

        JSONArray jsonArray = object.getJSONArray("HeWeather data service 3.0");
        JSONObject jsonObject_all = jsonArray.getJSONObject(0);

        JSONObject jsonObjectAqi = jsonObject_all.getJSONObject("aqi");
        JSONObject jsonObjectBasic = jsonObject_all.getJSONObject("basic");
        JSONArray jsonArrayDaily_forecast = jsonObject_all.getJSONArray("daily_forecast");
        JSONArray jsonArrayHourly_forecast = jsonObject_all.getJSONArray("hourly_forecast");
        JSONObject jsonObjectNow = jsonObject_all.getJSONObject("now");
        String s = jsonObject_all.getString("status");
        JSONObject jsonObjectSuggestion = jsonObject_all.getJSONObject("suggestion");

        //AQI空气质量
        JSONObject jsonObjectCity = jsonObjectAqi.getJSONObject("city");
        info.setAqi(jsonObjectCity.getString("aqi"));
        info.setPm25(jsonObjectCity.getString("pm25"));
        info.setQlty(jsonObjectCity.getString("qlty"));
        //basic
        String s1 = jsonObjectBasic.getString("id");
        String city_name = jsonObjectBasic.getString("city");
        info.setId(s1);
        info.setCityName(city_name);
        //daily_forecast每日天气预报
        JSONObject jsonObject0 = jsonArrayDaily_forecast.getJSONObject(0);
        JSONObject jsonObject1 = jsonArrayDaily_forecast.getJSONObject(1);
        JSONObject jsonObject2 = jsonArrayDaily_forecast.getJSONObject(2);
        JSONObject jsonObject3 = jsonArrayDaily_forecast.getJSONObject(3);
        JSONObject jsonObject4 = jsonArrayDaily_forecast.getJSONObject(4);
        JSONObject jsonObject5 = jsonArrayDaily_forecast.getJSONObject(5);
        JSONObject jsonObject6 = jsonArrayDaily_forecast.getJSONObject(6);
        //===今天=====
        info.setDate_0(jsonObject0.getString("date"));
        info.setHum_0(jsonObject0.getString("hum"));
        info.setPcpn_0(jsonObject0.getString("pcpn"));
        info.setPop_0(jsonObject0.getString("pop"));
        info.setPres_0(jsonObject0.getString("pres"));
        info.setVis_0(jsonObject0.getString("vis"));

        JSONObject jsonObjectAstro_0 = jsonObject0.getJSONObject("astro");
        info.setSr_0(jsonObjectAstro_0.getString("sr"));
        info.setSs_0(jsonObjectAstro_0.getString("ss"));
        JSONObject jsonObjectCond_0 = jsonObject0.getJSONObject("cond");
        info.setCode_d_0(jsonObjectCond_0.getString("code_d"));
        info.setCode_n_0(jsonObjectCond_0.getString("code_n"));
        info.setTxt_d_0(jsonObjectCond_0.getString("txt_d"));
        info.setTxt_n_0(jsonObjectCond_0.getString("txt_n"));
        JSONObject jsonObjectTmp_0 = jsonObject0.getJSONObject("tmp");
        info.setMax_0(jsonObjectTmp_0.getString("max"));
        info.setMin_0(jsonObjectTmp_0.getString("min"));
        JSONObject jsonObjectWind_0 = jsonObject0.getJSONObject("wind");
        info.setDeg_0(jsonObjectWind_0.getString("deg"));
        info.setDir_0(jsonObjectWind_0.getString("dir"));
        info.setSc_0(jsonObjectWind_0.getString("sc"));
        info.setSpd_0(jsonObjectWind_0.getString("spd"));

        //=====第一天====
        info.setDate_1(jsonObject1.getString("date"));
        info.setHum_1(jsonObject1.getString("hum"));
        info.setPcpn_1(jsonObject1.getString("pcpn"));
        info.setPop_1(jsonObject1.getString("pop"));
        info.setPres_1(jsonObject1.getString("pres"));
        info.setVis_1(jsonObject1.getString("vis"));

        JSONObject jsonObjectAstro_1 = jsonObject1.getJSONObject("astro");
        info.setSr_1(jsonObjectAstro_1.getString("sr"));
        info.setSs_1(jsonObjectAstro_1.getString("ss"));
        JSONObject jsonObjectCond_1 = jsonObject1.getJSONObject("cond");
        info.setCode_d_1(jsonObjectCond_1.getString("code_d"));
        info.setCode_n_1(jsonObjectCond_1.getString("code_n"));
        info.setTxt_d_1(jsonObjectCond_1.getString("txt_d"));
        info.setTxt_n_1(jsonObjectCond_1.getString("txt_n"));
        JSONObject jsonObjectTmp_1 = jsonObject1.getJSONObject("tmp");
        info.setMax_1(jsonObjectTmp_1.getString("max"));
        info.setMin_1(jsonObjectTmp_1.getString("min"));
        JSONObject jsonObjectWind_1 = jsonObject1.getJSONObject("wind");
        info.setDeg_1(jsonObjectWind_1.getString("deg"));
        info.setDir_1(jsonObjectWind_1.getString("dir"));
        info.setSc_1(jsonObjectWind_1.getString("sc"));
        info.setSpd_1(jsonObjectWind_1.getString("spd"));

        //=====第二天====
        info.setDate_2(jsonObject2.getString("date"));
        info.setHum_2(jsonObject2.getString("hum"));
        info.setPcpn_2(jsonObject2.getString("pcpn"));
        info.setPop_2(jsonObject2.getString("pop"));
        info.setPres_2(jsonObject2.getString("pres"));
        info.setVis_2(jsonObject2.getString("vis"));

        JSONObject jsonObjectAstro_2 = jsonObject2.getJSONObject("astro");
        info.setSr_2(jsonObjectAstro_2.getString("sr"));
        info.setSs_2(jsonObjectAstro_2.getString("ss"));
        JSONObject jsonObjectCond_2 = jsonObject2.getJSONObject("cond");
        info.setCode_d_2(jsonObjectCond_2.getString("code_d"));
        info.setCode_n_2(jsonObjectCond_2.getString("code_n"));
        info.setTxt_d_2(jsonObjectCond_2.getString("txt_d"));
        info.setTxt_n_2(jsonObjectCond_2.getString("txt_n"));
        JSONObject jsonObjectTmp_2 = jsonObject2.getJSONObject("tmp");
        info.setMax_2(jsonObjectTmp_2.getString("max"));
        info.setMin_2(jsonObjectTmp_2.getString("min"));
        JSONObject jsonObjectWind_2 = jsonObject2.getJSONObject("wind");
        info.setDeg_2(jsonObjectWind_2.getString("deg"));
        info.setDir_2(jsonObjectWind_2.getString("dir"));
        info.setSc_2(jsonObjectWind_2.getString("sc"));
        info.setSpd_2(jsonObjectWind_2.getString("spd"));

        //Now 实时天气
        //info.setFl_now(jsonObjectNow.getString("fl"));
        info.setHum_now(jsonObjectNow.getString("hum"));
        info.setPcpn_now(jsonObjectNow.getString("pcpn"));
        //info.setPres_now(jsonObjectNow.getString("pres"));
        info.setTmp_now(jsonObjectNow.getString("tmp"));
        //info.setVis_now(jsonObjectNow.getString("vis"));

        JSONObject jsonObjectCond_now = jsonObjectNow.getJSONObject("cond");
        info.setCode_now(jsonObjectCond_now.getString("code"));
        info.setTxt_now(jsonObjectCond_now.getString("txt"));
        JSONObject jsonObjectWind_now = jsonObjectNow.getJSONObject("wind");
        //info.setDeg_now(jsonObjectWind_now.getString("deg"));
        info.setDir_now(jsonObjectWind_now.getString("dir"));
        info.setSc_now(jsonObjectWind_now.getString("sc"));
        //info.setSpd_now(jsonObjectWind_now.getString("spd"));

        //status 状态码
        info.setStatus(s);

        //suggestion 建议


        return info;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public static String getData(String url, String param) {

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        HttpURLConnection conn = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);

            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            conn.disconnect();
        }
        return result;
    }


}




