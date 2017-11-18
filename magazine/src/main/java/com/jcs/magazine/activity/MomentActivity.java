
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcs.magazine.R;
import com.jcs.magazine.adapter.MomentDetailAdapter;
import com.jcs.magazine.base.BaseActivity;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.BaseMgz;
import com.jcs.magazine.bean.CommentBean;
import com.jcs.magazine.bean.CommentPostBean;
import com.jcs.magazine.bean.MomentBeanRefactor;
import com.jcs.magazine.bean.UserBean;
import com.jcs.magazine.config.BuildConfig;
import com.jcs.magazine.global.LoginUserHelper;
import com.jcs.magazine.network.YzuClientDemo;
import com.jcs.magazine.util.DimentionUtils;
import com.jcs.magazine.util.RelativeDateFormat;
import com.jcs.magazine.util.UiUtil;
import com.jcs.magazine.util.glide.GlideCircleTransform;
import com.jcs.magazine.widget.CircleImageView;
import com.jcs.magazine.widget.SimpleDividerItemDecoration;
import com.jcs.magazine.widget.nine_grid.NineGridTestLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 说说详情页
 * author：Jics
 * 2017/4/11 14:31
 */
public class MomentActivity extends BaseActivity implements TextView.OnEditorActionListener,
		View.OnClickListener, MomentDetailAdapter.OnLongPressItemListener {
	private XRecyclerView recyclerView;
	private MomentBeanRefactor mb;
	private Toolbar toolbar;
	private MomentDetailAdapter adapter;
	private List<CommentBean> commentList;
	private EditText et_make_comment;
	private String atNick = "";
	private CommentBean atCommentBean = null;
	private int index = 1;
	private int total = 0;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moment);
		initView();
	}

	public void initView() {
		commentList = new ArrayList<>();

		toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
		toolbar.setTitle(getIntent().getStringExtra("nickname"));
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mb = (MomentBeanRefactor) getIntent().getSerializableExtra("mb");

		recyclerView = (XRecyclerView) findViewById(R.id.rv_moment_detial);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new MomentDetailAdapter(this, commentList);
		adapter.setOnLongPressItemListener(this);

		recyclerView.setAdapter(adapter);
		initData();
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
					YzuClientDemo.getInstance().getCommentLists(BuildConfig.COMMENT_TYPE_MOMENT, mb.getMid(), ++index, 10)
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
		et_make_comment = (EditText) findViewById(R.id.et_make_comment);
		et_make_comment.setOnEditorActionListener(this);
		initDetail();
	}

	/**
	 * 上部分
	 */
	private void initDetail() {
		LinearLayout ll_head_container;
		NineGridTestLayout nineGridTestLayout;
		final CircleImageView civ;
		TextView nick, tv_content, tv_public_time, tv_praise, tv_btn;

		ll_head_container = (LinearLayout) findViewById(R.id.ll_head_container);
		nineGridTestLayout = (NineGridTestLayout) findViewById(R.id.layout_nine_grid);
		tv_content = (TextView) findViewById(R.id.tv_content);
		civ = (CircleImageView) findViewById(R.id.civ_head);
		nick = (TextView) findViewById(R.id.tv_nickname);
		tv_btn = (TextView) findViewById(R.id.tv_btn);
		tv_public_time = (TextView) findViewById(R.id.tv_public_time);
		tv_praise = (TextView) findViewById(R.id.tv_praise);

		List<UserBean> praiser = mb.getPraiser();
		Glide.with(this).load(mb.getPostman().getHead())
				.error(R.drawable.default_avater)
				.into(new SimpleTarget<GlideDrawable>() {
					@Override
					public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
						civ.setImageDrawable(resource);
					}
				});
		//用户已登录且是自己发的帖子
		if (LoginUserHelper.getInstance().isLogined()
				&& LoginUserHelper.getInstance().getUser().getUid()==mb.getPostman().getUid()) {
			tv_btn.setText("删除");
			// TODO: 2017/9/14 删除逻辑
		} else {
			tv_btn.setText("+关注");
			// TODO: 2017/9/14 关注逻辑
		}

		//urls是九宫格图片
		final List<String> urls = mb.getImages();
		nineGridTestLayout.setUrlList(urls);
		nick.setText(mb.getPostman().getNick());
		tv_public_time.setText(RelativeDateFormat.formatString(mb.getDate()));
		tv_content.setText(mb.getExcerpt());
		tv_praise.setText("" + mb.getPraise());
		if (praiser != null) {
			for (final UserBean userBean : praiser) {
				final ImageView imageView = new ImageView(this);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DimentionUtils.dip2px(this, 35), DimentionUtils.dip2px(this, 35));
				lp.setMargins(DimentionUtils.dip2px(this, 5), 0, 0, 0);
				Glide.with(this).load(userBean.getHead())
						.transform(new GlideCircleTransform(this))
						.placeholder(R.drawable.default_avater)
						.error(R.drawable.default_avater)
						.into(new SimpleTarget<GlideDrawable>() {
							@Override
							public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
								imageView.setImageDrawable(resource);
							}
						});
				ll_head_container.addView(imageView, lp);
				imageView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						UiUtil.toast(userBean.toString());
					}
				});
			}
		}
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
		YzuClientDemo.getInstance().getCommentLists(2,mb.getMid(), 1, 10)
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

	private void sendComment(String comment,int quoteId) {

		UserBean user = LoginUserHelper.getInstance().getUser();
		CommentPostBean commentPostBean = new CommentPostBean();
		commentPostBean.setTalkId(mb.getMid());
		commentPostBean.setUid(user.getUid());
		commentPostBean.setExcerpt(comment);
		commentPostBean.setQuoteId(quoteId);
		commentPostBean.setType("2");

		YzuClientDemo.getInstance().sendComment(commentPostBean)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<BaseMgz>() {
					@Override
					public void accept(BaseMgz baseMgz) throws Exception {
						// TODO: 2017/9/20 评论成功之后清除评论框并清除at的信息
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
					Intent intent = new Intent(MomentActivity.this, LoginActicity.class);
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
		UiUtil.toast(commentList.get(itemPosition).getId()+"");
		final ListPopupWindow mListPop;
		List<String> lists = new ArrayList<>();
		lists.add("举报");
		lists.add("回复");
		lists.add("复制");
		if (LoginUserHelper.getInstance().isLogined()) {
			if (LoginUserHelper.getInstance().getUser().getUid()==commentList.get(itemPosition).getUser().getUid()) {
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
							et_make_comment.setSelection(user.length());
						} else {
							Intent intent = new Intent(MomentActivity.this, LoginActicity.class);
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
}
