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
import com.jcs.magazine.network.YzuClient;
import com.jcs.magazine.network.YzuClientDemo;
import com.jcs.magazine.util.glide.ImageAutoLoadScrollListener;
import com.melnykov.fab.FloatingActionButton;

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

	private View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.main_fragment_moment_re, container, false);
			momentBeanList=new ArrayList<>();
			bannerItemList=new ArrayList<>();
			final XRecyclerView recyclerView = (XRecyclerView) rootView.findViewById(R.id.rv_main_talk);
			recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
			getBannerData();
			getListData();
			adapter = new MomentListAdapter(getContext(),momentBeanList,bannerItemList);

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
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(1000);
								getActivity().runOnUiThread(new Runnable() {
									@Override
									public void run() {
										recyclerView.refreshComplete();
										recyclerView.setPullRefreshEnabled(true);
									}
								});
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}).start();
				}

				@Override
				public void onLoadMore() {
					recyclerView.setPullRefreshEnabled(false);
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(1000);getActivity().runOnUiThread(new Runnable() {
									@Override
									public void run() {
										recyclerView.setPullRefreshEnabled(true);
										recyclerView.loadMoreComplete();
										recyclerView.setNoMore(true);
									}
								});
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}).start();
				}
			});
		}else{
			ViewGroup group= (ViewGroup) rootView.getParent();
			if (group != null) {
				group.removeView(rootView);
			}
		}

		return rootView;
	}

	private void getListData(){
		YzuClientDemo.getInstance()
				.getMomentLists(1,10)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<BaseListTemplet<MomentBeanRefactor>>() {
					@Override
					public void accept(BaseListTemplet<MomentBeanRefactor> momentBeanBaseListTemplet) throws Exception {
						for (MomentBeanRefactor momentBean : momentBeanBaseListTemplet.getResults().getBody()) {
							momentBeanList.add(momentBean);
							adapter.notifyDataSetChanged();
						}

					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {

					}
				});
	}
	private void getBannerData(){
		YzuClient.getInstance()
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
		switch (v.getId()){
			case R.id.fab:
				Intent intent=new Intent(getContext(), MakePostActivity.class);
				PermissionHelper.getHelper().startActivity(getContext(), intent);
				break;
		}
	}
}