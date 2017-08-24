package com.jcs.magazine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcs.magazine.R;
import com.jcs.magazine.adapter.MomentListAdapter;
import com.jcs.magazine.base.BaseFragment;

/**
 * Created by liudong on 17/4/12.
 */

public class MomentFragment extends BaseFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_fragment_moment, container, false);

		final XRecyclerView recyclerView = (XRecyclerView) view.findViewById(R.id.rv_main_talk);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		final MomentListAdapter adapter = new MomentListAdapter(getContext());

		recyclerView.setAdapter(adapter);

		recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 1));
		//上拉下拉风格
		recyclerView.setRefreshProgressStyle(ProgressStyle.LineScalePulseOut);
		recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallZigZagDeflect);
		//设置箭头
		recyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
		//回调监听部分
		recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
			@Override
			public void onRefresh() {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(1000);
							getActivity().runOnUiThread(new Runnable() {
								@Override
								public void run() {
									recyclerView.refreshComplete();
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
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(1000);getActivity().runOnUiThread(new Runnable() {
								@Override
								public void run() {
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
		return view;
	}
}