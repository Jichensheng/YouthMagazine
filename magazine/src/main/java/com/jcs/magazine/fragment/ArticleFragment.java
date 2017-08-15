package com.jcs.magazine.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcs.magazine.R;
import com.jcs.magazine.activity.ArticleDetialActivity;
import com.jcs.magazine.adapter.ArtRvAdapter;
import com.jcs.magazine.bean.ArticleBean;
import com.jcs.magazine.bean.BaseMgz;
import com.jcs.magazine.bean.ContentsBean;
import com.jcs.magazine.network.YzuClient;
import com.jcs.magazine.util.DialogHelper;
import com.jcs.magazine.util.UiUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 目录下每章的文章列表页面
 * author：Jics
 * 2017/4/5 09:52
 */
@SuppressLint("ValidFragment")
public class ArticleFragment extends Fragment implements ArtRvAdapter.OnArtItemClickListener {
	private RecyclerView recyclerView;
	//某章的文章列表
	private List<ContentsBean.ArticlesBean> list;
	private ArtRvAdapter artRvAdapter;
	//某章节的所有文章数据
	private ContentsBean content;

	public ArticleFragment() {

	}

	public ArticleFragment(ContentsBean content) {
		this.content = content;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_article_list, container, false);
		initView(view);
		return view;
	}

	private void initView(View parent) {
		list = new ArrayList<>();
		//遍历章节里的每篇文章
		for (ContentsBean.ArticlesBean articlesBean : content.getArticles()) {
			list.add(articlesBean);
		}
		recyclerView = (RecyclerView) parent.findViewById(R.id.rv_content);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		artRvAdapter = new ArtRvAdapter(getContext(), list);
		artRvAdapter.setOnArtItemClickListener(this);
		recyclerView.setAdapter(artRvAdapter);


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

	public String getName() {
		return content.getName();
	}
}
