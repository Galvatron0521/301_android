package com.rilintech.fragment_301_huxike_android.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.rilintech.fragment_301_huxike_android.R;
import com.rilintech.fragment_301_huxike_android.db.PatientInfoManager;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

/**
 * Created by YANG on 15/11/9.
 */
public class PersonalCenterEditActivity extends BaseActivity implements View.OnClickListener {
    //取消
    private TextView tv_cancel;
    //保存
    private TextView tv_save;
    //内容
    private TextView item_content;
    //项目
    private TextView item_name;
    //
    private RelativeLayout rl_item;
    //dialog的编辑内容
    private TextView ed_content;
    private TextView mOk;
    //选中的内容
    private String mSelected = "";

    private int position;
    private ArrayList<String> list_key;
    private ArrayList<String> list_value;
    //编辑框
    private AlertDialog dialog;
    //记录当前是哪个patient属性
    private int patient_flag;
    //性别
    private static final int SEX_FLAG = 1;
    //诊断
    private static final int SEE_FLAG = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_personal_center_edit);

        getIntentData();
        initView();
        if (patient_flag == SEX_FLAG || patient_flag == SEE_FLAG) {
            showSexDialog();
        } else {
            showDialog();
        }
    }

    private void getIntentData() {
        list_key = new ArrayList<>();
        list_value = new ArrayList<>();
        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        list_key = intent.getStringArrayListExtra("list_key");
        list_value = intent.getStringArrayListExtra("list_value");

    }

    private void initView() {
        rl_item = (RelativeLayout) findViewById(R.id.rl_item);
        rl_item.setOnClickListener(this);
        item_content = (TextView) findViewById(R.id.item_content);
        item_content.setText(list_value.get(position));
        item_name = (TextView) findViewById(R.id.item_name);
        item_name.setText(list_key.get(position));
        tv_cancel = (TextView) findViewById(R.id.cancel);
        tv_save = (TextView) findViewById(R.id.save);
        tv_save.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);

        if (getResources().getString(R.string.patient_sex).equals(item_name.getText())) {
            patient_flag = SEX_FLAG;
        } else if(getResources().getString(R.string.patient_describe).equals(item_name.getText())){
            patient_flag = SEE_FLAG;
        }

    }


    private void showDialog() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.personal_center_edit_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        ed_content = (EditText) view.findViewById(R.id.tag_et);
        ed_content.setText(item_content.getText());
        mOk = (TextView) view.findViewById(R.id.ok_tv);

        builder.setCancelable(false);
        dialog = builder.show();
        dialog.getWindow().setContentView(view);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        mOk.setOnClickListener(this);

    }

    private void showSexDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setIcon(R.drawable.ic_launcher);
        //builder.setTitle("请选择性别");
        final String[] sex = {"男", "女", "中性"};
        final String[] see = {"1", "2", "3", "4"};

        //设置一个单项选择下拉框
        /**
         * 第一个参数指定我们要显示的一组下拉单选框的数据集合
         * 第二个参数代表索引，指定默认哪一个单选框被勾选上，1表示默认'女' 会被勾选上
         * 第三个参数给每一个单选项绑定一个监听器
         */
        if (patient_flag == SEX_FLAG) {
            builder.setSingleChoiceItems(sex, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mSelected = sex[which];
                }
            });
        }else if(patient_flag == SEE_FLAG){
            builder.setSingleChoiceItems(see, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mSelected = see[which];
                }
            });
        }
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                item_content.setText(mSelected);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                this.finish();
                break;
            case R.id.save:
                PatientInfoManager manager = new PatientInfoManager(this);
                manager.openDataBase();
                int result = manager.updateItem(nameToKey(item_name.getText().toString()),
                        item_content.getText().toString());
                manager.closeDataBase();
                if (result == 1) {
                    setResult(RESULT_OK);
                    this.finish();
                } else {
                    Toast.makeText(PersonalCenterEditActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.ok_tv:
                item_content.setText(ed_content.getText());
                dialog.dismiss();
                break;
            case R.id.rl_item:
                if (getResources().getString(R.string.patient_sex).equals(item_name.getText())) {
                    showSexDialog();
                } else {
                    showDialog();
                }
                break;
            default:

                break;
        }
    }

    private String nameToKey(String name) {
        String key = "";
        if (getResources().getString(R.string.patient_name).equals(name)) {
            key = "name";
        } else if (getResources().getString(R.string.patient_sex).equals(name)) {
            key = "sex";
        } else if (getResources().getString(R.string.patient_age).equals(name)) {
            key = "age";
        } else if (getResources().getString(R.string.patient_bedNO).equals(name)) {
            key = "bed_no";
        } else if (getResources().getString(R.string.patient_identity_card).equals(name)) {
            key = "identity_card";
        } else if (getResources().getString(R.string.patient_height).equals(name)) {
            key = "height";
        } else if (getResources().getString(R.string.patient_weight).equals(name)) {
            key = "weight";
        } else if (getResources().getString(R.string.patient_phone).equals(name)) {
            key = "phone";
        } else if (getResources().getString(R.string.patient_zip_code).equals(name)) {
            key = "zip_code";
        } else if (getResources().getString(R.string.patient_address).equals(name)) {
            key = "address";
        } else if (getResources().getString(R.string.patient_company).equals(name)) {
            key = "company";
        } else if (getResources().getString(R.string.patient_hospital).equals(name)) {
            key = "hospital";
        } else if (getResources().getString(R.string.patient_describe).equals(name)) {
            key = "describe";
        }

        return key;

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
