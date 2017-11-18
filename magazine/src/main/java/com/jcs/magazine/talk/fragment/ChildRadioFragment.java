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

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcs.magazine.R;
import com.jcs.magazine.activity.ArticleDetialActivityRe;
import com.jcs.magazine.bean.ArticleBean;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.BaseMgz;
import com.jcs.magazine.bean.TalkContentsBean;
import com.jcs.magazine.config.BuildConfig;
import com.jcs.magazine.network.YzuClientDemo;
import com.jcs.magazine.talk.adapter.RadioRvAdapter;
import com.jcs.magazine.talk.interfaces.TabFragmentInterface;
import com.jcs.magazine.util.DialogHelper;
import com.jcs.magazine.util.UiUtil;
import com.jcs.magazine.util.glide.ImageAutoLoadScrollListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author：Jics
 * 2017/9/5 14:38
 */
public class ChildRadioFragment extends Fragment implements TabFragmentInterface,RadioRvAdapter.OnArtItemClickListener {
	private View rootView;
	private String tabName;
	private XRecyclerView recyclerView;
	//某章的文章列表
	private List<TalkContentsBean.ArticlesBean> list;
	private RadioRvAdapter artRvAdapter;

	private int index = 1;
	private int total = 0;
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

		recyclerView = (XRecyclerView) parent.findViewById(R.id.rv_content);
		intitData();

		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		recyclerView.addOnScrollListener(new ImageAutoLoadScrollListener(getContext()));
		//上拉下拉风格
		recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallZigZagDeflect);
		//设置箭头
		recyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

		artRvAdapter = new RadioRvAdapter(getContext(), list);
		artRvAdapter.setOnArtItemClickListener(this);
		recyclerView.setAdapter(artRvAdapter);
		recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
			@Override
			public void onRefresh() {
				recyclerView.setPullRefreshEnabled(false);
				YzuClientDemo.getInstance()
						.getRadioLists(1,10)
						.subscribeOn(Schedulers.newThread())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new Consumer<BaseListTemplet<TalkContentsBean.ArticlesBean>>() {
							@Override
							public void accept(BaseListTemplet<TalkContentsBean.ArticlesBean> articlesBeanBaseListTemplet) throws Exception {
								list.clear();
								list.addAll(articlesBeanBaseListTemplet.getResults().getBody());
								artRvAdapter.notifyDataSetChanged();
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
							.getRadioLists(++index, 10)
							.subscribeOn(Schedulers.newThread())
							.observeOn(AndroidSchedulers.mainThread())
							.subscribe(new Consumer<BaseListTemplet<TalkContentsBean.ArticlesBean>>() {
								@Override
								public void accept(BaseListTemplet<TalkContentsBean.ArticlesBean> articlesBeanBaseListTemplet) throws Exception {
									list.clear();
									list.addAll(articlesBeanBaseListTemplet.getResults().getBody());
									artRvAdapter.notifyDataSetChanged();
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


	}

	private void intitData() {
		recyclerView.setNoMore(false);
		YzuClientDemo.getInstance()
				.getRadioLists(1,10)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<BaseListTemplet<TalkContentsBean.ArticlesBean>>() {
					@Override
					public void accept(BaseListTemplet<TalkContentsBean.ArticlesBean> articlesBeanBaseListTemplet) throws Exception {
						index = 1;
						total = articlesBeanBaseListTemplet.getResults().getTotal();
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
		int articleID = list.get(position).getArticleId();
		YzuClientDemo.getInstance().getTalk(articleID)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<BaseMgz<ArticleBean>>() {
					@Override
					public void accept(BaseMgz<ArticleBean> articleBean) throws Exception {
						loading.dismiss();
						Intent intent = new Intent(getContext(), ArticleDetialActivityRe.class);
						intent.putExtra("content", articleBean.getResults().getContent());
						intent.putExtra("title", list.get(position).getTitle());
						intent.putExtra("author", list.get(position).getAuthor());
						intent.putExtra("type", BuildConfig.COMMENT_TYPE_TALK);
						intent.putExtra("articleId",list.get(position).getArticleId());
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
