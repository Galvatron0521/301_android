package com.rilintech.fragment_301_huxike_android.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.rilintech.fragment_301_huxike_android.R;
import com.rilintech.fragment_301_huxike_android.bean.ClockModel;

/**
 * Created by YANG on 15/10/29.
 */
public class ClockWarningDialog {

    private static AlertDialog dialog;
    private static TextView clock_dialog_tag;
    private static TextView clock_dialog_time;
    private static TextView clock_dialog_ok;
    private static Activity activity;

    public static void showDialog(final Context context,
                                  ClockModel clockModel,
                                  final MediaPlayer alarmMusic,
                                  final Vibrator mVibrator,
                                  final AudioManager mAudioManager,
                                  final int current) {

        activity = (Activity) context;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.clock_dialog_layout, null);

        clock_dialog_tag = (TextView) view.findViewById(R.id.clock_dialog_tag);
        clock_dialog_time = (TextView) view.findViewById(R.id.clock_dialog_alarm);
        clock_dialog_ok = (TextView) view.findViewById(R.id.clock_dialog_ok);

        try {
            clock_dialog_tag.setText(clockModel.getTag());
            clock_dialog_time.setText(clockModel.getTime());
        }catch (Exception e){
            clock_dialog_tag.setText("闹铃");
            clock_dialog_time.setText("12：00");
        }

        clock_dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (alarmMusic.isPlaying()) {
                    alarmMusic.stop();
                    alarmMusic.reset();
                    alarmMusic.release();
                }
                //恢复系统之前的音量
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, current, AudioManager.FLAG_PLAY_SOUND);
                mVibrator.cancel();
                activity.finish();
            }
        });

        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.getWindow().setContentView(view);

    }


}
