package com.jcs.magazine.talk.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcs.magazine.R;
import com.jcs.magazine.activity.ArticleDetialActivity;
import com.jcs.magazine.bean.ArticleBean;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.BaseMgz;
import com.jcs.magazine.bean.ContentsBean;
import com.jcs.magazine.network.YzuClient;
import com.jcs.magazine.talk.adapter.RadioRvAdapter;
import com.jcs.magazine.talk.interfaces.LoveInterface;
import com.jcs.magazine.util.DialogHelper;
import com.jcs.magazine.util.UiUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author：Jics
 * 2017/9/5 14:38
 */
public class ChildRadioFragment extends Fragment implements LoveInterface,RadioRvAdapter.OnArtItemClickListener {
	private View rootView;
	private String tabName;
	private XRecyclerView recyclerView;
	//某章的文章列表
	private List<ContentsBean.ArticlesBean> list;
	private RadioRvAdapter artRvAdapter;

	@Override
	public void setTabName(String tabName) {
		this.tabName=tabName;
	}

	@Override
	public String getTabName() {
		return tabName;
	}
	@Override
	public Fragment getFragment() {
		return this;
	}


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragment_radio_list, container, false);
			initView(rootView);
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null)
		{
			parent.removeView(rootView);
		}
		return rootView;
	}

	private void initView(View parent) {
		list = new ArrayList<>();

		intitData();

		recyclerView = (XRecyclerView) parent.findViewById(R.id.rv_content);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

	/*	//上拉下拉风格
		recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
		recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallScaleMultiple);
		//设置箭头
		recyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);*/

		artRvAdapter = new RadioRvAdapter(getContext(), list);
		artRvAdapter.setOnArtItemClickListener(this);
		recyclerView.setAdapter(artRvAdapter);
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
				.getRadioLists(1,10)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<BaseListTemplet<ContentsBean.ArticlesBean>>() {
					@Override
					public void accept(BaseListTemplet<ContentsBean.ArticlesBean> articlesBeanBaseListTemplet) throws Exception {
						list.clear();
						list.addAll(articlesBeanBaseListTemplet.getResults().getBody());
						artRvAdapter.notifyDataSetChanged();
					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {
						Log.e("radio", "accept: "+throwable.toString());
					}
				});
	}

	@Override
	public void onItemClick(View view, final int position) {
		final AlertDialog loading = new DialogHelper(getContext()).show(R.layout.loading);
		//TODO 文章ID
		int articleID = list.get(position).getArticleId();
		YzuClient.getInstance().getArticle("5311")
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<BaseMgz<ArticleBean>>() {
					@Override
					public void accept(BaseMgz<ArticleBean> articleBean) throws Exception {
						loading.dismiss();
						Intent intent = new Intent(getContext(), ArticleDetialActivity.class);
						intent.putExtra("content", articleBean.getResults().getContent());
						intent.putExtra("title", list.get(position).getTitle());
						intent.putExtra("author", list.get(position).getAuthor());
						startActivity(intent);
					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {
						loading.dismiss();
						UiUtil.toast("回调失败:" + throwable.toString());
					}
				});


	}

	@Override
	public void onHeartClick() {

	}

	@Override
	public void onShareClick() {

	}

}
