package com.rilintech.fragment_301_huxike_android.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rilintech.fragment_301_huxike_android.R;
import com.rilintech.fragment_301_huxike_android.activity.PersonalCenterEditActivity;
import com.rilintech.fragment_301_huxike_android.adapter.ClockIndexListViewAdapter;
import com.rilintech.fragment_301_huxike_android.adapter.PersonalCenterAdapter;
import com.rilintech.fragment_301_huxike_android.bean.PatientInfo;
import com.rilintech.fragment_301_huxike_android.db.PatientInfoManager;

import java.util.ArrayList;


/**
 * Created by rilintech on 15/7/28.
 */
public class FragmentPersonalCenter extends Fragment {

    private PatientInfo patientInfo;
    //listView
    private ListView mListView;
    //listView 加载器
    private PersonalCenterAdapter personalCenterAdapter;
    //数据集合
    private ArrayList<String> list_key;
    private ArrayList<String> list_value;
    //是否获得了数据
    private int flag;
    //获得数据
    private static final int GETDATA = 1;
    //未获得数据
    private static final int DONTDATA = 0;
    //记录当前的ListView的位置
    private int CURRENT_LISTVIEW_ITEM_POSITION = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_personal_center_listview, container, false);

        initView(view);

       // new MyAsyncTask().execute();

        putData();
        setListener(getActivity());

        return view;
    }


    private class MyAsyncTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            return putData();
        }

        @Override
        protected void onPostExecute(Object o) {

            if ((int) o == GETDATA) {
                setListener(getActivity());
            }

            super.onPostExecute(o);
        }
    }

    private int putData() {

        list_key.add(getResources().getString(R.string.patient_name));
        list_key.add(getResources().getString(R.string.patient_sex));
        list_key.add(getResources().getString(R.string.patient_age));
        list_key.add(getResources().getString(R.string.patient_bedNO));
        list_key.add(getResources().getString(R.string.patient_identity_card));
        list_key.add(getResources().getString(R.string.patient_height));
        list_key.add(getResources().getString(R.string.patient_weight));
        list_key.add(getResources().getString(R.string.patient_phone));
        list_key.add(getResources().getString(R.string.patient_zip_code));
        list_key.add(getResources().getString(R.string.patient_address));
        list_key.add(getResources().getString(R.string.patient_company));
        list_key.add(getResources().getString(R.string.patient_hospital));
        list_key.add(getResources().getString(R.string.patient_describe));

        PatientInfoManager patientInfoManager = new PatientInfoManager(getActivity());
        patientInfoManager.openDataBase();
        patientInfo = patientInfoManager.patientInfo();
        patientInfoManager.closeDataBase();
        if (patientInfo != null) {
            list_value.add(patientInfo.getName());
            list_value.add(patientInfo.getSex());
            list_value.add(patientInfo.getAge());
            list_value.add(patientInfo.getBedNO());
            list_value.add(patientInfo.getIdentity_card());
            list_value.add(patientInfo.getHeight());
            list_value.add(patientInfo.getWeight());
            list_value.add(patientInfo.getPhone());
            list_value.add(patientInfo.getZip_code());
            list_value.add(patientInfo.getAddress());
            list_value.add(patientInfo.getCompany());
            list_value.add(patientInfo.getHospital());
            list_value.add(patientInfo.getDescribe());
            flag = GETDATA;
        }
        return flag;

    }

    //设置监听
    private void setListener(Context context) {
        try {
            personalCenterAdapter = new PersonalCenterAdapter(context, list_key, list_value);
            mListView.setAdapter(personalCenterAdapter);
            mListView.setOnItemClickListener(new MyOnItemClickListener());
            ////回到原来的位置
            mListView.setSelection(CURRENT_LISTVIEW_ITEM_POSITION);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class MyOnItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), PersonalCenterEditActivity.class);

            if (position == list_key.size()) {

            } else {

                intent.putExtra("position", position);
                intent.putStringArrayListExtra("list_key", list_key);
                intent.putStringArrayListExtra("list_value", list_value);
                startActivityForResult(intent, 1);
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            list_key = new ArrayList<>();
            list_value = new ArrayList<>();
            MyAsyncTask task = new MyAsyncTask();
            task.execute();
            personalCenterAdapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void initView(View view) {

        flag = DONTDATA;
        mListView = (ListView) view.findViewById(R.id.listview_personal_center);
        list_key = new ArrayList<>();
        list_value = new ArrayList<>();

    }

    @Override
    public void onPause() {
        super.onPause();
        //得到当前ListView可见部分的第一个位置
        CURRENT_LISTVIEW_ITEM_POSITION = mListView.getFirstVisiblePosition();
    }

}
