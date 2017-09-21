package com.jcs.magazine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jcs.magazine.R;
import com.jcs.magazine.adapter.LaboratoryAdapter;
import com.jcs.magazine.base.BaseFragment;
import com.jcs.magazine.bean.LaboratoryBean;
import com.jcs.magazine.util.DimentionUtils;
import com.jcs.magazine.util.StatusBarUtil;
import com.jcs.magazine.util.UiUtil;
import com.jcs.magazine.util.glide.GlideBlurTransform;
import com.jcs.magazine.yzu_viewPager.ScaleInTransformer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * author：Jics
 * 2016/6/21 14:16
 */
public class LaboratoryFragment extends BaseFragment {
	private static final String TAG = "jcs_laboratory";
	private View rootView;// 缓存Fragment view
	private LaboratoryAdapter mAdapter;
	private List<LaboratoryBean> list;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		list = initData();
		mAdapter = new LaboratoryAdapter(getContext(), list);
		mAdapter.setOnClickPageListener(new LaboratoryAdapter.OnClickPageListener() {

			@Override
			public void onClickPage(View view, int position) {
				UiUtil.toast("" + position);
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.main_fragment_laboratory_re, container, false);
			WeakReference<LaboratoryAdapter> weakReference = new WeakReference<LaboratoryAdapter>(mAdapter);

			final ViewPager mViewPager = (ViewPager) rootView.findViewById(R.id.id_viewpager);

			LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mViewPager.getLayoutParams();
			lp.setMargins(DimentionUtils.dip2px(getContext(), 40),
					StatusBarUtil.getStatusBarHeight(getContext()),
					DimentionUtils.dip2px(getContext(), 40),
					DimentionUtils.dip2px(getContext(), 5));
			mViewPager.setLayoutParams(lp);
			// 设置Page间间距
			mViewPager.setPageMargin(DimentionUtils.dip2px(getContext(),25));
			// 设置缓存的页面数量
			mViewPager.setOffscreenPageLimit(3);
			mViewPager.setPageTransformer(true, new ScaleInTransformer());// 动画进大出小
			//viewPager的父容器把事件拦截了，否则只能拖动最中间的那个view才能左右滑动
			/*rootView.findViewById(R.id.container).setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					return mViewPager.dispatchTouchEvent(event);
				}
			});*/
            Glide.with(getContext()).load(list.get(0).getRes())
                    .transform(new GlideBlurTransform(getContext()))
                    .into((ImageView) rootView.findViewById(R.id.iv_bg));
			mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    Glide.with(getContext()).load(list.get(position).getRes())
                            .transform(new GlideBlurTransform(getContext()))
                            .into((ImageView) rootView.findViewById(R.id.iv_bg));
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
			mViewPager.setAdapter(weakReference.get());
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		return rootView;

	}

	private List<LaboratoryBean> initData() {
		List<LaboratoryBean> list = new ArrayList<>();
		LaboratoryBean shudong = new LaboratoryBean();
		shudong.setTitle("树洞");
		shudong.setRes(R.drawable.shudong);
		shudong.setDescription("告诉我你的小秘密");
		shudong.setIntent(null);
		list.add(shudong);

		LaboratoryBean yangli = new LaboratoryBean();
		yangli.setTitle("扬历");
		yangli.setRes(R.drawable.huangli);
		yangli.setDescription("日常小迷信");
		yangli.setIntent(null);
		list.add(yangli);


		return list;
	}

}
