package com.jcs.magazine.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcs.magazine.R;
import com.jcs.magazine.adapter.MakePostGridAdapter;
import com.jcs.magazine.base.BaseActivity;
import com.jcs.magazine.util.UiUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * author：Jics
 * 2017/9/18 15:59
 */
public class MakePostActivity extends BaseActivity implements MakePostGridAdapter.PostClickListener, View.OnClickListener {
	private static final int MAX_WORD = 140;
	private static final int MAX_COUNT_PER_LINE = 4;
	private static final int REQUEST_CODE_CHOOSE = 0x223;
	private MakePostGridAdapter adapter;
	private List<String> paths;
	private EditText et_post;
	private TextView tv_words_remain;

	@Override
	protected void onCreate(@Nullable Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_make_post);
		initView();
	}

	private void initView() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
		setSupportActionBar(toolbar);
		et_post = (EditText) findViewById(R.id.et_post);
		tv_words_remain = (TextView) findViewById(R.id.tv_words_remain);

		RecyclerView rv_pics = (RecyclerView) findViewById(R.id.rv_pics);
		rv_pics.setLayoutManager(new GridLayoutManager(this, MAX_COUNT_PER_LINE));
		paths = new ArrayList<>();
		adapter = new MakePostGridAdapter(this, paths);
		adapter.setOnPostClickListener(this);
		rv_pics.setAdapter(adapter);

		ImageView iv_pic = (ImageView) findViewById(R.id.iv_pic);
		ImageView iv_topic = (ImageView) findViewById(R.id.iv_topic);
		ImageView iv_emotion = (ImageView) findViewById(R.id.iv_emotion);
		iv_pic.setOnClickListener(this);
		iv_topic.setOnClickListener(this);
		iv_emotion.setOnClickListener(this);
		et_post.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_WORD)});  //其中100最大输
		et_post.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				int remain = MAX_WORD - et_post.getText().length();
				tv_words_remain.setText("" + remain);
				if (remain > 0) {
					tv_words_remain.setTextColor(ContextCompat.getColor(MakePostActivity.this, R.color.colorAccent));
				} else
					tv_words_remain.setTextColor(ContextCompat.getColor(MakePostActivity.this, R.color.tab_text_selected));
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClickItem(int type, int position) {
		switch (type) {
			case MakePostGridAdapter.CLICK_TYPE_DELET:
				UiUtil.toast("点击了删除");
				paths.remove(position);
//				adapter.notifyItemRemoved(position);
				adapter.notifyDataSetChanged();
				break;
			case MakePostGridAdapter.CLICK_TYPE_RETYR:
				UiUtil.toast("点击了重试");
				picPicker();
				break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_pic:
				UiUtil.toast("点击了图片");
				picPicker();
				break;
			case R.id.iv_topic:
				break;
			case R.id.iv_emotion:
				break;

		}
	}

	private void picPicker() {
		RxPermissions rxPermissions = new RxPermissions(this);
		rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
				.subscribe(new Observer<Boolean>() {
					@Override
					public void onSubscribe(Disposable d) {

					}

					@Override
					public void onNext(Boolean aBoolean) {
						if (aBoolean) {
							Matisse.from(MakePostActivity.this)
									.choose(MimeType.ofImageNoGif(), false)
									.countable(true)
									.capture(true)
									.captureStrategy(new CaptureStrategy(true, "com.jcs.pic.fileprovider"))
									.maxSelectable(9)
									.gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
									.restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
									.thumbnailScale(0.85f)
									.imageEngine(new GlideEngine())
									.forResult(REQUEST_CODE_CHOOSE);
						} else {
							UiUtil.toast("权限申请失败");
						}
					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onComplete() {

					}
				});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
			paths.clear();
			paths.addAll(Matisse.obtainPathResult(data));
			adapter.notifyDataSetChanged();
		}
	}
}
