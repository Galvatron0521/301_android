package com.rilintech.fragment_301_huxike_android.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rilintech.fragment_301_huxike_android.R;
import com.rilintech.fragment_301_huxike_android.bean.PatientInfo;
import com.rilintech.fragment_301_huxike_android.http.UserModel;
import com.rilintech.fragment_301_huxike_android.utils.ProgressUtil;
import com.rilintech.fragment_301_huxike_android.utils.SharedPrefsUtil;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


/**
 * Created by zsn on 15/10/27.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText accountEdit;// 用户名
    private EditText passwordEdit;// 密码
    private Button login;// 登陆按钮
    private TextView registerButton;
    private SharedPreferences shared;
    private SharedPreferences.Editor editor;
    private ImageView visible;
    private AlertDialog dialog;
    private PatientInfo info;
    private Activity activity;

    private TextView tiShiTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        initView();
        initListener();

        shared = getSharedPreferences("users", MODE_PRIVATE);
        editor = shared.edit();

    }

    /**
     * 各种监听
     */
    private void initListener() {
        login.setOnClickListener(this);
    }

    /**
     * 初始化
     */
    private void initView() {
        Log.i("1234567", "initView: " + Build.VERSION.SDK_INT);
        tiShiTextView = (TextView)findViewById(R.id.cuowutishi);
        accountEdit = (EditText) findViewById(R.id.account_et);
        passwordEdit = (EditText) findViewById(R.id.password_et);
        login = (Button) findViewById(R.id.btn_login);
        registerButton = (TextView) findViewById(R.id.forget_pssward);
        registerButton.setOnClickListener(this);

        accountEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tiShiTextView.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tiShiTextView.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //登陆
            case R.id.btn_login:
                login();
                break;

            case R.id.forget_pssward:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;

            default:
                break;
        }
    }

    /**
     * 登陆
     */
    private void login() {
        loginSuccess();
//        // 将信息存入到users里面
//        String user_name = accountEdit.getText().toString();
//        String user_pwd = passwordEdit.getText().toString();
//
//        if (TextUtils.isEmpty(user_name)) {
//
//            Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_LONG).show();
//            return;
//        }
//        if (TextUtils.isEmpty(user_pwd)) {
//
//            Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_LONG).show();
//            return;
//        }
//
////        ProgressUtil.getInstance().showProgress(activity, "正在登陆...");
//        UserModel userModel = new UserModel();
//        userModel.login(activity, user_name, user_pwd, new UserModel.ResultEventCallBack() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, final String responseString) {
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            JSONObject dataJson = new JSONObject(responseString);
//                            JSONObject errorJson = dataJson.getJSONObject("error");
//                            if (errorJson.getString("errorcode").equals("200")) {
//                                String userId = errorJson.getString("user_id");
//                                ProgressUtil.getInstance().showSuccessProgress("登陆成功");
//                                loginSuccess();
//                                SharedPrefsUtil.putValue(activity, "data", "userId", userId);
//                                ProgressUtil.getInstance().showDoneProgress();
//                            } else if (errorJson.getString("errorcode").equals("500")) {
//                                ProgressUtil.getInstance().showErrorProgress("用户名或密码错误");
//                                tiShiTextView.setVisibility(View.VISIBLE);
//                            }
//                        } catch (Exception e) {
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void onError(int statusCode, Header[] headers, String responseString) {
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ProgressUtil.getInstance().showErrorProgress("用户名或密码错误");
//                    }
//                });
//            }
//        });
    }

    /**
     * 登陆成功
     */
    public void loginSuccess() {
//        saveUserName();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
//        passwordEdit.setText("");
    }

    /**
     * 保存登陆的患者
     */
    private void saveUserName() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("data", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", accountEdit.getText().toString());
        editor.commit();
    }

    /**
     * 密码可见
     */
    public void visible(View view) {
        passwordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        passwordEdit.postInvalidate();
        visible.setVisibility(View.VISIBLE);

    }

    /**
     * 密码不可见
     */
    public void invisible(View view) {
        passwordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
        passwordEdit.postInvalidate();
        visible.setVisibility(View.GONE);
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
