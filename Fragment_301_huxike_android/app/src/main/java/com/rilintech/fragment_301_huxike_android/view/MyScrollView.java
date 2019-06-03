package com.rilintech.fragment_301_huxike_android.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by YANG on 15/11/4.
 */
public class MyScrollView extends LinearLayout {

    private Context context;
    //ScrollView按下是的Y坐标
    private float startY;
    //ScrollView按下是的时间
    private long startTime;

    public static final String SCROLL_ACTION = "com.rilintech.hxk_301.view.MyScrollView";

    public MyScrollView(Context context) {
        super(context);
        this.context = context;
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        boolean flag = false;
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getY();
                startTime = System.currentTimeMillis();
                Log.d("hu", "1111startY=" + startY);
                flag = false;
                break;
            case MotionEvent.ACTION_MOVE:

                flag = false;
                break;
            case MotionEvent.ACTION_UP:
                if (System.currentTimeMillis() - startTime < 1000) {
                    Log.d("hu", "1111vent.getY()=" + event.getY());
                    if (event.getY() - startY >= 500) {

                        Intent intent = new Intent(SCROLL_ACTION);
                        context.sendBroadcast(intent);

                        flag = true;
                    }
                }

                break;
        }
        /*return super.onInterceptTouchEvent(event);*/
        return flag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getY();
                startTime = System.currentTimeMillis();
                Log.d("hu", "222startY=" + startY);
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                if (System.currentTimeMillis() - startTime < 1000) {
                    Log.d("hu", "222vent.getY()=" + event.getY());
                    if (event.getY() - startY >= 500) {
                        Intent intent = new Intent(SCROLL_ACTION);
                        context.sendBroadcast(intent);
                    }
                }

                break;
        }

        return super.onTouchEvent(event);
    }
}
