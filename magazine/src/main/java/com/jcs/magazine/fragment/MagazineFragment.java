package com.jcs.magazine.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jcs.magazine.R;
import com.jcs.magazine.activity.PrefaceActivity;
import com.jcs.magazine.adapter.YZUPageAdapter;
import com.jcs.magazine.base.BaseFragment;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.ContentsBean;
import com.jcs.magazine.network.YzuClient;
import com.jcs.magazine.util.UiUtil;
import com.jcs.magazine.yzu_viewPager.ScaleInTransformer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * author：Jics
 * 2016/6/21 14:16
 */
public class MagazineFragment extends BaseFragment {
	private static final String TAG="jcs_net";
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
			public void onClickPage(final ImageView view, String position) {
				/*YzuClient.getInstance()
						.getMagazineCover(1,3)//第一页，每页3条
						.subscribeOn(Schedulers.newThread())//网络请求开新线程
						.observeOn(AndroidSchedulers.mainThread())//网络响应在UI线程
						.subscribe(new Consumer<BaseListTemplet<MgzCoverBean>>() {
							@Override
							public void accept(BaseListTemplet<MgzCoverBean> mgzCoverBeanBaseMgz) throws Exception {
								Log.e(TAG, "accept: "+ mgzCoverBeanBaseMgz.isSucc() );

								Intent inten = new Intent(getActivity(), PrefaceActivity.class);
								ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(getActivity(),view,"cover");
								startActivity(inten,options.toBundle());
							}
						}, new Consumer<Throwable>() {
							@Override
							public void accept(Throwable throwable) throws Exception {
								UiUtil.toast( "回调失败:"+throwable.toString());
							}
						});*/




				YzuClient.getInstance().getContents(1)
						.subscribeOn(Schedulers.newThread())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new Consumer<BaseListTemplet<ContentsBean>>() {
							@Override
							public void accept(BaseListTemplet<ContentsBean> contentsBeanListBeanTemplet) throws Exception {
								Intent intent = new Intent(getActivity(), PrefaceActivity.class);
								Bundle bundle=new Bundle();
								bundle.putSerializable("contents",contentsBeanListBeanTemplet);
								intent.putExtras(bundle);
								ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(getActivity(),view,"cover");
								startActivity(intent,options.toBundle());
							}
						}, new Consumer<Throwable>() {
							@Override
							public void accept(Throwable throwable) throws Exception {
								UiUtil.toast("网络回调错误："+throwable.toString());
							}
						});
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
