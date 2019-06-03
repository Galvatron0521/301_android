package com.rilintech.fragment_301_huxike_android.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.nineoldandroids.view.ViewHelper;

import java.util.HashMap;
import java.util.Map;

/*
 * 自定义viewpager实现动画切换效果：
 * 
 * 1、需要拿到当前切换的两个view
 * 
 * 2、一个动画的梯度值
 */
public class MyViewPagerTransformerAnim extends ViewPager {

	private View mLeft;
	private View mRight;

	private float mTrans;
	private float mScale;

	private static final float MIN_SCALE = 0.6f;
	private Map<Integer, View> mChildren = new HashMap<Integer, View>();

	private Context context;

	/*
	 * 要有两个构造方法
	 */
	public MyViewPagerTransformerAnim(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public MyViewPagerTransformerAnim(Context context) {
		super(context);
		this.context = context;
	}

	/*
	 * 设置put的方法
	 */
	public void setViewForPosition(View view, int position) {
		mChildren.put(position, view);
	}

	/*
	 * remove的方法
	 */
	public void removeViewFromPosition(Integer position) {
		mChildren.remove(position);
	}

	/**
	 * 重写的方法
	 */
	@Override
	protected void onPageScrolled(int position, float offset, int offsetPixels) {

		// Log.e("TAG", "position =" + position + ",offset = " + offset);
		mLeft = mChildren.get(position);
		mRight = mChildren.get(position + 1);

		animStack(mLeft, mRight, offset, offsetPixels);// 创建动画效果

		super.onPageScrolled(position, offset, offsetPixels);
	}

	private void animStack(View left, View right, float offset, int offsetPixels) {
		if (right != null) {

			// 从0-1页，offset:0`1
			mScale = (1 - MIN_SCALE) * offset + MIN_SCALE;

			mTrans = -getWidth() - getPageMargin() + offsetPixels;

			ViewHelper.setScaleX(right, mScale);
			ViewHelper.setScaleY(right, mScale);

			ViewHelper.setTranslationX(right, mTrans);
		}
		if (left != null) {
			left.bringToFront();
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int mWidth = wm.getDefaultDisplay().getWidth();
		int mHeight = wm.getDefaultDisplay().getHeight();

		int height = 0;
		//下面遍历所有child的高度
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			child.measure(widthMeasureSpec,
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			int h = child.getMeasuredHeight();
			if (h > height) //采用最大的view的高度。
				height = h;
		}

		heightMeasureSpec = MeasureSpec.makeMeasureSpec(mHeight/3*1,
				MeasureSpec.EXACTLY);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
