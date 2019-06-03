package com.rilintech.fragment_301_huxike_android.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.utils.L;
import com.rilintech.fragment_301_huxike_android.R;
import com.rilintech.fragment_301_huxike_android.bean.XiaoChuanRiJi;
import com.rilintech.fragment_301_huxike_android.http.XiaoChuanRiJiModel;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mac on 16/4/14.
 */
public class XiaoChuanRiJiActivity extends BaseActivity {

    private List<XiaoChuanRiJi> viewList;
    public PopupWindow popupWindow;
    public View contentView;
    List<String> listYaoWuName;
    int index1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listYaoWuName = new ArrayList<String>();
        listYaoWuName.add("硫酸沙丁胺醇气雾剂");
        listYaoWuName.add("吸入用异丙托溴铵溶液");
        listYaoWuName.add("塞托溴铵粉雾剂（含吸入器）");
        listYaoWuName.add("氨茶碱注射液");
        listYaoWuName.add("茶碱缓释胶囊Ⅱ");
        listYaoWuName.add("吸入用布地奈德混悬液");
        listYaoWuName.add("布地奈德福莫特罗粉吸入剂");
        listYaoWuName.add("沙美特罗替卡松粉吸入剂");
        listYaoWuName.add("孟鲁司特钠片");
        listYaoWuName.add("注射用甲泼尼龙琥珀酸钠");
        setContentView(R.layout.xiaochuanriji_add_activity);
        final XiaoChuanRiJi xiaoChuanRiJi = new XiaoChuanRiJi();

        Intent intent = getIntent();
        xiaoChuanRiJi.setWeek(String.valueOf(intent.getIntExtra("week", 0)));
        xiaoChuanRiJi.setSymptomArray(new ArrayList<XiaoChuanRiJi.SymptomArrayEntity>());
        XiaoChuanRiJi.SymptomArrayEntity symptomArrayEntity = new XiaoChuanRiJi.SymptomArrayEntity();
        symptomArrayEntity.setSectionName(getString(R.string.sectionnametop));
        symptomArrayEntity.setItems(new ArrayList<XiaoChuanRiJi.SymptomArrayEntity.ItemsEntity>());

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.xiaochuanriji_add).findViewById(R.id.xiaochuanriji_top_id);
        int a = linearLayout.getChildCount();


        String[] itemNames = {getString(R.string.kesou), getString(R.string.chuanxi),
                getString(R.string.qiji), getString(R.string.xiaongmen),
                getString(R.string.yejianxiaochuan), getString(R.string.xiaochuanshirihuodong)
        };

        for (int i = 2; i < linearLayout.getChildCount(); i++) {

            if (linearLayout.getChildAt(i) instanceof LinearLayout) {

                final XiaoChuanRiJi.SymptomArrayEntity.ItemsEntity itemsEntity = new XiaoChuanRiJi.SymptomArrayEntity.ItemsEntity();
                itemsEntity.setSelectString("");
                itemsEntity.setItemName(itemNames[i - 2]);
                itemsEntity.setType("checkbox");
                for (int k = 1; k < ((LinearLayout) linearLayout.getChildAt(i)).getChildCount(); k++) {

                    if (((LinearLayout) linearLayout.getChildAt(i)).getChildAt(k) instanceof ImageView) {
                        ((ImageView) ((LinearLayout) linearLayout.getChildAt(i)).getChildAt(k)).setTag((k - 1) * 2);

                        ((ImageView) ((LinearLayout) linearLayout.getChildAt(i)).getChildAt(k)).setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                v.setSelected(!v.isSelected());
                                StringBuffer buffer = new StringBuffer(itemsEntity.getSelectString());
                                if (v.isSelected()) {
                                    System.out.println(buffer.toString());//输出123456
                                    int a = (int) v.getTag();
                                    buffer.replace(a, a + 1, "1");

                                    ((ImageView) v).setImageResource(R.drawable.xiaochuanriji_duihao);
                                    System.out.println(buffer.toString());//输出a23456
                                    itemsEntity.setSelectString(buffer.toString());
                                } else {
                                    int a = (int) v.getTag();
                                    buffer.replace(a, a + 1, "0");
                                    ((ImageView) v).setImageResource(0);
                                    System.out.println(buffer.toString());//输出a23456
                                    itemsEntity.setSelectString(buffer.toString());
                                }
                            }
                        });
                    }
                    if ((k - 1) == 0) {
                        itemsEntity.setSelectString(itemsEntity.getSelectString() + "0");
                    } else {
                        itemsEntity.setSelectString(itemsEntity.getSelectString() + ",0");
                    }


                }
                symptomArrayEntity.getItems().add(itemsEntity);


            }
        }

        xiaoChuanRiJi.getSymptomArray().add(symptomArrayEntity);

        LinearLayout linearLayoutMiddle = (LinearLayout) findViewById(R.id.xiaochuanriji_add).findViewById(R.id.xiaochuanriji_middle);

        xiaoChuanRiJi.setAddMedicineArray(new ArrayList<XiaoChuanRiJi.AddMedicineArrayEntity>());
        XiaoChuanRiJi.AddMedicineArrayEntity addMedicineArrayEntity = new XiaoChuanRiJi.AddMedicineArrayEntity();
        addMedicineArrayEntity.setSectionName(getString(R.string.sectionnamemiddle));
        addMedicineArrayEntity.setItems(new ArrayList<XiaoChuanRiJi.AddMedicineArrayEntity.ItemsEntity>());

        final XiaoChuanRiJi.AddMedicineArrayEntity.ItemsEntity middleItemsEntityTimes = new XiaoChuanRiJi.AddMedicineArrayEntity.ItemsEntity();
        middleItemsEntityTimes.setSelectString("");
        middleItemsEntityTimes.setItemName(getString(R.string.usertimes));
        addMedicineArrayEntity.getItems().add(middleItemsEntityTimes);

        xiaoChuanRiJi.getAddMedicineArray().add(addMedicineArrayEntity);
        for (int i = 0; i < linearLayoutMiddle.getChildCount(); i++) {

            if (linearLayoutMiddle.getChildAt(i) instanceof TextView) {
                ((TextView) linearLayoutMiddle.getChildAt(i)).setTag(i - 1);
                ((TextView) linearLayoutMiddle.getChildAt(i)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        final View textViewMiddle = v;
                        inputTitleDialog(new CallbaackString() {
                            @Override
                            public void callBackString(String string) {


                                StringBuffer buffer = new StringBuffer(middleItemsEntityTimes.getSelectString());
                                String[] str = middleItemsEntityTimes.getSelectString().split(",");
                                L.d("ddddddddd=====" + (int) v.getTag());
                                str[(int) v.getTag()] = string;

                                String stringText = "";
                                for (int k = 0; k < str.length; k++) {
                                    if (k == 0) {
                                        stringText = stringText + str[k];
                                    } else {
                                        stringText = stringText + "," + str[k];
                                    }
                                }
                                middleItemsEntityTimes.setSelectString(stringText);
                                ((TextView) textViewMiddle).setText(string);

                                int b = buffer.indexOf(",", 0);
                            }
                        });
                    }
                });

            }

            if (i == 0) {
                middleItemsEntityTimes.setSelectString(middleItemsEntityTimes.getSelectString() + " ");
            } else {
                middleItemsEntityTimes.setSelectString(middleItemsEntityTimes.getSelectString() + " ,");

            }
        }

        final XiaoChuanRiJi.AddMedicineArrayEntity.ItemsEntity middleItemsTextViewEntityTimes = new XiaoChuanRiJi.AddMedicineArrayEntity.ItemsEntity();

        TextView middleTextView = (TextView) findViewById(R.id.xiaochuanriji_add).findViewById(R.id.xiaochuanriji_middle_textview);
        middleItemsTextViewEntityTimes.setItemName("药物名");
        middleItemsEntityTimes.setType("text");

        //添加药物名   因症状加重加吸的药物
        xiaoChuanRiJi.getAddMedicineArray().get(0).getItems().add(middleItemsTextViewEntityTimes);
        middleItemsTextViewEntityTimes.setSelectString(" ");
        middleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View textView = v;

                initPopupView1(v, 1, new CallbaackString() {
                    @Override
                    public void callBackString(String string) {

                        if (string.trim().equals("")) {
                            middleItemsTextViewEntityTimes.setSelectString(" ");
                            Toast.makeText(getApplicationContext(), "药物名为空", Toast.LENGTH_SHORT).show();

                        } else {
                            ((TextView) textView).setText(string);
                            middleItemsTextViewEntityTimes.setSelectString(string);
                        }
                    }
                });
            }
        });

        xiaoChuanRiJi.setUseMedicineArray(new ArrayList<XiaoChuanRiJi.UseMedicineArrayEntity>());
        TextView button = (TextView) findViewById(R.id.xiaochuanriji_add_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.xiaochuanriji_add).findViewById(R.id.bottom_id);

                ViewGroup.LayoutParams lp1 = linearLayout.getLayoutParams();

                final View view = View.inflate(getApplicationContext(), R.layout.xiaochuanriji_item, null);

                linearLayout.addView(view, lp1);

                final XiaoChuanRiJi.UseMedicineArrayEntity useMedicineArrayEntity = new XiaoChuanRiJi.UseMedicineArrayEntity();
                useMedicineArrayEntity.setItems(new ArrayList<XiaoChuanRiJi.UseMedicineArrayEntity.ItemsEntity>());
                useMedicineArrayEntity.setSectionName(getString(R.string.sectionnamebottom));
                LinearLayout bottomLinearLayout = (LinearLayout) view.findViewById(R.id.xiaochuanriji_bottom_id);

                String[] bottomString = {getString(R.string.bottomuseryongliang), getString(R.string.bottomusertimes)};
                for (int k = 1; k < bottomLinearLayout.getChildCount(); k++) {

                    final XiaoChuanRiJi.UseMedicineArrayEntity.ItemsEntity bottomiItemsEntity = new XiaoChuanRiJi.UseMedicineArrayEntity.ItemsEntity();
                    LinearLayout linearLayoutBottom;
                    if (bottomLinearLayout.getChildAt(k) instanceof LinearLayout) {
                        linearLayoutBottom = (LinearLayout) bottomLinearLayout.getChildAt(k);
                    } else {
                        continue;
                    }
                    useMedicineArrayEntity.getItems().add(bottomiItemsEntity);
                    bottomiItemsEntity.setSelectString("");
                    bottomiItemsEntity.setItemName(bottomString[k - 1]);

                    for (int i = 0; i < linearLayoutBottom.getChildCount(); i++) {
                        if (linearLayoutBottom.getChildAt(i) instanceof TextView) {

                            ((TextView) linearLayoutBottom.getChildAt(i)).setTag(i - 1);
                            ((TextView) linearLayoutBottom.getChildAt(i)).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final View textViewBottom = v;
                                    inputTitleDialog(new CallbaackString() {
                                        @Override
                                        public void callBackString(String string) {

                                            StringBuffer buffer = new StringBuffer(bottomiItemsEntity.getSelectString());
                                            String[] str = bottomiItemsEntity.getSelectString().split(",");
                                            str[(int) textViewBottom.getTag()] = string;

                                            String stringText = "";
                                            for (int n = 0; n < str.length; n++) {
                                                if (n == 0) {
                                                    stringText = stringText + str[n];
                                                } else {
                                                    stringText = stringText + "," + str[n];
                                                }
                                            }
                                            bottomiItemsEntity.setSelectString(stringText);
                                            ((TextView) textViewBottom).setText(string);
                                        }
                                    });
                                }
                            });
                        }

                        if ((i - 1) == 0) {
                            bottomiItemsEntity.setSelectString(bottomiItemsEntity.getSelectString() + " ");
                        } else {
                            bottomiItemsEntity.setSelectString(bottomiItemsEntity.getSelectString() + ", ");

                        }
                    }
                }

                TextView deleteTextView = (TextView) view.findViewById(R.id.delete_id);
                deleteTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linearLayout.removeView(view);
                        xiaoChuanRiJi.getUseMedicineArray().remove(useMedicineArrayEntity);
                    }
                });

                TextView bottomTextView = (TextView) view.findViewById(R.id.xiaochuanriji_bottom_textview);


                final XiaoChuanRiJi.UseMedicineArrayEntity.ItemsEntity bottomiItemsTextViewEntity = new XiaoChuanRiJi.UseMedicineArrayEntity.ItemsEntity();
                useMedicineArrayEntity.getItems().add(bottomiItemsTextViewEntity);
                bottomiItemsTextViewEntity.setItemName("药物名");
                bottomiItemsTextViewEntity.setSelectString(" ");

                bottomTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final View bottomTextView = v;

                        initPopupView1(v, 1, new CallbaackString() {
                            @Override
                            public void callBackString(String string) {
                                if (string.trim().equals("")) {
                                    bottomiItemsTextViewEntity.setSelectString("");
                                    Toast.makeText(getApplicationContext(), "药物名为空", Toast.LENGTH_SHORT).show();
                                } else {
                                    ((TextView) bottomTextView).setText(string);
                                    bottomiItemsTextViewEntity.setSelectString(string);
                                }
                            }
                        });

                    }
                });

                xiaoChuanRiJi.getUseMedicineArray().add(useMedicineArrayEntity);

            }
        });


        final FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_id);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionButton.setVisibility(View.INVISIBLE);
                XiaoChuanRiJi xiaoChuanRiJi1 = xiaoChuanRiJi;
                XiaoChuanRiJiModel xiaoChuanRiJiModel = new XiaoChuanRiJiModel();
                xiaoChuanRiJiModel.upLoadData(getApplicationContext(), xiaoChuanRiJi, new XiaoChuanRiJiModel.ResultUploadCallBack() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, Object responseString) {
                        floatingActionButton.setVisibility(View.INVISIBLE);
                        close();
                    }

                    @Override
                    public void onError(int statusCode, Header[] headers, Object responseString) {
                        floatingActionButton.setVisibility(View.INVISIBLE);
                        close();
                    }
                });

                //close();

            }
        });

        ImageView backButton = (ImageView) findViewById(R.id.xiaochuanriji_add_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initPopupView1(View v, int flag, final CallbaackString callbaackString) {

        popupWindow = null;
        if (popupWindow == null) {

            LayoutInflater mLayoutInflater = LayoutInflater.from(this);
            contentView = mLayoutInflater.inflate(R.layout.xiaochuanriji_yaowu_name_popwindow, null);
            final RadioGroup radioGroup = (RadioGroup) contentView.findViewById(R.id.redioGroup_id);

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    Log.i("oo", group.getCheckedRadioButtonId() + "");
                    RadioButton radioButton = (RadioButton) contentView.findViewById(group.getCheckedRadioButtonId());

                    index1 = (int) Integer.parseInt(radioButton.getTag() + "");
                }
            });


            Button cancleButton = (Button) contentView.findViewById(R.id.cancle_id);
            cancleButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
            Button sureButton = (Button) contentView.findViewById(R.id.sure_id);
            sureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callbaackString.callBackString(listYaoWuName.get(index1));
                    popupWindow.dismiss();
                }
            });

            DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
            ;
            popupWindow = new PopupWindow(contentView, dip2px(dm.widthPixels - 100) / 2, (dm.heightPixels - 20) * 4 / 8);
        }

        ColorDrawable cd = new ColorDrawable(0x000000);
        popupWindow.setBackgroundDrawable(cd);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        //popupWindow.showAsDropDown(v, 0, 5);

        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        popupWindow.update();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    public int dip2px(float dpValue) {
        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    void close() {
        Intent intent = new Intent();
        intent.putExtra("result", "返回值");

        this.setResult(RESULT_OK, intent); // 设置结果数据
        this.finish(); // 关闭Activity
    }

    private void inputTitleDialog(final CallbaackString callbaackString) {

        final EditText inputServer = new EditText(this);
        inputServer.setFocusable(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name)).setIcon(
                R.drawable.icon_feedback).setView(inputServer).setNegativeButton("取消", null);
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        callbaackString.callBackString(inputServer.getText().toString());
                    }
                });
        builder.show();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // requestCode用于区分业务
        // resultCode用于区分某种业务的执行情况
        if (1 == requestCode && RESULT_OK == resultCode) {
            String result = data.getStringExtra("result");
            Toast.makeText(this.getBaseContext(), result, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.getBaseContext(), "无返回值", Toast.LENGTH_SHORT).show();
        }
    }

    private interface CallbaackString {

        public void callBackString(String string);

    }
}
