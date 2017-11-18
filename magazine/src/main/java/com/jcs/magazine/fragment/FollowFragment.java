package com.jcs.magazine.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcs.magazine.R;
import com.jcs.magazine.activity.OtherUserActivity;
import com.jcs.magazine.adapter.FollowListAdatper;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.UserBean;
import com.jcs.magazine.network.YzuClient;
import com.jcs.magazine.util.glide.ImageAutoLoadScrollListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author：Jics
 * 2017/9/21 14:06
 */
public class FollowFragment extends Fragment implements FollowInterface, FollowListAdatper.OnItemClickListen {
	public static final int TYPE_FOLLOW = 1;
	public static final int TYPE_FOLLOWER = 2;

	private View rootView;// 缓存Fragment view
	private String tabName;
	private XRecyclerView recyclerView;
	//用户列表
	private List<UserBean> list;
	private FollowListAdatper adatper;

	private int followType;
	private int uid;



	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragment_follow, container, false);
			initView(rootView);
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		return rootView;
	}

	private void initView(View parent) {
		list = new ArrayList<>();

		intitData();

		recyclerView = (XRecyclerView) parent.findViewById(R.id.xrv_follow);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		//上拉下拉风格
//		recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
//		recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallScaleMultiple);
		//设置箭头
//		recyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);

		adatper = new FollowListAdatper(getContext(), list);
		adatper.setOnItemClickListen(this);
		recyclerView.setAdapter(adatper);

		recyclerView.addOnScrollListener(new ImageAutoLoadScrollListener(getContext()));
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
							Thread.sleep(1000);
							getActivity().runOnUiThread(new Runnable() {
								@Override
								public void run() {
									recyclerView.loadMoreComplete();
									recyclerView.setPullRefreshEnabled(true);
								}
							});
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		});


	}

	private void intitData() {
		YzuClient.getInstance()
				.getFollowLists(getUid(),getFollowType(),1, 12)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<BaseListTemplet<UserBean>>() {
					@Override
					public void accept(BaseListTemplet<UserBean> userBeanBaseListTemplet) throws Exception {
						list.clear();
						list.addAll(userBeanBaseListTemplet.getResults().getBody());
						adatper.notifyDataSetChanged();
					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {

					}
				});
	}


	@Override
	public void onItemClick(int position) {
		getContext().startActivity(new Intent(getContext(), OtherUserActivity.class));
	}

	@Override
	public void setFollowType(int type) {
		followType=type;
	}

	@Override
	public int getFollowType() {
		return followType!=0?followType:TYPE_FOLLOW;
	}

	@Override
	public void setUid(int uid) {
		this.uid=uid;
	}

	@Override
	public int getUid() {
		return uid;
	}

	@Override
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	@Override
	public String getTabName() {
		return tabName;
	}

	@Override
	public FollowFragment getFragment() {
		return this;
	}
}
