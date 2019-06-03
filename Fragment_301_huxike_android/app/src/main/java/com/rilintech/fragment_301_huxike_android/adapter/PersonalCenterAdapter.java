package com.rilintech.fragment_301_huxike_android.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rilintech.fragment_301_huxike_android.R;
import com.rilintech.fragment_301_huxike_android.activity.LoginActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YANG on 15/11/9.
 */
public class PersonalCenterAdapter extends BaseAdapter {

    private Context context;
    private List<String> list_key;
    private List<String> list_value;
    private LayoutInflater inflater;
    private SharedPreferences shared;
    private SharedPreferences.Editor editor;

    public PersonalCenterAdapter(Context context, List<String> list_key, List<String> list_value) {
        this.context = context;
        this.list_key = list_key;
        this.list_value = list_value;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list_key.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return list_key.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        convertView = null;


        if (position == list_key.size()){//退出登录item
            holder = new ViewHolder();

            convertView = inflater.inflate(R.layout.list_view_item_isbottom, null);
            holder.iv_logout = (ImageView) convertView.findViewById(R.id.iv_logout);

        }else if (position == 0 || position == 3 || position == 8) {//含有组title

            holder = new ViewHolder();

            convertView = inflater.inflate(R.layout.list_view_item_ishead, null);
            holder.tv_key = (TextView) convertView.findViewById(R.id.item_key);
            holder.tv_value = (TextView) convertView.findViewById(R.id.item_value);


        } else {//其余常规item
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.list_view_item_nohead, null);
                holder.tv_key = (TextView) convertView.findViewById(R.id.item_key);
                holder.tv_value = (TextView) convertView.findViewById(R.id.item_value);

                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();

            }
        }

        if (position == list_key.size()) {//退出登录item

            holder.iv_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    shared = context.getSharedPreferences("users", Context.MODE_PRIVATE);
                    editor = shared.edit();
                    editor.putString("user_name", "");
                    editor.putString("user_pwd", "");
                    editor.commit();

                    context.startActivity(new Intent(context, LoginActivity.class));

                    Activity activity = (Activity) context;
                    activity.finish();

                }
            });

        }else {
            holder.tv_key.setText(list_key.get(position));
           // holder.tv_value.setText(list_value.get(position));
        }

        return convertView;
    }


    class ViewHolder {
        TextView tv_key;
        TextView tv_value;
        ImageView iv_logout;
    }

}
