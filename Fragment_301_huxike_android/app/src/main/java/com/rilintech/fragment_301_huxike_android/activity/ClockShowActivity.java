package com.rilintech.fragment_301_huxike_android.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import com.rilintech.fragment_301_huxike_android.R;
import com.rilintech.fragment_301_huxike_android.bean.ClockModel;
import com.rilintech.fragment_301_huxike_android.db.ClockDBUtil;
import com.rilintech.fragment_301_huxike_android.service.AlarmAlertWakeLock;
import com.rilintech.fragment_301_huxike_android.service.AlarmReceiver;
import com.rilintech.fragment_301_huxike_android.utils.ClockGetNextAlarm;
import com.rilintech.fragment_301_huxike_android.view.ClockWarningDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ClockShowActivity extends Activity {

    public static final String KEY_ON = "0";
    public static final String KEY_OFF = "1";
    //每个铃声的uuid
    private int mClockUuid = -1;
    //当前响铃的实例
    private ClockModel clockModel;
    //保存音乐的map
    private Map<String, Integer> map;
    //音乐播放器
    private MediaPlayer alarmMusic;
    //系统震动
    private Vibrator mVibrator;
    //音频管理器
    private AudioManager mAudioManager;
    //当前系统音量
    private int current;
    //系统最大音量
    private int max;

    private MyHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        // 息屏启动
        if (!getIntent().getBooleanExtra("screen_off", false)) {
            win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        }

        mClockUuid = getIntent().getIntExtra("uuid", -1);
        Log.d("hu","传过来的uuid==="+mClockUuid);
        ClockDBUtil clockDBUtil = new ClockDBUtil(this);
        clockDBUtil.openDataBase();
        clockModel = clockDBUtil.getOneClockByUuid(mClockUuid + "");
        clockDBUtil.closeDataBase();

        //启动一分钟响铃计时
        new Thread(new MyThread()).start();
        //设置下次闹铃时间
        setNextAlarm();
        //给音乐map赋值
        setMapData();
        //设置闹铃响
        setAlarm();
        //弹出闹铃提示
        showDialog();


    }

    private class MyHandler extends Handler{
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    try {
                        if (alarmMusic.isPlaying()) {
                            alarmMusic.stop();
                            alarmMusic.reset();
                            alarmMusic.release();
                        }
                    } catch (Exception e) {
                        alarmMusic = null;
                    }
                    mVibrator.cancel();
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, current, AudioManager.FLAG_PLAY_SOUND);
                    break;

                default:
                    break;
            }
        }
    }


    /**
     * 响铃计时线程（一分钟）停止响铃，并启动5分钟后再响
     *
     * @author ykt003
     */
    public class MyThread implements Runnable {
        @Override
        public void run() {
            Looper.prepare();
            try {
                Thread.sleep(60 * 1000);
                Message message = new Message();
                message.what = 1;
                handler = new MyHandler();
                handler.sendMessage(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Looper.loop();
        }
    }

    private void showDialog() {

        ClockWarningDialog.showDialog(this, clockModel, alarmMusic, mVibrator, mAudioManager, current);
    }

    /**
     * 存入音乐数据
     */
    private void setMapData() {

        map = new HashMap<>();

        map.put(getResources().getString(R.string.ring1), R.raw.a);
        map.put(getResources().getString(R.string.ring2), R.raw.b);
        map.put(getResources().getString(R.string.ring3), R.raw.c);
        map.put(getResources().getString(R.string.ring4), R.raw.d);
        map.put(getResources().getString(R.string.ring5), R.raw.e);
        map.put(getResources().getString(R.string.ring6), R.raw.f);
        map.put(getResources().getString(R.string.ring7), R.raw.g);
        map.put(getResources().getString(R.string.ring8), R.raw.h);
        map.put(getResources().getString(R.string.ring9), R.raw.i);
        map.put(getResources().getString(R.string.ring10), R.raw.j);
    }

    /**
     * 设置闹铃
     */
    private void setAlarm() {

        alarmMusic = MediaPlayer.create(this, R.raw.a);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        current = mAudioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);

        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            String key = entry.getKey();
            try {
                if (clockModel.getRing().equals(key)) {
                    alarmMusic = MediaPlayer.create(this, map.get(key));
                }
            } catch (Exception e) {
                alarmMusic = MediaPlayer.create(this, R.raw.a);
            }
        }
        // 设置为循环播放
        alarmMusic.setLooping(true);
        // 获取震动
        mVibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        //震动时长与音乐播放时长一样,放在MediaPlayer.create()之后，不然报错null object reference
        //mVibrator.vibrate(alarmMusic.getDuration());
        mVibrator.vibrate(new long[]{1000, 2000, 1000, 2000, 1000}, 0);
        //调节到最大音量
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, max, AudioManager.FLAG_PLAY_SOUND);

        // 闹铃响
        alarmMusic.start();
    }

    /**
     * 设置下次闹铃响铃时间
     */
    private void setNextAlarm() {

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        // 建立Intent和PendingIntent来调用闹钟管理器
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.setAction(AlarmReceiver.ALARM_ACTION);
        intent.putExtra("uuid", Integer.parseInt(clockModel.getUuid()));
        Log.d("hu","设置下次响铃的uuid===="+Integer.parseInt(clockModel.getUuid()));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                Integer.parseInt(clockModel.getUuid()), intent, 0);

        String flag = KEY_OFF;
        //看是否有间隔时间
        if (!clockModel.getSpace().equals("0")) {//有间隔时间
            //计算今晚凌晨的时间
            long nextDayTime = ClockGetNextAlarm.getTonightLastTime(System.currentTimeMillis());
            //下次响铃时间的毫秒
            long nextCurrentTimeMillis = ClockGetNextAlarm.getNextAlarm(System.currentTimeMillis(),
                    clockModel.getTime(), clockModel.getSpace());

            if (nextCurrentTimeMillis <= nextDayTime) {//第二次响铃小于凌晨时间（在当天）
                //闹铃响
                alarmManager.set(AlarmManager.RTC_WAKEUP, nextCurrentTimeMillis, pendingIntent);
                flag = KEY_ON;
                ClockGetNextAlarm.L("showActivity", nextCurrentTimeMillis);

            } else {//第二次响铃大于凌晨时间（不在当天，明天）
                //每周可以重复响铃的次数
                int counts = 7 * 24 / (Integer.parseInt(clockModel.getSpace()));
                for (int i = 0; i < counts; i++) {
                    //下次响铃时间的毫秒
                    if (i == 0) {
                        //已经是下次响铃时间了
                        nextCurrentTimeMillis += 0;
                    } else {
                        nextCurrentTimeMillis += Integer.parseInt(clockModel.getSpace()) * AlarmManager.INTERVAL_HOUR;
                    }
                    //下次响铃时间所在的日期是否包含在重复的日期里
                    boolean bl = ClockGetNextAlarm.isContainsInRepeat(nextCurrentTimeMillis, clockModel.getRepeat());
                    if (bl) {//包含
                        //设置响铃时间为此时的时间，并且跳出循环
                        alarmManager.set(AlarmManager.RTC_WAKEUP, nextCurrentTimeMillis, pendingIntent);
                        ClockGetNextAlarm.L("showActivity", nextCurrentTimeMillis);
                        flag = KEY_ON;
                        break;
                    } else {//不包含
                        //继续循环
                        flag = KEY_OFF;
                    }
                }
            }
        } else {//无间隔时间

            if (clockModel.getRepeat().equals("7,7,7,7,7,7,7")) {//代表没有重复日期
                //开关要关掉
                flag = KEY_OFF;
            } else {//有重复日期
                calendar.setTimeInMillis(ClockGetNextAlarm.getNextAlarmTime(
                        ClockGetNextAlarm.DATE_MODE_WEEK,
                        clockModel.getRepeat(),
                        clockModel.getTime()));
                //设置闹铃
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                ClockGetNextAlarm.L("showActivity", calendar.getTimeInMillis());
                //开关打开
                flag = KEY_ON;
            }
        }

        clockModel.setmSwitch(flag);
        //更新开关状态
        ClockDBUtil clockDBUtil = new ClockDBUtil(this);
        clockDBUtil.openDataBase();
        clockDBUtil.updateMswitch(Integer.parseInt(clockModel.getmSwitch()),
                clockModel.getId());
        clockDBUtil.closeDataBase();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_HOME:

                break;
            case KeyEvent.KEYCODE_BACK:

                break;
            case KeyEvent.KEYCODE_MENU:

                break;
            case KeyEvent.KEYCODE_APP_SWITCH:

                break;
            default:
                break;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        AlarmAlertWakeLock.releaseCpuLock();
        super.onDestroy();
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
