package com.jcs.magazine.fragment;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jcs.magazine.R;
import com.jcs.magazine.activity.PrefaceActivity;
import com.jcs.magazine.adapter.YZUPageAdapter;
import com.jcs.magazine.base.BaseFragment;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.ContentsBean;
import com.jcs.magazine.bean.MgzCoverBean;
import com.jcs.magazine.network.YzuClientDemo;
import com.jcs.magazine.util.BitmapUtil;
import com.jcs.magazine.util.DialogHelper;
import com.jcs.magazine.util.DimentionUtils;
import com.jcs.magazine.util.NetworkUtil;
import com.jcs.magazine.util.StatusBarUtil;
import com.jcs.magazine.yzu_viewPager.ScaleInTransformer;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * author：Jics
 * 2016/6/21 14:16
 */
public class MagazineFragment extends BaseFragment {
	private static final String TAG = "jcs_net";
	private View rootView;// 缓存Fragment view
	private YZUPageAdapter mAdapter;
	private boolean isFirstClick = true;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final BaseListTemplet<MgzCoverBean> mgzCoverBeanBaseMgz = (BaseListTemplet<MgzCoverBean>) getActivity().getIntent().getSerializableExtra("covers");
		final List<MgzCoverBean> coverBeens = mgzCoverBeanBaseMgz.getResults().getBody();
		mAdapter = new YZUPageAdapter(getContext(), coverBeens);
		mAdapter.setOnClickPageListener(new YZUPageAdapter.OnClickPageListener() {

			@Override
			public void onClickPage(final View bookView, final ImageView view, final int position) {
				if (isFirstClick) {//防止多次点击
					isFirstClick = false;
					final AlertDialog loading = new DialogHelper(getContext()).show(R.layout.loading);
					new Thread(new Runnable() {
						@Override
						public void run() {
							BitmapUtil.getViewBitmap(bookView);
						}
					}).start();
					//目录id
					int contentsId = coverBeens.get(position).getVol();
					YzuClientDemo.getInstance().getContents(contentsId)
							.subscribeOn(Schedulers.newThread())
							.observeOn(AndroidSchedulers.mainThread())
							.subscribe(new Consumer<BaseListTemplet<ContentsBean>>() {
								@Override
								public void accept(BaseListTemplet<ContentsBean> contentsBeanListBeanTemplet) throws Exception {
									loading.dismiss();
									isFirstClick = true;
									Intent intent = new Intent(getActivity(), PrefaceActivity.class);
									intent.putExtra("img", YzuClientDemo.RESOURCE_COVER_HOST +coverBeens.get(position).getImage());
									Bundle bundle = new Bundle();
									bundle.putSerializable("contents", contentsBeanListBeanTemplet);
									intent.putExtras(bundle);
									//大于5.0元素共享
									if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
										ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view, "cover");
										startActivity(intent, options.toBundle());
									} else
										startActivity(intent);
								}
							}, new Consumer<Throwable>() {
								@Override
								public void accept(Throwable throwable) throws Exception {
									isFirstClick = true;
									loading.dismiss();
									if (NetworkUtil.isConnectingToInternet(getContext())) {//服务器原因
										new DialogHelper(getContext()).show(new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog, int which) {
												dialog.dismiss();
											}
										}, "连接失败", "服务器维护中，请稍后重试");
									} else {//网络原因
										new DialogHelper(getContext()).show(new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog, int which) {
												dialog.dismiss();
											}
										}, "连接失败", "请检查网络是否连接");

									}
									Log.e(TAG, "accept: " + throwable.toString());
								}
							});
				}

			}

		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.main_fragment_magazine, container, false);
			WeakReference<YZUPageAdapter> weakReference = new WeakReference<YZUPageAdapter>(mAdapter);

			final ViewPager mViewPager = (ViewPager) rootView.findViewById(R.id.id_viewpager);



			LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mViewPager.getLayoutParams();
			lp.setMargins(DimentionUtils.dip2px(getContext(), 40),
					StatusBarUtil.getStatusBarHeight(getContext()),
					DimentionUtils.dip2px(getContext(), 40),
					DimentionUtils.dip2px(getContext(), 5));
			mViewPager.setLayoutParams(lp);
			// 设置Page间间距
			mViewPager.setPageMargin(10);
			// 设置缓存的页面数量
			mViewPager.setOffscreenPageLimit(3);
			mViewPager.setPageTransformer(true, new ScaleInTransformer());// 动画进大出小
			//viewPager的父容器把事件拦截了，否则只能拖动最中间的那个view才能左右滑动
		/*	rootView.findViewById(R.id.container).setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					return mViewPager.dispatchTouchEvent(event);
				}
			});*/
			mViewPager.setAdapter(weakReference.get());
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null)
		{
			parent.removeView(rootView);
		}
		return rootView;

	}

}
