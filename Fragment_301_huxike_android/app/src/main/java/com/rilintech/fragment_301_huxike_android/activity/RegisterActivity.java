package com.rilintech.fragment_301_huxike_android.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.rilintech.fragment_301_huxike_android.R;
import com.rilintech.fragment_301_huxike_android.bean.UserBean;
import com.rilintech.fragment_301_huxike_android.http.UserModel;
import com.rilintech.fragment_301_huxike_android.utils.ProgressUtil;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by rilintech on 16/4/9.
 */
public class RegisterActivity extends Activity implements View.OnClickListener{

    private EditText usernameEditText, passwordEditText;
    private Button registerButton;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        activity = this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        usernameEditText = (EditText)findViewById(R.id.account_et);
        passwordEditText = (EditText)findViewById(R.id.password_et);
        registerButton = (Button) findViewById(R.id.btn_login);
        registerButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                register();
            break;
            default:
                break;
        }
    }

    /**
     * 注册
     */
    private void register(){
        UserModel userModel = new UserModel();
        UserBean userBean = new UserBean();
        userBean.setUsername(usernameEditText.getText().toString());
        userBean.setPassword(passwordEditText.getText().toString());
        ProgressUtil.getInstance().showProgress(activity, "正在注册...");
        userModel.register(activity, userBean, new UserModel.ResultEventCallBack() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, final String responseString) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject dataJson = new JSONObject(responseString);
                            JSONObject errorJson = dataJson.getJSONObject("error");
                            if (errorJson.getString("errorcode").equals("200")){
                                ProgressUtil.getInstance().showSuccessProgress("注册成功");
                                finish();
                            }else if(errorJson.getString("errorcode").equals("500")){
                                ProgressUtil.getInstance().showErrorProgress("注册失败");
                            }else if(errorJson.getInt("errorcode") == 300){
                                ProgressUtil.getInstance().showErrorProgress("该用户名已被占用");
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
                        ProgressUtil.getInstance().showErrorProgress("注册失败");
                    }
                });
            }
        });
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
