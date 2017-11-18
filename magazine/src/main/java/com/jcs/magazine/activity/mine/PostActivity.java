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
import com.jcs.magazine.bean.MomentBean;
import com.jcs.magazine.bean.UserBean;
import com.jcs.magazine.global.LoginUserHelper;
import com.jcs.magazine.network.YzuClient;

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
public class PostActivity extends BaseActivity {
	private List<MomentBean> momentBeanList;;
	private MPostListAdapter adapter;
	@Override
	protected void onCreate(@Nullable Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_post);
		initData();
		initView();

	}

	private void initData() {
		momentBeanList=new ArrayList<>();
		adapter = new MPostListAdapter(this, momentBeanList,true);
		if (LoginUserHelper.getInstance().isLogined()) {
			UserBean user=LoginUserHelper.getInstance().getUser();
			YzuClient.getInstance().getUserPostLists(user.getUid(),1,10)
					.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
					.subscribe(new Consumer<BaseListTemplet<MomentBean>>() {
						@Override
						public void accept(BaseListTemplet<MomentBean> momentBeanBaseListTemplet) throws Exception {
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
		Toolbar tb_toolbar= (Toolbar) findViewById(R.id.tb_toolbar);
		setSupportActionBar(tb_toolbar);

		final XRecyclerView xRecyclerView= (XRecyclerView) findViewById(R.id.rv_my_post);
		xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		xRecyclerView.setAdapter(adapter);
		xRecyclerView.setLoadingListener(getListener(xRecyclerView));

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case android.R.id.home:
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 刷新逻辑
	 * @param xRecyclerView
	 * @return
	 */
	@NonNull
	private XRecyclerView.LoadingListener getListener(final XRecyclerView xRecyclerView) {
		return new XRecyclerView.LoadingListener() {
			@Override
			public void onRefresh() {
				xRecyclerView.setPullRefreshEnabled(false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(1000);
							PostActivity.this.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									xRecyclerView.refreshComplete();
									xRecyclerView.setPullRefreshEnabled(true);
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
				xRecyclerView.setPullRefreshEnabled(false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(1000);PostActivity.this.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									xRecyclerView.setPullRefreshEnabled(true);
									xRecyclerView.loadMoreComplete();
									xRecyclerView.setNoMore(true);
								}
							});
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		};
	}
}
