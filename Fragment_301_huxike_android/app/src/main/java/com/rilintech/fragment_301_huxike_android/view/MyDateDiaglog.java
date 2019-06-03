package com.rilintech.fragment_301_huxike_android.view;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.rilintech.fragment_301_huxike_android.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rilintech on 16/4/13.
 */
public class MyDateDiaglog {

    private static MyDateDiaglog instance = null;
    private String  timeString;
    private Dialog dialog;
    private  AlertDialog.Builder alterDialog;

    public static MyDateDiaglog getInstance() {
        if (instance == null) {
            instance = new MyDateDiaglog();
        }
        return instance;
    }

    private MyDateDiaglog(){}

    public void showDialog(final Activity context, String titleInfo, final MyDateDiaglog.DialogCallBack callBack) {
        // 取得自定义View
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View myLoginView = layoutInflater.inflate(R.layout.layout_date, null);

        final DatePicker datePicker = (DatePicker)myLoginView.findViewById(R.id.date);
        TextView tittleTextView = (TextView)myLoginView.findViewById(R.id.image);
        //tittleTextView.setText(intent.getStringExtra("value"));
        Button cancleButton = (Button)myLoginView.findViewById(R.id.cancle);
        Button commitButton = (Button)myLoginView.findViewById(R.id.commit);

        AlertDialog.Builder alterDialog = new AlertDialog.Builder(context);
        final AlertDialog  dialog = alterDialog.create();
        dialog.setCancelable(false);
        dialog.show();
        dialog.setContentView(myLoginView);

        Date date = new Date();//获取当前日期对象
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        timeString = format.format(date);
        Calendar calendar = Calendar.getInstance();

        datePicker.init(calendar.YEAR, calendar.MONTH, calendar.DAY_OF_MONTH, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //获取一个日历对象，并初始化为当前选中的时间
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
                timeString = format.format(calendar.getTime());
                callBack.exectEvent(timeString);
                dialog.dismiss();
            }
        });

        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.exectEvent(timeString);
                dialog.dismiss();
            }
        });
    }

    public interface DialogCallBack {
        void exectEvent(String timeString);
    }
}
