package com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.activity.ArticleActivity;
import com.base.BaseFragment;
import com.githang.navigatordemo.R;
import com.yzu_viewPager.ScaleInTransformer;
import com.adapter.YZUPageAdapter;

/**
 * author：Jics
 * 2016/6/21 14:16
 */
public class MagazineFragment extends BaseFragment {
	private ViewPager mViewPager;
	private YZUPageAdapter mAdapter;
	private boolean flag;
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.main_fragment_magazine,container,false);

		mViewPager = (ViewPager) view.findViewById(R.id.id_viewpager);
		// 设置Page间间距
		mViewPager.setPageMargin(10);
		// 设置缓存的页面数量
		mViewPager.setOffscreenPageLimit(3);
		mViewPager.setPageTransformer(true, new ScaleInTransformer());// 动画进大出小

		//viewPager的父容器把事件拦截了，否则只能拖动最中间的那个view才能左右滑动
		view.findViewById(R.id.container).setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return mViewPager.dispatchTouchEvent(event);
			}
		});

		mAdapter = new YZUPageAdapter(view.getContext());
		mAdapter.setOnClickPageListener(new YZUPageAdapter.OnClickPageListener() {

			@Override
			public void onClickPage(String position) {
				Intent inten =new Intent(getActivity(),ArticleActivity.class);
				startActivity(inten);

			}

		});
		mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				switch (arg0) {
					// 正在拖动页面时执行此处
					case ViewPager.SCROLL_STATE_DRAGGING:
						flag= false;
						break;
					case ViewPager.SCROLL_STATE_SETTLING:
						flag = true;
						break;
					// 未拖动页面时执行此处
					case ViewPager.SCROLL_STATE_IDLE:
						if (mViewPager.getCurrentItem() == mViewPager.getAdapter()
								.getCount() - 1 && !flag) {
							Log.e("最后一页","对的");
						}
						flag = true;
						break;
				}
			}

		});
		mViewPager.setAdapter(mAdapter);
		return view;
	}
}
