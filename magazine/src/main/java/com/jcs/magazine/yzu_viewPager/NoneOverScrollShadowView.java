package com.jcs.magazine.yzu_viewPager;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * author：Jics
 * 2016/6/22 15:47
 */
public class NoneOverScrollShadowView extends ViewPager {
	private float preX;

	public NoneOverScrollShadowView(Context context) {
		super(context);
	}
	public NoneOverScrollShadowView(Context context, AttributeSet attrs){
		super(context,attrs);
		if(Build.VERSION.SDK_INT>=9){
			this.setOverScrollMode(View.OVER_SCROLL_NEVER);
		}

	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		getParent().requestDisallowInterceptTouchEvent(true);
		return super.dispatchTouchEvent(ev);
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		boolean res = super.onInterceptTouchEvent(ev);
		if(ev.getAction() == MotionEvent.ACTION_DOWN) {
			preX = ev.getX();
		} else {
			if( Math.abs(ev.getX() - preX)> 4 ) {
				return true;
			} else {
				preX = ev.getX();
			}
		}
		return res;
	}
}
