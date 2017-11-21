package com.jcs.magazine.activity.mine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcs.magazine.R;
import com.jcs.magazine.adapter.MPostListAdapter;
import com.jcs.magazine.base.BaseActivity;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.MomentBeanRefactor;
import com.jcs.magazine.bean.UserBean;
import com.jcs.magazine.global.LoginUserHelper;
import com.jcs.magazine.network.YzuClientDemo;
import com.jcs.magazine.util.DimentionUtils;
import com.jcs.magazine.widget.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 已发的帖
 * author：Jics
 * 2017/9/13 10:09
 */
@Deprecated
public class PostActivity extends BaseActivity {
	private List<MomentBeanRefactor> momentBeanList;
	private MPostListAdapter adapter;
	XRecyclerView recyclerView;
	private int index = 1;
	private int total = 0;

	@Override
	protected void onCreate(@Nullable Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_post);
		initData();
		initView();

	}

	private void initData() {
		momentBeanList = new ArrayList<>();
		adapter = new MPostListAdapter(this, momentBeanList, true);
		if (LoginUserHelper.getInstance().isLogined()) {
			UserBean user = LoginUserHelper.getInstance().getUser();
			YzuClientDemo.getInstance().getUserPostLists(user.getUid(), 1, 10)
					.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
					.subscribe(new Consumer<BaseListTemplet<MomentBeanRefactor>>() {
						@Override
						public void accept(BaseListTemplet<MomentBeanRefactor> momentBeanBaseListTemplet) throws Exception {
							index = 1;
							total = momentBeanBaseListTemplet.getResults().getTotal();
							momentBeanList.clear();
							momentBeanList.addAll(momentBeanBaseListTemplet.getResults().getBody());
							adapter.notifyDataSetChanged();
						}
					}, new Consumer<Throwable>() {
						@Override
						public void accept(Throwable throwable) throws Exception {

						}
					});
		}
	}
	private void initView() {
		Toolbar tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
		setSupportActionBar(tb_toolbar);

		recyclerView = (XRecyclerView) findViewById(R.id.rv_my_post);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(adapter);
		recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this, DimentionUtils.dip2px(this, 1)));
		recyclerView.setLoadingListener(getListener(recyclerView));

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 刷新逻辑
	 *
	 * @param xRecyclerView
	 * @return
	 */
	@NonNull
	private XRecyclerView.LoadingListener getListener(final XRecyclerView xRecyclerView) {
		return new XRecyclerView.LoadingListener() {
			@Override
			public void onRefresh() {
				xRecyclerView.setPullRefreshEnabled(false);
				if (LoginUserHelper.getInstance().isLogined()) {
					UserBean user = LoginUserHelper.getInstance().getUser();
					YzuClientDemo.getInstance().getUserPostLists(user.getUid(),  1, 10)
							.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
							.subscribe(new Consumer<BaseListTemplet<MomentBeanRefactor>>() {
								@Override
								public void accept(BaseListTemplet<MomentBeanRefactor> momentBeanBaseListTemplet) throws Exception {
									index = 1;
									total = momentBeanBaseListTemplet.getResults().getTotal();
									momentBeanList.clear();
									momentBeanList.addAll(momentBeanBaseListTemplet.getResults().getBody());
									adapter.notifyDataSetChanged();
									xRecyclerView.refreshComplete();
									xRecyclerView.setPullRefreshEnabled(true);
								}
							}, new Consumer<Throwable>() {
								@Override
								public void accept(Throwable throwable) throws Exception {
									xRecyclerView.setPullRefreshEnabled(true);

								}
							});
				}
			}

			@Override
			public void onLoadMore() {
				if (total <= index * 10) {
					recyclerView.setNoMore(true);
				} else {
					recyclerView.setPullRefreshEnabled(false);
					if (LoginUserHelper.getInstance().isLogined()) {
						UserBean user = LoginUserHelper.getInstance().getUser();
						YzuClientDemo.getInstance().getUserPostLists(user.getUid(), ++index, 10)
								.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
								.subscribe(new Consumer<BaseListTemplet<MomentBeanRefactor>>() {
									@Override
									public void accept(BaseListTemplet<MomentBeanRefactor> momentBeanBaseListTemplet) throws Exception {
										momentBeanList.addAll(momentBeanBaseListTemplet.getResults().getBody());
										adapter.notifyDataSetChanged();
										xRecyclerView.refreshComplete();
										xRecyclerView.setPullRefreshEnabled(true);
										xRecyclerView.loadMoreComplete();
									}
								}, new Consumer<Throwable>() {
									@Override
									public void accept(Throwable throwable) throws Exception {
										xRecyclerView.setPullRefreshEnabled(true);

									}
								});
					}
				}
			}
		};
	}
}
