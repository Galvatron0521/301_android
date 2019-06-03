package com.rilintech.fragment_301_huxike_android.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.rilintech.fragment_301_huxike_android.R;
import com.rilintech.fragment_301_huxike_android.utils.DateUtil;
import com.rilintech.fragment_301_huxike_android.view.MyDateDiaglog;
import com.umeng.analytics.MobclickAgent;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by rilintech on 16/4/13.
 */
public class PersonEditActivity extends BaseActivity{

    private EditText editText;
    private String type;
    private int position;
    private ImageView commitImageView;
    private TextView tittleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_edit);

        initView();
        initData();
    }

    private void initView(){
        editText = (EditText)findViewById(R.id.edit);
        commitImageView = (ImageView)findViewById(R.id.iv_sure);
        tittleTextView = (TextView)findViewById(R.id.tv_title);
    }

    private void initData(){

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        position = intent.getIntExtra("position",-1);
        tittleTextView.setText(intent.getStringExtra("tittle"));
        editText.setText(intent.getStringExtra("value"));
        final String value = intent.getStringExtra("value");
        if("text".equals(type)){

        }else if("date".equals(type)){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(),0);
            editText.clearFocus();
            final TimePickerView pvTime = new TimePickerView(activity, TimePickerView.Type.YEAR_MONTH_DAY);
            //控制时间范围
            Calendar calendar = Calendar.getInstance();
            pvTime.setRange(calendar.get(Calendar.YEAR) - 50, calendar.get(Calendar.YEAR));
            pvTime.setTime(new Date());
            pvTime.setCyclic(true);
            pvTime.setCancelable(true);
            pvTime.show();
            //时间选择后回调
            pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date) {
                   editText.setText(DateUtil.getDateFormat(date));
                }
            });
            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pvTime.show();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(),0);
                }
            });



        }else if("sex".equals(type)){

            final String[] item = new String[]{"男", "女"};
            new AlertDialog.Builder(this).setTitle("单选框").setIcon(
                    android.R.drawable.ic_dialog_info)
                    .setTitle("注意")
                    .setSingleChoiceItems(
                    item, -1,
                    new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        editText.setText(item[which]);
                        dialog.dismiss();
                    }})
                    .setNegativeButton("取消", null)
                    .show();
        }else if("诊断".equals(type)){
            final String[] item = new String[]{"1", "2", "3", "4", "5", "6", "7"};
            new AlertDialog.Builder(this).setTitle("单选框").setIcon(
                    android.R.drawable.ic_dialog_info)
                    .setTitle("注意")
                    .setSingleChoiceItems(
                            item, -1,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    editText.setText(item[which]);
                                    dialog.dismiss();
                                }})
                    .setNegativeButton("取消", null)
                    .show();
        }

        commitImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = editText.getText().toString().trim();
                Intent intent = new Intent();
                intent.putExtra("result", result);
                intent.putExtra("position", position);
                /*
                 * 调用setResult方法表示我将Intent对象返回给之前的那个Activity，这样就可以在onActivityResult方法中得到Intent对象，
                 */
                setResult(101, intent);
                //结束当前这个Activity对象的生命
                finish();
            }
        });
    }

    /**
     * 返回键
     * @param view
     */
    public void onClick(View view){
        this.finish();
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
