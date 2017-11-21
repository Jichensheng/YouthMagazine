package com.jcs.magazine.adapter.ReMake;

import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcs.magazine.base.BaseFragment;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.util.DimentionUtils;
import com.jcs.magazine.util.UiUtil;
import com.jcs.magazine.widget.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 已发的帖
 * author：Jics
 * 2017/9/13 10:09
 */
public abstract class BaseRefreshFragment<T> extends BaseFragment{
	public static final int PAGE_COUNT = 10;
	protected List<T> beanList;
	protected BaseRefreshAdapter<T> adapter;
	protected XRecyclerView recyclerView;
	protected int index = 1;
	private int total = 0;
	protected boolean canPull = true;
	//刷新回调
	private Consumer<BaseListTemplet<T>> refresh = new Consumer<BaseListTemplet<T>>() {
		@Override
		public void accept(BaseListTemplet<T> listTemplet) throws Exception {
			if (listTemplet.isSucc()) {
				index = 1;
				total = listTemplet.getResults().getTotal();
				beanList.clear();
				beanList.addAll(listTemplet.getResults().getBody());
				adapter.notifyDataSetChanged();
			} else {
				UiUtil.toast("数据获取失败！");
			}
			recyclerView.refreshComplete();
			if (canPull) {
				recyclerView.setPullRefreshEnabled(true);
			}
		}
	};
	//加载更多回调
	private Consumer<BaseListTemplet<T>> loadMore = new Consumer<BaseListTemplet<T>>() {
		@Override
		public void accept(BaseListTemplet<T> listTemplet) throws Exception {
			if (listTemplet.isSucc()) {
				beanList.addAll(listTemplet.getResults().getBody());
				adapter.notifyDataSetChanged();
			}
			recyclerView.refreshComplete();

			if (canPull) {
				recyclerView.setPullRefreshEnabled(true);
			} else
				recyclerView.setLoadingMoreEnabled(true);

			recyclerView.loadMoreComplete();
		}
	};
	//错误回调
	private Consumer<Throwable> onError = new Consumer<Throwable>() {
		@Override
		public void accept(Throwable throwable) throws Exception {
			if (canPull) {
				recyclerView.setPullRefreshEnabled(true);
			} else
				recyclerView.setLoadingMoreEnabled(true);
		}
	};

	/**
	 * 初始化数据
	 */
	protected void init() {
		beanList = new ArrayList<>();
		initData();
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		//请求最初数据
		this.netHandler(getObservable(1, PAGE_COUNT), refresh, onError);
		recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext(), DimentionUtils.dip2px(getContext(), 1)));
		recyclerView.setAdapter(adapter);

		if (canPull) {
			recyclerView.setPullRefreshEnabled(true);
		} else {//只能上拉
			recyclerView.setPullRefreshEnabled(false);
			recyclerView.setLoadingMoreEnabled(true);
		}
		recyclerView.setLoadingListener(getListener());
	}

	public void setCanPull(boolean canPull) {
		this.canPull = canPull;
	}

	/**
	 * 网络请求
	 *
	 * @param observable 数据源
	 * @param onNext     数据接收处理
	 * @param onError    访问失败处理
	 */
	public void netHandler(Observable<BaseListTemplet<T>> observable, Consumer<BaseListTemplet<T>> onNext, Consumer<Throwable> onError) {
		observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(onNext, onError);
	}


	/**
	 * 刷新逻辑
	 *
	 * @return
	 */
	private XRecyclerView.LoadingListener getListener() {
		return new XRecyclerView.LoadingListener() {
			@Override
			public void onRefresh() {
				if (canPull) {
					recyclerView.setPullRefreshEnabled(false);
					netHandler(getObservable(1, PAGE_COUNT), refresh, onError);
				}
			}

			@Override
			public void onLoadMore() {
				if (total <= index * PAGE_COUNT) {
					recyclerView.setNoMore(true);
				} else {
					if (canPull) {
						recyclerView.setPullRefreshEnabled(false);
					} else
						recyclerView.setLoadingMoreEnabled(false);
					netHandler(getObservable(++index, PAGE_COUNT), loadMore, onError);
				}
			}
		};
	}

	protected abstract Observable<BaseListTemplet<T>> getObservable(int page, int count);

	/**
	 * 初始化recyclerview、adapter、obRefresh、obLoadMore
	 */
	public abstract void initData();
}
