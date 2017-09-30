package com.jcs.magazine.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jcs.magazine.R;
import com.jcs.magazine.adapter.MakePostGridAdapter;
import com.jcs.magazine.base.BaseActivity;
import com.jcs.magazine.bean.BaseMgz;
import com.jcs.magazine.bean.UserBean;
import com.jcs.magazine.global.LoginUserHelper;
import com.jcs.magazine.network.YzuClientDemo;
import com.jcs.magazine.network.upload.DefaultProgressListener;
import com.jcs.magazine.network.upload.UploadFileRequestBody;
import com.jcs.magazine.util.BitmapUtil;
import com.jcs.magazine.util.FileUtil;
import com.jcs.magazine.util.LocalFileManager;
import com.jcs.magazine.util.UiUtil;
import com.jcs.topsnackbar.Prompt;
import com.jcs.topsnackbar.TSnackbar;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

/**
 * author：Jics
 * 2017/9/18 15:59
 */
public class MakePostActivity extends BaseActivity implements MakePostGridAdapter.PostClickListener, View.OnClickListener {
	private String TAG = getClass().getName();
	private static final int MAX_WORD = 140;
	private static final int MAX_COUNT_PER_LINE = 4;
	private static final int REQUEST_CODE_CHOOSE = 0x223;
	private MakePostGridAdapter adapter;
	private List<String> paths;
	private List<File> uploadPic;
	private EditText et_post;
	private TextView tv_words_remain, tv_send_post, title;
	private boolean isUploading;

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
		tv_send_post = (TextView) findViewById(R.id.tv_send_post);
		title = (TextView) findViewById(R.id.title);

		RecyclerView rv_pics = (RecyclerView) findViewById(R.id.rv_pics);
		rv_pics.setLayoutManager(new GridLayoutManager(this, MAX_COUNT_PER_LINE));
		paths = new ArrayList<>();
		uploadPic = new ArrayList<>();
		adapter = new MakePostGridAdapter(this, paths);
		adapter.setOnPostClickListener(this);
		rv_pics.setAdapter(adapter);

		ImageView iv_pic = (ImageView) findViewById(R.id.iv_pic);
		ImageView iv_topic = (ImageView) findViewById(R.id.iv_topic);
		ImageView iv_emotion = (ImageView) findViewById(R.id.iv_emotion);
		iv_pic.setOnClickListener(this);
		iv_topic.setOnClickListener(this);
		tv_send_post.setOnClickListener(this);
		iv_emotion.setOnClickListener(this);
		et_post.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_WORD)});  //其中100最大输
		et_post.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				tv_send_post.setClickable(false);
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
				if (s.length() > 0) {
					tv_send_post.setClickable(true);
					tv_send_post.setTextColor(ContextCompat.getColor(MakePostActivity.this, R.color.tab_text_selected));
				} else {
					tv_send_post.setClickable(false);
					tv_send_post.setTextColor(ContextCompat.getColor(MakePostActivity.this, R.color.light_gray));
				}
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
				paths.remove(position);
				uploadPic.remove(position);
//				adapter.notifyItemRemoved(position);
				adapter.notifyDataSetChanged();

				break;
			case MakePostGridAdapter.CLICK_TYPE_RETYR:
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

			case R.id.tv_send_post:
				sendPost();
				break;

		}
	}

	/**
	 * 发说说的业务逻辑
	 */
	private void sendPost() {

		UserBean user = LoginUserHelper.getInstance().getUser();
		if (user!=null&&!TextUtils.isEmpty(et_post.getText()) && !isUploading) {
			isUploading = true;
			MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
			requestBodyBuilder.addFormDataPart("excerpt", et_post.getText().toString());
			requestBodyBuilder.addFormDataPart("uid", user.getUid());
			requestBodyBuilder.addFormDataPart("head", user.getHeadName());
			requestBodyBuilder.addFormDataPart("nick", user.getNick());
			for (int i = 0; i < uploadPic.size(); i++) {
				UploadFileRequestBody fileRequestBody = new UploadFileRequestBody(uploadPic.get(i), new DefaultProgressListener(mHandler, i));
				requestBodyBuilder.addFormDataPart("pic", uploadPic.get(i).getName(), fileRequestBody);
			}
			// TODO: 2017/9/19
			YzuClientDemo.getInstance()
					.makePost(requestBodyBuilder.build())
					.subscribeOn(Schedulers.newThread())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(new Consumer<BaseMgz>() {
						@Override
						public void accept(BaseMgz baseMgz) throws Exception {
							Log.e(TAG, "accept: " + baseMgz.toString());
							if (baseMgz.isSucc()) {
								// TODO: 2017/9/19 上传成功收到服务器的回调时清除临时图片
								finish();
							}
							isUploading = false;
							title.setText("编辑");
						}
					}, new Consumer<Throwable>() {
						@Override
						public void accept(Throwable throwable) throws Exception {
							isUploading = false;
							title.setText("编辑");
						}
					});
		} else if (isUploading) {
			TSnackbar.make(findViewById(android.R.id.content).getRootView(),
					"别着急，还在发送中", TSnackbar.LENGTH_SHORT, TSnackbar.APPEAR_FROM_TOP_TO_DOWN)
					.setPromptThemBackground(Prompt.WARNING).show();
		}


	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
//			Log.e(TAG, "handleMessage: " + String.format("arg= %d, what = %d", msg.arg1, msg.what));
			if (msg.what != 100) {//否则会重新遍历一遍100
				title.setText("发送中（" + (msg.arg1 + 1) + "/" + uploadPic.size() + "）");
			}
		}
	};

	private void picPicker() {
		if (isUploading) {
			TSnackbar.make(findViewById(android.R.id.content).getRootView(),
					"别着急，还在发送中", TSnackbar.LENGTH_SHORT, TSnackbar.APPEAR_FROM_TOP_TO_DOWN)
					.setPromptThemBackground(Prompt.WARNING).show();
		} else {
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

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
			final File imageDir = FileUtil.getImageCacheFile();
			// TODO: 2017/9/19 是否有正在上传的 有的话不要清除缓存
			LocalFileManager.getInstance().deleteAllFiles(imageDir);//清空上次缓存
			uploadPic.clear();
			paths.clear();
			paths.addAll(Matisse.obtainPathResult(data));
			adapter.notifyDataSetChanged();
			for (int i = 0; i < paths.size(); i++) {
				Glide.with(this).load(paths.get(i)).asBitmap().error(R.drawable.default_avater)
						.into(new SimpleTarget<Bitmap>(400, 300) {
							@Override
							public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
								new Thread(new Runnable() {
									@Override
									public void run() {
										File file = new File(imageDir, System.currentTimeMillis() + ".jpg");
										uploadPic.add(file);
										BitmapUtil.saveBitmap(file, resource);
									}
								}).start();
							}
						});
			}

		}
	}

}
