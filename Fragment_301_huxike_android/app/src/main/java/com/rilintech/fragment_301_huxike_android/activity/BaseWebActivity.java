package com.rilintech.fragment_301_huxike_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.rilintech.fragment_301_huxike_android.R;
import com.rilintech.fragment_301_huxike_android.view.ProgressWebView;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * @author rilintech
 * @Description:WebView界面，带自定义进度条显示
 */
public class BaseWebActivity extends BaseActivity {

    protected ProgressWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baseweb);

        webView = (ProgressWebView) findViewById(R.id.baseweb_webview);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String url = bundle.getString("url");

        webView.loadUrl(url);
        //设置支持js代码
        webView.getSettings().setJavaScriptEnabled(true);
        //设置支持插件
        //webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        //设置允许访问文件数据
        webView.getSettings().setAllowFileAccess(true);
        //设置是否支持插件
        //webView.getSettings().setPluginsEnabled(true);
        //支持缩放
        webView.getSettings().setSupportZoom(true);
        //支持缓存
        webView.getSettings().setAppCacheEnabled(true);
        //设置缓存模式
        //webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //设置此属性，可任意比例缩放
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
    }

    /**
     * 返回键
     *
     * @param view
     */
    public void onClick(View view) {

        this.finish();
    }


    private void callHiddenWebViewMethod(String name) {
        if (webView != null) {
            try {
                Method method = WebView.class.getMethod(name);
                method.invoke(webView);
            } catch (NoSuchMethodException e) {

            } catch (IllegalAccessException e) {

            } catch (InvocationTargetException e) {

            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.pauseTimers();

        if (isFinishing()) {
            webView.loadUrl("about:blank");
            setContentView(new FrameLayout(this));
        }
        callHiddenWebViewMethod("onPause");
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        callHiddenWebViewMethod("onResume");
        MobclickAgent.onResume(this);
    }


}


