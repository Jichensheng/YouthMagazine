package com.jcs.magazine.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcs.magazine.R;
import com.jcs.magazine.adapter.ArticalDetialAdapter;
import com.jcs.magazine.adapter.MomentDetailAdapter;
import com.jcs.magazine.base.BaseActivity;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.BaseMgz;
import com.jcs.magazine.bean.CommentBean;
import com.jcs.magazine.bean.CommentPostBean;
import com.jcs.magazine.bean.UserBean;
import com.jcs.magazine.global.LoginUserHelper;
import com.jcs.magazine.network.YzuClientDemo;
import com.jcs.magazine.util.DimentionUtils;
import com.jcs.magazine.util.UiUtil;
import com.jcs.magazine.widget.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 带评论的文章详情页
 * author：Jics
 * 2017/9/25 14:59
 */
public class ArticleDetialActivityRe extends BaseActivity implements TextView.OnEditorActionListener,
		View.OnClickListener, MomentDetailAdapter.OnLongPressItemListener, ArticalDetialAdapter.OnWebViewLoadedListener {
	private ArticalDetialAdapter adapter;
	private List<CommentBean> commentList;
	private int type;
	private int articleId;
	private int index = 1;
	private int total = 0;
	private EditText et_make_comment;
	private CommentBean atCommentBean = null;
	private String atNick = "";
	private XRecyclerView recyclerView;

	@Override
	protected void onCreate(@Nullable Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_article_detial_re);
		Toolbar toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
		setSupportActionBar(toolbar);
		commentList = new ArrayList<>();
		et_make_comment = (EditText) findViewById(R.id.et_make_comment);
		et_make_comment.setOnEditorActionListener(this);
		String content = getIntent().getStringExtra("content");
		String title = getIntent().getStringExtra("title");
		String autore = getIntent().getStringExtra("author");
		type = getIntent().getIntExtra("type", 0);
		articleId = getIntent().getIntExtra("articleId", 0);

		recyclerView = (XRecyclerView) findViewById(R.id.xrv_artical);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this, DimentionUtils.dip2px(this, 1)));
		recyclerView.setPullRefreshEnabled(false);
		recyclerView.setLoadingMoreEnabled(true);
		recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
			@Override
			public void onRefresh() {
			}

			@Override
			public void onLoadMore() {
				if (total <= index * 10) {
					recyclerView.setNoMore(true);
				} else {
					recyclerView.setLoadingMoreEnabled(false);
					YzuClientDemo.getInstance().getCommentLists(type, articleId, ++index, 10)
							.subscribeOn(Schedulers.newThread())
							.observeOn(AndroidSchedulers.mainThread())
							.subscribe(new Consumer<BaseListTemplet<CommentBean>>() {
								@Override
								public void accept(BaseListTemplet<CommentBean> commentBeanBaseListTemplet) throws Exception {
									commentList.addAll(commentBeanBaseListTemplet.getResults().getBody());
									adapter.notifyDataSetChanged();
									recyclerView.loadMoreComplete();
									recyclerView.setLoadingMoreEnabled(true);
								}
							}, new Consumer<Throwable>() {
								@Override
								public void accept(Throwable throwable) throws Exception {
									recyclerView.setLoadingMoreEnabled(true);
								}
							});

				}
			}
		});
		adapter = new ArticalDetialAdapter(commentList, this, content, title, autore);
		adapter.setOnLongPressItemListener(this);
		adapter.setOnWebVIewLoadedListener(this);
		recyclerView.setAdapter(adapter);
	}


	@Override
	protected void onResume() {
		if (!LoginUserHelper.getInstance().isLogined()) {
			et_make_comment.setFocusable(false);
			et_make_comment.setOnClickListener(this);
		} else {
			et_make_comment.setFocusable(true);
			et_make_comment.setFocusableInTouchMode(true);
		}
		super.onResume();
	}

	private void initData() {
		recyclerView.setNoMore(false);
		YzuClientDemo.getInstance().getCommentLists(type, articleId, 1, 10)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<BaseListTemplet<CommentBean>>() {
					@Override
					public void accept(BaseListTemplet<CommentBean> commentBeanBaseListTemplet) throws Exception {
						index = 1;
						total = commentBeanBaseListTemplet.getResults().getTotal();
						commentList.clear();
						commentList.addAll(commentBeanBaseListTemplet.getResults().getBody());
						adapter.notifyDataSetChanged();
					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {

					}
				});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		switch (actionId) {
			case EditorInfo.IME_ACTION_SEND:
				String comment = v.getText().toString();
				if (!TextUtils.isEmpty(comment)) {
					UiUtil.toast(v.getText().toString());
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					if (imm != null) {
						imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
								0);
					}
					//被at的那条评论
					if (atCommentBean != null) {
						if (("@" + atCommentBean.getUser().getNick() + " ").equals(atNick)) {
							if (comment.startsWith(atNick)) {
								sendComment(comment.substring(atNick.length()), atCommentBean.getId());
							} else if (comment.contains(atNick)) {
								sendComment(comment.replaceAll(atNick, ""), atCommentBean.getId());
							} else {
								atCommentBean = null;
								sendComment(comment, 0);
							}
						}
					} else {
						sendComment(comment, 0);
					}
				}


				break;
		}

		return true;
	}

	private void sendComment(String comment, int quoteId) {

		UserBean user = LoginUserHelper.getInstance().getUser();
		CommentPostBean commentPostBean = new CommentPostBean();
		commentPostBean.setTalkId(articleId);
		commentPostBean.setUid(user.getUid());
		commentPostBean.setExcerpt(comment);
		commentPostBean.setQuoteId(quoteId);
		commentPostBean.setType(type + "");

		YzuClientDemo.getInstance().sendComment(commentPostBean)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<BaseMgz>() {
					@Override
					public void accept(BaseMgz baseMgz) throws Exception {
						et_make_comment.setText("");
						atCommentBean = null;
						initData();
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
			case R.id.et_make_comment:
				if (!LoginUserHelper.getInstance().isLogined()) {
					Intent intent = new Intent(this, LoginActicity.class);
					startActivity(intent);
				}
				break;
		}
	}

	/**
	 * 长按弹出框
	 *
	 * @param itemView
	 * @param itemPosition
	 */
	@Override
	public void onLongPress(View itemView, final int itemPosition) {
		UiUtil.toast(commentList.get(itemPosition).getId() + "");
		final ListPopupWindow mListPop;
		List<String> lists = new ArrayList<>();
		lists.add("举报");
		lists.add("回复");
		lists.add("复制");
		if (LoginUserHelper.getInstance().isLogined()) {
			if (LoginUserHelper.getInstance().getUser().getUid() == commentList.get(itemPosition).getUser().getUid()) {
				lists.add("删除");
			}
		}
		mListPop = new ListPopupWindow(this);
		mListPop.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lists));
		mListPop.setWidth(DimentionUtils.dip2px(this, 70));
		mListPop.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
		mListPop.setAnchorView(itemView);
		mListPop.setModal(true);//设置是否是模式
		mListPop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				switch (position) {
					case 0://举报
						UiUtil.toast(String.format("少侠，你举报了%s的评论，内容如下:\n%s",
								commentList.get(itemPosition).getUser().getNick(), commentList.get(itemPosition).getExcerpt()));
						break;
					case 1://回复
						UiUtil.toast("回复");
						if (LoginUserHelper.getInstance().isLogined()) {
							et_make_comment.requestFocus();
							et_make_comment.setFocusableInTouchMode(true);
							CommentBean commentBean = commentList.get(itemPosition);
							getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

							String user = "@" + commentBean.getUser().getNick() + " ";
							atNick = user;
							atCommentBean = new CommentBean();
							UserBean quoteUser = new UserBean();
							quoteUser.setNick(commentBean.getUser().getNick());
							atCommentBean.setUser(quoteUser);//判断用户名是不是完整
							atCommentBean.setId(commentBean.getId());

							et_make_comment.setText(user);
							//游标位置
							et_make_comment.setSelection(user.length());
						} else {
							Intent intent = new Intent(ArticleDetialActivityRe.this, LoginActicity.class);
							startActivity(intent);
						}

						break;
					case 2://复制
						ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
						clipboardManager.setPrimaryClip(ClipData.newPlainText(null, commentList.get(itemPosition).getExcerpt()));
						if (clipboardManager.hasPrimaryClip()) {
							clipboardManager.getPrimaryClip().getItemAt(0).getText();
						}
//						UiUtil.toast("复制");
						break;
					case 3://删除
						UiUtil.toast("删除");
						break;
				}
				mListPop.dismiss();
			}
		});
		mListPop.show();
	}

	@Override
	public void onWebLoaded() {
		initData();
	}
}
