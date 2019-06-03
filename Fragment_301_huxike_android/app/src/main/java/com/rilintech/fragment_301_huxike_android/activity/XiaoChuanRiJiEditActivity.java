package com.rilintech.fragment_301_huxike_android.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.utils.L;
import com.rilintech.fragment_301_huxike_android.R;
import com.rilintech.fragment_301_huxike_android.bean.XiaoChuanRiJi;
import com.rilintech.fragment_301_huxike_android.http.XiaoChuanRiJiModel;
import com.rilintech.fragment_301_huxike_android.utils.ProgressUtil;
import com.rilintech.fragment_301_huxike_android.utils.SingleRiJi;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mac on 16/4/18.
 */
public class XiaoChuanRiJiEditActivity extends BaseActivity {


    private List<XiaoChuanRiJi> viewList;
    public View contentView;
    public PopupWindow popupWindow;
    public int index1 = 0;

    public ViewPager viewPager;
    public TextView textView;
    List<String> listYaoWuName;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.xiaochuanrijiedit_activity);
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

        XiaoChuanRiJiModel xiaoChuanRiJiModel = new XiaoChuanRiJiModel();
//        ProgressUtil.getInstance().showProgress(activity, "正在加载...");
        xiaoChuanRiJiModel.downloadIPatientModels(activity, new XiaoChuanRiJiModel.ResultDownCallBack() {
            @Override
            public void onDownSuccess(int statusCode, Header[] headers, final Object responseString) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getinfo((List<XiaoChuanRiJi>) responseString);
                        ProgressUtil.getInstance().showSuccessProgress("加载完毕...");
                    }
                });
            }

            @Override
            public void onDownError(int statusCode, Header[] headers, Object responseString) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProgressUtil.getInstance().showErrorProgress("加载失败...");
                    }
                });
            }
        });


        TextView textView = (TextView) findViewById(R.id.add_xiaochuanriji);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewList == null) {
                    Intent intent = new Intent(XiaoChuanRiJiEditActivity.this, XiaoChuanRiJiActivity.class);
                    intent.putExtra("week", 1);
                    SingleRiJi.getInstance().setViewList(viewList);
                    startActivityForResult(intent, 0);
                } else {
                    Intent intent = new Intent(XiaoChuanRiJiEditActivity.this, XiaoChuanRiJiActivity.class);
                    intent.putExtra("week", viewList.size() + 1);
                    SingleRiJi.getInstance().setViewList(viewList);
                    startActivityForResult(intent, 0);

                }
            }
        });

        final FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_id);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                XiaoChuanRiJiModel xiaoChuanRiJiModel = new XiaoChuanRiJiModel();
                xiaoChuanRiJiModel.updateData(getApplicationContext(), viewList, new XiaoChuanRiJiModel.ResultUploadCallBack() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, Object responseString) {
                        ProgressUtil.getInstance().showProgress(activity, "正在加载...");
                        XiaoChuanRiJiModel xiaoChuanRiJiModel1 = new XiaoChuanRiJiModel();
                        floatingActionButton.setVisibility(View.INVISIBLE);
                        xiaoChuanRiJiModel1.downloadIPatientModels(activity, new XiaoChuanRiJiModel.ResultDownCallBack() {
                            @Override
                            public void onDownSuccess(int statusCode, Header[] headers, final Object responseString) {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getinfo((List<XiaoChuanRiJi>) responseString);
                                        ProgressUtil.getInstance().showSuccessProgress("加载完毕...");
                                        floatingActionButton.setVisibility(View.VISIBLE);
                                    }
                                });
                            }

                            @Override
                            public void onDownError(int statusCode, Header[] headers, Object responseString) {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ProgressUtil.getInstance().showErrorProgress("加载失败...");
                                        floatingActionButton.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onError(int statusCode, Header[] headers, Object responseString) {

                    }
                });


            }
        });

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
//                                 String inputName = inputServer.getText().toString();
                        callbaackString.callBackString(inputServer.getText().toString());
                    }
                });
        builder.show();


    }

    PagerAdapter pagerAdapter = new PagerAdapter() {

        View pageView;

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {

            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {


            container.removeView((View) object);

        }

        @Override
        public int getItemPosition(Object object) {

            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            //return titleList.get(position);//直接用适配器来完成标题的显示，所以从上面可以看到，我们没有使用PagerTitleStrip。当然你可以使用。
            return "1111111";

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {


            pageView = initView(viewList.get(position));
            container.addView(pageView);

            return pageView;
        }

    };


    public void getinfo(List<XiaoChuanRiJi> xiaoChuanRiJisList) {

        textView = (TextView) findViewById(R.id.weekselect_id);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initPopupView(v, 100);

            }
        });

        List<XiaoChuanRiJi> list = xiaoChuanRiJisList;
        viewList = xiaoChuanRiJisList;

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(viewList.size());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                XiaoChuanRiJi xiaoChuanRiJipop = viewList.get(position);
                textView.setText("第" + xiaoChuanRiJipop.getWeek() + "周");
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public int dip2px(float dpValue) {
        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void initPopupView(View v, int flag) {

        if (popupWindow == null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(this);
            contentView = mLayoutInflater.inflate(R.layout.week_popwindow_listview, null);
            ListView listView = (ListView) contentView.findViewById(R.id.popwindow_lsit_id);
            listView.setAdapter(new listViewAdater());
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    viewPager.setCurrentItem(position);
                    popupWindow.dismiss();
                    XiaoChuanRiJi xiaoChuanRiJipop = viewList.get(position);
                    textView.setText("第" + xiaoChuanRiJipop.getWeek() + "周");
                }
            });
            popupWindow = new PopupWindow(contentView, dip2px(100), 400);
        }

        ColorDrawable cd = new ColorDrawable(0x000000);
        popupWindow.setBackgroundDrawable(cd);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(v, 0, 5);

        //popupWindow.showAtLocation(v, Gravity.CENTER,0,0);
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
            popupWindow = new PopupWindow(contentView, dm.widthPixels - 40, (dm.heightPixels - 20) * 3 / 9);
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

    private class listViewAdater extends BaseAdapter {

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.popwindow_item, null);

            }
            TextView textView = (TextView) convertView.findViewById(R.id.listview_textview_id);
            XiaoChuanRiJi xiaoChuanRiJipop = viewList.get(position);
            textView.setText("第" + xiaoChuanRiJipop.getWeek() + "周");
            return convertView;
        }
    }

    private class listPopViewAdater extends BaseAdapter {

        @Override
        public int getCount() {

            return listYaoWuName.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.xiaohcuanriji_yaowu_name_item, null);

            }
            TextView textView = (TextView) convertView.findViewById(R.id.xiaochuanriji_yaowuname__item_id);
            textView.setText(listYaoWuName.get(position));
            return convertView;
        }
    }


    private View initView(XiaoChuanRiJi xiaoChuanRiJi1) {

        final XiaoChuanRiJi xiaoChuanRiJi = xiaoChuanRiJi1;
        LayoutInflater lf = getLayoutInflater().from(this);
        final View listView = lf.inflate(R.layout.xiaochuantiji_activity, null);

        final LinearLayout linearLayout = (LinearLayout) listView.findViewById(R.id.xiaochuanriji_top_id);
        int a = linearLayout.getChildCount();

        String[] itemNames = {getString(R.string.kesou), getString(R.string.chuanxi),
                getString(R.string.qiji), getString(R.string.xiaongmen),
                getString(R.string.yejianxiaochuan), getString(R.string.xiaochuanshirihuodong)
        };

        for (int i = 2; i < linearLayout.getChildCount(); i++) {

            if (linearLayout.getChildAt(i) instanceof LinearLayout) {

                final XiaoChuanRiJi.SymptomArrayEntity.ItemsEntity itemsEntity = xiaoChuanRiJi.getSymptomArray().get(0).getItems().get(i - 2);
                itemsEntity.setItemName(itemNames[i - 2]);
                itemsEntity.setType("checkbox");
                for (int k = 1; k < ((LinearLayout) linearLayout.getChildAt(i)).getChildCount(); k++) {

                    if (((LinearLayout) linearLayout.getChildAt(i)).getChildAt(k) instanceof ImageView) {
                        ((ImageView) ((LinearLayout) linearLayout.getChildAt(i)).getChildAt(k)).setTag((k - 1) * 2);
                        if (itemsEntity.getSelectString() == null) {
                            itemsEntity.setSelectString("0,0,0,0,0,0,0");
                        }
                        String[] selectString = itemsEntity.getSelectString().split(",");
                        if (selectString[k - 1].equals("1")) {
                            ((ImageView) ((LinearLayout) linearLayout.getChildAt(i)).getChildAt(k)).setSelected(true);
                            ((ImageView) ((LinearLayout) linearLayout.getChildAt(i)).getChildAt(k)).setImageResource(R.drawable.xiaochuanriji_duihao);

                        }

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
                }

            }
        }

        LinearLayout linearLayoutMiddle = (LinearLayout) listView.findViewById(R.id.xiaochuanriji_middle);
        XiaoChuanRiJi.AddMedicineArrayEntity addMedicineArrayEntity = (XiaoChuanRiJi.AddMedicineArrayEntity) xiaoChuanRiJi.getAddMedicineArray().get(0);
        XiaoChuanRiJi.AddMedicineArrayEntity.ItemsEntity middleItemsEntityTimes1 = addMedicineArrayEntity.getItems().get(0);
        if (addMedicineArrayEntity.getItems().size() > 1) {
            middleItemsEntityTimes1 = addMedicineArrayEntity.getItems().get(1);
        }

        final XiaoChuanRiJi.AddMedicineArrayEntity.ItemsEntity middleItemsEntityTimes = middleItemsEntityTimes1;
        if (middleItemsEntityTimes.getSelectString() == null || middleItemsEntityTimes.getSelectString().equals("")) {
            middleItemsEntityTimes.setSelectString(" , , , , , , ");
        }
        String[] slectString = middleItemsEntityTimes.getSelectString().split(",");
        int stringLength = 7 - slectString.length;
        ArrayList<String> listString = new ArrayList<String>();
        for (int i = 0; i < slectString.length; i++) {
            listString.add(slectString[i]);
        }

        for (int i = 0; i < stringLength; i++) {
            listString.add(" ");
        }


        for (int i = 1; i < linearLayoutMiddle.getChildCount(); i++) {

            if (linearLayoutMiddle.getChildAt(i) instanceof TextView) {
                ((TextView) linearLayoutMiddle.getChildAt(i)).setTag(i - 1);
                ((TextView) linearLayoutMiddle.getChildAt(i)).setText(listString.get(i - 1) + "");
                ((TextView) linearLayoutMiddle.getChildAt(i)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        final View textViewMiddle = v;
                        inputTitleDialog(new CallbaackString() {
                            @Override
                            public void callBackString(String string) {

                                StringBuffer buffer = new StringBuffer(middleItemsEntityTimes.getSelectString());
                                String[] str = middleItemsEntityTimes.getSelectString().split(",");
                                str[(int) v.getTag()] = string;

                                String stringText = " ";
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
        }

        final XiaoChuanRiJi.AddMedicineArrayEntity.ItemsEntity middleItemsTextViewEntityTimes = addMedicineArrayEntity.getItems().get(0);

        TextView middleTextView = (TextView) listView.findViewById(R.id.xiaochuanriji_middle_textview);
        middleTextView.setText(middleItemsTextViewEntityTimes.getSelectString());
        middleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View textView = v;

                initPopupView1(v, 1, new CallbaackString() {
                    @Override
                    public void callBackString(String string) {
                        for (XiaoChuanRiJi.UseMedicineArrayEntity useMedicineArrayEntity : xiaoChuanRiJi.getUseMedicineArray()) {
                            if (useMedicineArrayEntity.getItems().get(0).getItemName().equals(string)) {

                                Toast.makeText(getApplicationContext(), "已经添加该药物", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        ((TextView) textView).setText(string);
                        middleItemsTextViewEntityTimes.setSelectString(string);
                    }
                });

            }
        });

        for (int l = 0; l < xiaoChuanRiJi.getUseMedicineArray().size(); l++) {

            final LinearLayout linearLayout1 = (LinearLayout) listView.findViewById(R.id.bottom_id);

            ViewGroup.LayoutParams lp1 = linearLayout.getLayoutParams();
            final View view = View.inflate(getApplicationContext(), R.layout.xiaochuanriji_item, null);

            linearLayout1.addView(view, lp1);
            final XiaoChuanRiJi.UseMedicineArrayEntity useMedicineArrayEntity = xiaoChuanRiJi.getUseMedicineArray().get(l);
            TextView deleteTextView = (TextView) view.findViewById(R.id.delete_id);
            deleteTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    linearLayout1.removeView(view);
                    xiaoChuanRiJi.getUseMedicineArray().remove(useMedicineArrayEntity);
                }
            });

            LinearLayout bottomLinearLayout = (LinearLayout) view.findViewById(R.id.xiaochuanriji_bottom_id);
            String[] bottomString = {getString(R.string.bottomuseryongliang), getString(R.string.bottomusertimes)};

            for (int k = 1; k < bottomLinearLayout.getChildCount(); k++) {

                LinearLayout linearLayoutBottom;
                if (bottomLinearLayout.getChildAt(k) instanceof LinearLayout) {
                    linearLayoutBottom = (LinearLayout) bottomLinearLayout.getChildAt(k);
                } else {
                    continue;
                }
                final XiaoChuanRiJi.UseMedicineArrayEntity.ItemsEntity bottomiItemsEntity = useMedicineArrayEntity.getItems().get(k);
                if (bottomiItemsEntity.getSelectString() == null) {
                    bottomiItemsEntity.setSelectString(" , , , , , , , ");
                }
                String[] slectString1 = bottomiItemsEntity.getSelectString().split(",");
                int stringLength1 = 7 - slectString1.length;
                ArrayList<String> listString1 = new ArrayList<String>();
                for (int i = 0; i < slectString1.length; i++) {
                    listString1.add(slectString1[i]);
                }

                for (int i = 0; i < stringLength1; i++) {
                    listString1.add(" ");
                }

                for (int i = 1; i < linearLayoutBottom.getChildCount(); i++) {

                    if (linearLayoutBottom.getChildAt(i) instanceof TextView) {

                        ((TextView) linearLayoutBottom.getChildAt(i)).setText(listString1.get(i - 1));
                        ((TextView) linearLayoutBottom.getChildAt(i)).setTag(i-1);
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

                                        String stringText = " ";
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
                }
            }

            TextView bottomTextView = (TextView) view.findViewById(R.id.xiaochuanriji_bottom_textview);
            final XiaoChuanRiJi.UseMedicineArrayEntity.ItemsEntity bottomiItemsTextViewEntity = useMedicineArrayEntity.getItems().get(0);
            bottomiItemsTextViewEntity.setItemName("药物名");
            bottomTextView.setText(bottomiItemsTextViewEntity.getSelectString());
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
        }


        TextView button = (TextView) listView.findViewById(R.id.xiaochuanriji_add_id);
        if (xiaoChuanRiJi.getUseMedicineArray() == null) {
            xiaoChuanRiJi.setUseMedicineArray(new ArrayList<XiaoChuanRiJi.UseMedicineArrayEntity>());
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LinearLayout linearLayout = (LinearLayout) listView.findViewById(R.id.bottom_id);

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
                    bottomiItemsEntity.setSelectString(" ");
                    bottomiItemsEntity.setItemName(bottomString[k - 1]);

                    for (int i = 1; i < linearLayoutBottom.getChildCount(); i++) {
                        if (linearLayoutBottom.getChildAt(i) instanceof TextView) {

                            ((TextView) linearLayoutBottom.getChildAt(i)).setTag(i-1);
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

                                            String stringText = " ";
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
        return listView;
    }

    private interface CallbaackString {

        public void callBackString(String string);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        XiaoChuanRiJiModel xiaoChuanRiJiModel = new XiaoChuanRiJiModel();
        ProgressUtil.getInstance().showProgress(activity, "正在加载...");
        xiaoChuanRiJiModel.downloadIPatientModels(activity, new XiaoChuanRiJiModel.ResultDownCallBack() {
            @Override
            public void onDownSuccess(int statusCode, Header[] headers, final Object responseString) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getinfo((List<XiaoChuanRiJi>) responseString);
                        ProgressUtil.getInstance().showSuccessProgress("加载完毕...");
                    }
                });
            }

            @Override
            public void onDownError(int statusCode, Header[] headers, Object responseString) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProgressUtil.getInstance().showErrorProgress("加载失败...");
                    }
                });
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        MobclickAgent.onResume(this);
    }

}
