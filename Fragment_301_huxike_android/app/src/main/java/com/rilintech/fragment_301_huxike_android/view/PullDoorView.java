package com.rilintech.fragment_301_huxike_android.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.rilintech.fragment_301_huxike_android.R;


public class PullDoorView extends RelativeLayout {

    private Context mContext;

    private Scroller mScroller;
    private Scroller mScroller2;

    private int mScreenWidth = 0;

    private int mScreenHeigh = 0;

    private int mLastDownY = 0;

    private int mCurryY;

    private int mDelY;

    public boolean mCloseFlag = false;

    public boolean mOnTouchFlag = false;

    private ImageView mImgView;


    public PullDoorView(Context context) {
        super(context);
        mContext = context;
        setupView();
        new Thread(new ClosePullDoorViewThread()).start();
        IntentFilter filter = new IntentFilter(MyScrollView.SCROLL_ACTION);
        mContext.registerReceiver(mBroadcastReceiver, filter);
    }

    public PullDoorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setupView();
        new Thread(new ClosePullDoorViewThread()).start();
        IntentFilter filter = new IntentFilter(MyScrollView.SCROLL_ACTION);
        mContext.registerReceiver(mBroadcastReceiver, filter);
    }

    private void setupView() {

        // 这个Interpolator你可以设置别的 我这里选择的是有弹跳效果的Interpolator
        Interpolator polator = new BounceInterpolator();
        //以常量速率改变
        Interpolator polator2 = new LinearInterpolator();
        mScroller = new Scroller(mContext, polator);
        mScroller2 = new Scroller(mContext, polator2);

        // 获取屏幕分辨率
        WindowManager wm = (WindowManager) (mContext
                .getSystemService(Context.WINDOW_SERVICE));
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenHeigh = dm.heightPixels;
        mScreenWidth = dm.widthPixels;

        // 这里你一定要设置成透明背景,不然会影响你看到底层布局
        this.setBackgroundColor(Color.argb(0, 0, 0, 0));
        mImgView = new ImageView(mContext);
        mImgView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        mImgView.setScaleType(ImageView.ScaleType.FIT_XY);// 填充整个屏幕
        mImgView.setImageResource(R.drawable.pull_door_image); // 默认背景
        addView(mImgView);
    }

    // 设置推动门背景
    public void setBgImage(int id) {
        mImgView.setImageResource(id);
    }

    // 设置推动门背景
    public void setBgImage(Drawable drawable) {
        mImgView.setImageDrawable(drawable);
    }

    // 推动门的动画
    public void startBounceAnim(int startY, int dy, int duration) {
        if (mOnTouchFlag) {
            mScroller.startScroll(0, startY, 0, dy, duration);
            invalidate();
        }else {
            mScroller2.startScroll(0, startY, 0, dy, duration);
            invalidate();
        }
    }

    //下滑，重新显示
    public void reStartBounceAnim(int startY, int dy, int duration){
        this.setVisibility(View.VISIBLE);
        mScroller.startScroll(0, startY, 0, dy, duration);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mOnTouchFlag = true;
                mLastDownY = (int) event.getY();
                System.err.println("ACTION_DOWN=" + mLastDownY);
                return true;
            case MotionEvent.ACTION_MOVE:
                mCurryY = (int) event.getY();
                System.err.println("ACTION_MOVE=" + mCurryY);
                mDelY = mCurryY - mLastDownY;
                // 只准上滑有效
                if (mDelY < 0) {
                    scrollTo(0, -mDelY);
                }
                System.err.println("-------------  " + mDelY);

                break;
            case MotionEvent.ACTION_UP:
                mCurryY = (int) event.getY();
                mDelY = mCurryY - mLastDownY;
                if (mDelY < 0) {

                    if (Math.abs(mDelY) > mScreenHeigh / 3) {

                        // 向上滑动超过半个屏幕高的时候 开启向上消失动画
                        startBounceAnim(this.getScrollY(), mScreenHeigh, 450);
                        mCloseFlag = true;

                    } else {
                        // 向上滑动未超过半个屏幕高的时候 开启向下弹动动画
                        startBounceAnim(this.getScrollY(), -this.getScrollY(), 450);

                    }
                }

                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {

        if (mOnTouchFlag) {
            if (mScroller.computeScrollOffset()) {
                scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
                // 不要忘记更新界面
                postInvalidate();
            } else {
                if (mCloseFlag) {
                    this.setVisibility(View.GONE);
                }
            }
        }else {
            if (mScroller2.computeScrollOffset()) {
                scrollTo(mScroller2.getCurrX(), mScroller2.getCurrY());
                // 不要忘记更新界面
                postInvalidate();
            } else {
                if (mCloseFlag) {
                    this.setVisibility(View.GONE);
                }
            }
        }
    }
    //3秒消失
    public class ClosePullDoorViewThread implements Runnable{
        @Override
        public void run() {
            try {
                Thread.sleep(5*1000);

                if (mOnTouchFlag){

                }else {
                    if (mCloseFlag) {

                    } else {
                        mCloseFlag = true;
                        startBounceAnim(0, mScreenHeigh, 500);
                    }
                }
            } catch (Exception e){

            }
        }

    }

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            mCloseFlag = false;
            mOnTouchFlag = true;
            reStartBounceAnim(mScreenHeigh, -mScreenHeigh, 1000);
        }
    };

    @Override
    protected void onDetachedFromWindow() {

        mContext.unregisterReceiver(mBroadcastReceiver);

        super.onDetachedFromWindow();
    }
}
