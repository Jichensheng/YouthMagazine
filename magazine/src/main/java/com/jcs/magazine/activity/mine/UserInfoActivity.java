package com.jcs.magazine.activity.mine;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jcs.magazine.R;
import com.jcs.magazine.base.BaseActivity;
import com.jcs.magazine.bean.UserBean;
import com.jcs.magazine.global.LoginUserHelper;
import com.jcs.magazine.network.YzuClient;
import com.jcs.magazine.util.BitmapUtil;
import com.jcs.magazine.util.DialogHelper;
import com.jcs.magazine.util.DimentionUtils;
import com.jcs.magazine.util.FileUtil;
import com.jcs.magazine.util.MessageEvent;
import com.jcs.magazine.util.UiUtil;
import com.jcs.magazine.widget.CircleImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.jcs.magazine.R.id.btn_exit;

/**
 * author：Jics
 * 2017/9/13 10:07
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {
	private static final int REQUEST_CODE_CHOOSE = 0x223;
	private CircleImageView civ_avater;
	private SuperTextView stv_name, stv_nick, stv_phone, stv_college;
	private TextView tv_save;
	private boolean needSave;
	private UserBean user;
	private Map<Integer, Integer> changeState;
	private String headPath="";

	@Override
	protected void onCreate(@Nullable Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_user_info);
		initView();
	}

	private void initView() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
		setSupportActionBar(toolbar);
//		setTitle("编辑资料");
		changeState = new HashMap<>();

		Button btn_exit = (Button) findViewById(R.id.btn_exit);
		tv_save = (TextView) findViewById(R.id.tv_save);
		tv_save.setTextColor(ContextCompat.getColor(this, R.color.light_gray));
		civ_avater = (CircleImageView) findViewById(R.id.civ_avater);
		stv_name = (SuperTextView) findViewById(R.id.stv_name);
		stv_nick = (SuperTextView) findViewById(R.id.stv_nick);
		stv_phone = (SuperTextView) findViewById(R.id.stv_phone);
		stv_college = (SuperTextView) findViewById(R.id.stv_college);

		btn_exit.setOnClickListener(this);
		civ_avater.setOnClickListener(this);
		tv_save.setOnClickListener(this);
//		stv_name.setOnClickListener(this);
		stv_nick.setOnClickListener(this);
		// TODO: 2017/9/14 修改绑定手机号等用户系统完善后再处理
//		stv_phone.setOnClickListener(this);
		stv_college.setOnClickListener(this);

		user = LoginUserHelper.getInstance().getUser();
		if (user != null) {
			Glide.with(this).load(user.getHead()).error(R.drawable.default_avater).into(civ_avater);
			stv_name.setRightString(user.getName());
			stv_nick.setRightString(user.getNick());
			stv_phone.setRightString(user.getPhone());
			stv_college.setRightString(user.getCollege());
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
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
	public void onClick(View v) {
		switch (v.getId()) {
			case btn_exit:
				SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
				sp.edit().putBoolean("user_info_isloged", false).apply();
				LoginUserHelper.getInstance().setLoginUser(null);
				EventBus.getDefault().post(new MessageEvent("refresh_login_state"));
				finish();
				break;
			case R.id.civ_avater:
				picPicker();
				break;
		/*	case R.id.stv_name:
				popupDialog(stv_name);
				break;*/
			case R.id.stv_nick:
				popupDialog(stv_nick, user.getNick());
				break;
		/*	case R.id.stv_phone:
				popupDialog(stv_phone);
				break;*/
			case R.id.stv_college:
				popupDialog(stv_college, user.getCollege());
				break;
			case R.id.tv_save:
				UiUtil.toast(needSave ? "保存" : "不保存");
				if (needSave) {
					save();
				}
				break;
		}
	}



	private void popupDialog(final SuperTextView view, final String remote) {

		final EditText edittext = new EditText(UserInfoActivity.this);

		edittext.setText(view.getRightString());
		edittext.setTextSize(16);
		edittext.setBackgroundColor(Color.TRANSPARENT);
		edittext.setPadding(DimentionUtils.dip2px(this, 28), DimentionUtils.dip2px(this, 30), DimentionUtils.dip2px(this, 30), DimentionUtils.dip2px(this, 10));
		edittext.setSelection(view.getRightString().length());


		new DialogHelper(this).show(new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				int count = 0;
				if (changeState.containsKey(view.getId())) {
					count = changeState.get(view.getId());
				} else {
					changeState.put(view.getId(), count);
				}

				//编辑框文字不为空且不等于原框文字
				Editable text = edittext.getText();
				if (!TextUtils.isEmpty(text) && !text.equals(view.getRightString())) {
					view.setRightString(text);
				}
				if (text.toString().equals(remote)) {
					count = count == 0 ? 0 : count - 1;
				} else {
					count = count == 1 ? 1 : count + 1;
				}
				changeState.put(view.getId(),count);
				int temp=0;
				for (Integer integer : changeState.values()) {
					temp += integer;
				}
				if (temp <= 0&&headPath.length()==0) {
					tv_save.setTextColor(ContextCompat.getColor(UserInfoActivity.this, R.color.light_gray));
					needSave = false;
				} else {
					tv_save.setTextColor(ContextCompat.getColor(UserInfoActivity.this, R.color.btn_red));
					needSave = true;
				}
			}
		}, true, edittext, 0, view.getLeftString(), null, true);
	}

	private void save() {
		//选中的头像
		File file=headPath.length()!=0?new File(FileUtil.getProjectRootFile(),FileUtil.DEFAULT_PIC_HEAD_NAME):null;

		MultipartBody.Builder requestBodyBuilder=new MultipartBody.Builder().setType(MultipartBody.FORM);

		if (!TextUtils.isEmpty(stv_nick.getRightString())) requestBodyBuilder.addFormDataPart("nick",stv_nick.getRightString());
		if (!TextUtils.isEmpty(stv_college.getRightString())) requestBodyBuilder.addFormDataPart("college",stv_college.getRightString());

		if(file!=null&&file.exists()){
			requestBodyBuilder.addFormDataPart("head","head.jpg", RequestBody.create(MediaType.parse("image/*"),file));
		}

		YzuClient.getInstance()
				.updataUserInfo(requestBodyBuilder.build())
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<UserBean>() {
					@Override
					public void accept(UserBean userBean) throws Exception {
						LoginUserHelper.getInstance().setLoginUser(userBean);
						EventBus.getDefault().post(new MessageEvent("refresh_login_state"));
						finish();
					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {

					}
				});
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
							Matisse.from(UserInfoActivity.this)
									.choose(MimeType.ofImageNoGif(), false)
									.countable(true)
									.capture(true)
									.captureStrategy( new CaptureStrategy(true, "com.jcs.pic.fileprovider"))
									.maxSelectable(1)
									.gridExpectedSize( getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
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

	/**
	 * 选择之后的回调
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
			needSave = true;
			tv_save.setTextColor(ContextCompat.getColor(UserInfoActivity.this, R.color.btn_red));
			List<String> list = Matisse.obtainPathResult(data);
			headPath= list.get(0);
//			Glide.with(this).load(headPath).error(R.drawable.default_avater).into(civ_avater);
			Glide.with(this).load(headPath).asBitmap().error(R.drawable.default_avater)
					.into(new SimpleTarget<Bitmap>(300,400) {
						@Override
						public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
							civ_avater.setImageBitmap(resource);
							new Thread(new Runnable() {
								@Override
								public void run() {
									BitmapUtil.saveBitmap(FileUtil.DEFAULT_PIC_HEAD_NAME,resource);
								}
							}).start();
						}
					});
		}
	}
}
