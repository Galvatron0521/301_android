package com.rilintech.fragment_301_huxike_android.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by YANG on 15/10/21.
 */
public class AlarmReceiver extends BroadcastReceiver {


    /* (non-Javadoc)
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
     */
    public static final String ALARM_ACTION = "com.rilintech.hxk_301.service.AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent data) {
        if (data.getAction().equals(ALARM_ACTION)) {

            Log.d("hu", "uuid===== " + data.getIntExtra("uuid", -1));
            AlarmAlertWakeLock.acquireCpuWakeLock(context);
            //弹出Activity提示闹铃
            /*Intent intent = new Intent(context, ClockShowActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("uuid", data.getIntExtra("uuid", -1));
            context.startActivity(intent);*/

            //弹出系统级别的dialog提示闹铃
            Intent intent = new Intent(context, AlarmService.class);
            intent.setAction(AlarmService.ALARM_SERVICE_ACTION);
            intent.putExtra("uuid", data.getIntExtra("uuid", -1));
            context.startService(intent);

        }
    }
}
