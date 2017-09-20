
package com.jcs.magazine.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
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

import com.jcs.magazine.R;
import com.jcs.magazine.adapter.MomentDetailAdapter;
import com.jcs.magazine.base.BaseActivity;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.BaseMgz;
import com.jcs.magazine.bean.CommentBean;
import com.jcs.magazine.bean.MomentBean;
import com.jcs.magazine.bean.UserBean;
import com.jcs.magazine.global.LoginUserHelper;
import com.jcs.magazine.network.YzuClient;
import com.jcs.magazine.util.DimentionUtils;
import com.jcs.magazine.util.UiUtil;
import com.jcs.magazine.widget.SimpleDividerItemDecoration;

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
	private RecyclerView recyclerView;
	private MomentBean mb;
	private Toolbar toolbar;
	private MomentDetailAdapter adapter;
	private List<CommentBean> commentList;
	private EditText et_make_comment;
	private String atNick = "";
	private CommentBean atCommentBean = null;

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
		mb = (MomentBean) getIntent().getSerializableExtra("mb");
		recyclerView = (RecyclerView) findViewById(R.id.rv_moment_detial);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new MomentDetailAdapter(this, mb, commentList);
		adapter.setOnLongPressItemListener(this);

		recyclerView.setAdapter(adapter);
		initData();
		recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this, DimentionUtils.dip2px(this, 1)));
		et_make_comment = (EditText) findViewById(R.id.et_make_comment);
		et_make_comment.setOnEditorActionListener(this);

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
		YzuClient.getInstance().getCommentLists(mb.getMid().trim(), 1, 10)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<BaseListTemplet<CommentBean>>() {
					@Override
					public void accept(BaseListTemplet<CommentBean> commentBeanBaseListTemplet) throws Exception {
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
					if (atCommentBean != null) {
						if (("@" + atCommentBean.getNick() + " ").equals(atNick)) {
							if (comment.startsWith(atNick)) {
								sendComment(comment.substring(atNick.length()),atCommentBean);
							} else if (comment.contains(atNick)){
								sendComment(comment.replaceAll(atNick,""), atCommentBean);
							}else{
								atCommentBean=null;
								sendComment(comment,null);
							}
						}
					} else {
						sendComment(comment, null);
					}
				}


				break;
		}

		return true;
	}

	private void sendComment(String comment, CommentBean quote) {
		/**
		 * 	//帖子id
		 private String mid;
		 //发帖人id
		 private String uid;
		 //评论文字
		 private String excerpt;
		 //评论时间
		 private String date;
		 //赞数
		 private String praise;
		 //评论人昵称
		 private String nick;
		 //评论人头像地址
		 private String head;
		 //引用评论
		 private CommentBean quote;
		 */
		UserBean user = LoginUserHelper.getInstance().getUser();
		CommentBean commentBean = new CommentBean();
		commentBean.setMid(mb.getMid());
		commentBean.setUid(user.getUid());
		commentBean.setExcerpt(comment);
		commentBean.setDate(String.valueOf(System.currentTimeMillis()));
		commentBean.setNick(user.getNick());
		commentBean.setHead(user.getHead());
		commentBean.setQuote(quote);

		YzuClient.getInstance().sendComment(commentBean)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<BaseMgz>() {
					@Override
					public void accept(BaseMgz baseMgz) throws Exception {
						// TODO: 2017/9/20 评论成功之后清除评论框并清除at的信息
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
		UiUtil.toast(commentList.get(itemPosition).getCid());
		final ListPopupWindow mListPop;
		List<String> lists = new ArrayList<>();
		lists.add("举报");
		lists.add("回复");
		lists.add("复制");
		if (LoginUserHelper.getInstance().isLogined()) {
			if (LoginUserHelper.getInstance().getUser().getUid().equals(commentList.get(itemPosition).getUid())) {
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
								commentList.get(itemPosition).getNick(),commentList.get(itemPosition).getExcerpt()));
						break;
					case 1://回复
						UiUtil.toast("回复");
						if (LoginUserHelper.getInstance().isLogined()) {
							et_make_comment.requestFocus();
							et_make_comment.setFocusableInTouchMode(true);
							CommentBean commentBean = commentList.get(itemPosition);
							getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

							String user = "@" + commentBean.getNick() + " ";
							atNick = user;
							atCommentBean = new CommentBean();
							atCommentBean.setNick(commentBean.getNick());
							atCommentBean.setExcerpt(commentBean.getExcerpt());
							atCommentBean.setUid(commentBean.getUid());

							et_make_comment.setText(user);
							et_make_comment.setSelection(user.length());
						} else {
							Intent intent = new Intent(MomentActivity.this, LoginActicity.class);
							startActivity(intent);
						}

						break;
					case 2://复制
						ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
						clipboardManager.setPrimaryClip(ClipData.newPlainText(null, commentList.get(itemPosition).getExcerpt()));
						if (clipboardManager.hasPrimaryClip()){
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
