package com.jcs.magazine.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcs.magazine.R;
import com.jcs.magazine.activity.MakePostActivity;
import com.jcs.magazine.adapter.MomentListAdapter;
import com.jcs.magazine.base.BaseFragment;
import com.jcs.magazine.bean.BannerItem;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.MomentBeanRefactor;
import com.jcs.magazine.global.PermissionHelper;
import com.jcs.magazine.network.YzuClientDemo;
import com.jcs.magazine.util.DimentionUtils;
import com.jcs.magazine.util.MessageEvent;
import com.jcs.magazine.util.glide.ImageAutoLoadScrollListener;
import com.jcs.magazine.widget.SimpleDividerItemDecoration;
import com.melnykov.fab.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liudong on 17/4/12.
 */

public class MomentFragment extends BaseFragment implements View.OnClickListener {
	private MomentListAdapter adapter;
	private List<MomentBeanRefactor> momentBeanList;
	private List<BannerItem> bannerItemList;
	private int index = 1;
	private int total = 0;
	private View rootView;
	private XRecyclerView recyclerView;
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (rootView == null) {
			EventBus.getDefault().register(this);
			rootView = inflater.inflate(R.layout.main_fragment_moment_re, container, false);
			momentBeanList = new ArrayList<>();
			bannerItemList = new ArrayList<>();
			recyclerView = (XRecyclerView) rootView.findViewById(R.id.rv_main_talk);
			recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
			recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext(), DimentionUtils.dip2px(getContext(), 1)));
			getBannerData();
			getListData();
			adapter = new MomentListAdapter(getContext(), momentBeanList, bannerItemList);

			recyclerView.setAdapter(adapter);

//			recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext(),1));
			//上拉下拉风格
			recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallZigZagDeflect);
			//设置箭头
			recyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

			recyclerView.addOnScrollListener(new ImageAutoLoadScrollListener(getContext()));

			//附着在ListView，跟随ListView滚动滑入滑出
			FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
			fab.setOnClickListener(this);
			fab.attachToRecyclerView(recyclerView);

			//回调监听部分
			recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
				@Override
				public void onRefresh() {
					recyclerView.setPullRefreshEnabled(false);
					YzuClientDemo.getInstance()
							.getMomentLists(1, 10)
							.subscribeOn(Schedulers.newThread())
							.observeOn(AndroidSchedulers.mainThread())
							.subscribe(new Consumer<BaseListTemplet<MomentBeanRefactor>>() {
								@Override
								public void accept(BaseListTemplet<MomentBeanRefactor> momentBeanBaseListTemplet) throws Exception {
									momentBeanList.clear();
									index = 1;
									total = momentBeanBaseListTemplet.getResults().getTotal();
									for (MomentBeanRefactor momentBean : momentBeanBaseListTemplet.getResults().getBody()) {
										momentBeanList.add(momentBean);
									}
									adapter.notifyDataSetChanged();
									recyclerView.refreshComplete();
									recyclerView.setPullRefreshEnabled(true);
								}
							}, new Consumer<Throwable>() {
								@Override
								public void accept(Throwable throwable) throws Exception {
									recyclerView.setPullRefreshEnabled(true);
								}
							});

				}

				@Override
				public void onLoadMore() {
					if (total <= index * 10) {
						recyclerView.setNoMore(true);
					} else {
						recyclerView.setPullRefreshEnabled(false);
						YzuClientDemo.getInstance()
								.getMomentLists(++index, 10)
								.subscribeOn(Schedulers.newThread())
								.observeOn(AndroidSchedulers.mainThread())
								.subscribe(new Consumer<BaseListTemplet<MomentBeanRefactor>>() {
									@Override
									public void accept(BaseListTemplet<MomentBeanRefactor> momentBeanBaseListTemplet) throws Exception {
										for (MomentBeanRefactor momentBean : momentBeanBaseListTemplet.getResults().getBody()) {
											momentBeanList.add(momentBean);
										}
										adapter.notifyDataSetChanged();

										recyclerView.loadMoreComplete();

										recyclerView.setPullRefreshEnabled(true);
									}
								}, new Consumer<Throwable>() {
									@Override
									public void accept(Throwable throwable) throws Exception {

										recyclerView.setPullRefreshEnabled(true);
									}
								});
					}

				}
			});
		} else {
			ViewGroup group = (ViewGroup) rootView.getParent();
			if (group != null) {
				group.removeView(rootView);
			}
		}

		return rootView;
	}

	private void getListData() {
		YzuClientDemo.getInstance()
				.getMomentLists(1, 10)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<BaseListTemplet<MomentBeanRefactor>>() {
					@Override
					public void accept(BaseListTemplet<MomentBeanRefactor> momentBeanBaseListTemplet) throws Exception {
						momentBeanList.clear();
						index = 1;
						total = momentBeanBaseListTemplet.getResults().getTotal();
						for (MomentBeanRefactor momentBean : momentBeanBaseListTemplet.getResults().getBody()) {
							momentBeanList.add(momentBean);
						}
						adapter.notifyDataSetChanged();

					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {

					}
				});
	}

	private void getBannerData() {
		YzuClientDemo.getInstance()
				.getMomentBannder()
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<BaseListTemplet<BannerItem>>() {
					@Override
					public void accept(BaseListTemplet<BannerItem> bannerItemBaseListTemplet) throws Exception {
						for (BannerItem bannerItem : bannerItemBaseListTemplet.getResults().getBody()) {
							bannerItemList.add(bannerItem);
							adapter.notifyDataSetChanged();
						}

					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {

					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.fab:
				Intent intent = new Intent(getContext(), MakePostActivity.class);
				PermissionHelper.getHelper().startActivity(getContext(), intent);
				break;
		}
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void refreshWhenMsg(MessageEvent messageEvent) {
		if (messageEvent.getMessage().equals("post_succ")) {
			getListData();
			recyclerView.setNoMore(false);
		}

	}

	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}
}