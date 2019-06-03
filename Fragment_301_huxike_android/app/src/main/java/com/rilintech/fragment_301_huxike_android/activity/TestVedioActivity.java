package com.rilintech.fragment_301_huxike_android.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.rilintech.fragment_301_huxike_android.R;

import java.io.File;
import java.io.IOException;

/**
 * Created by rilintech on 16/4/18.
 */
public class TestVedioActivity extends BaseActivity{

    private MediaPlayer mp;
    private SurfaceView sv;
    private ImageView boImageView;
    private boolean flag,flag2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio);
        mp=new MediaPlayer();
        boImageView = (ImageView)findViewById(R.id.image_s);
        sv=(SurfaceView)findViewById(R.id.surfaceView1);
        Button play=(Button)findViewById(R.id.play);
        final Button pause=(Button)findViewById(R.id.pause);
        Button stop=(Button)findViewById(R.id.stop);

        play.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mp.reset();
                try {
                    mp.setDataSource("/sdcard/1.mp4");
                    mp.setDisplay(sv.getHolder());
                    mp.prepare();
                    mp.start();
                    pause.setText("暂停");
                    pause.setEnabled(true);
                }catch(IllegalArgumentException e) {
                    e.printStackTrace();
                }catch(SecurityException e) {
                    e.printStackTrace();
                }catch(IllegalStateException e) {
                    e.printStackTrace();
                }catch(IOException e) {
                    e.printStackTrace();
                }

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(mp.isPlaying()){
                    mp.stop();
                    pause.setEnabled(false);
                }

            }
        });

        pause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(mp.isPlaying()){
                    mp.pause();
                    ((Button)v).setText("继续");
                }else{
                    mp.start();
                    ((Button)v).setText("暂停");
                }

            }
        });

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){

            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(TestVedioActivity.this, "视频播放完毕！", Toast.LENGTH_SHORT).show();
            }
        });


        boImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!flag) {
                    mp.reset();
                    try {
                        mp.setDataSource("/sdcard/1.mp4");
                        mp.setDisplay(sv.getHolder());
                        mp.prepare();
                        mp.start();
                        pause.setText("暂停");
                        pause.setEnabled(true);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    boImageView.setVisibility(View.INVISIBLE);
                }else {
                    if(mp.isPlaying()){
                        mp.pause();
                    }else{
                        mp.start();
                    }
                    boImageView.setVisibility(View.INVISIBLE);
                }

            }
        });

        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boImageView.setVisibility(View.VISIBLE);
                if(mp.isPlaying()){
                    mp.pause();
                }else{
                    mp.start();
                }
                flag = true;
            }
        });

    }
    @Override
    protected void onDestroy() {
        if(mp.isPlaying()){
            mp.stop();
        }
        mp.release();
        super.onDestroy();
    }

    /**
     * 返回键
     * @param view
     */
    public void onClick(View view){

        this.finish();
    }

}



