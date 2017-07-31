package com.jcs.magazine.yzu_viewPager;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
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

}
