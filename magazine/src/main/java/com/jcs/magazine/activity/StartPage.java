package com.jcs.magazine.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

import com.jcs.magazine.R;
import com.jcs.magazine.base.BaseActivity;
import com.jcs.magazine.base.BaseApplication;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.MgzCoverBean;
import com.jcs.magazine.network.YzuClient;
import com.jcs.magazine.util.UiUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class StartPage extends BaseActivity {
	final private int REQUEST_CODE_ASK_PERMISSIONS = 123;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 不显示系统的标题栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_start);

		ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
		animator.setDuration(2000);
		animator.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				YzuClient.getInstance()
						.getMagazineCover(1, 3)//第一页，每页3条
						.subscribeOn(Schedulers.newThread())//网络请求开新线程
						.observeOn(AndroidSchedulers.mainThread())//网络响应在UI线程
						.subscribe(new Consumer<BaseListTemplet<MgzCoverBean>>() {
							@Override
							public void accept(BaseListTemplet<MgzCoverBean> mgzCoverBeanBaseMgz) throws Exception {
								Intent intent = new Intent(StartPage.this, MainActivity.class);
								Bundle bundle = new Bundle();
								bundle.putSerializable("covers", mgzCoverBeanBaseMgz);
								intent.putExtras(bundle);
								startActivity(intent);
								finish();
							}
						}, new Consumer<Throwable>() {
							@Override
							public void accept(Throwable throwable) throws Exception {
								UiUtil.toast("回调失败:" + throwable.toString());
							}
						});
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}
		});
		animator.start();

	}


	public void checkPermission() {
		if (Build.VERSION.SDK_INT >= 23) {
			List<String> permissionStrs = new ArrayList<>();
			int hasWriteSdcardPermission = ContextCompat.checkSelfPermission(BaseApplication.getInstance(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
			if (hasWriteSdcardPermission != PackageManager.PERMISSION_GRANTED) {
				permissionStrs.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
			}

/*			int hasCameraPermission = ContextCompat.checkSelfPermission(
					StartPage.this,
					Manifest.permission.CAMERA);
			if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
				permissionStrs.add(Manifest.permission.CAMERA);
			}*/
			String[] stringArray = permissionStrs.toArray(new String[0]);
			if (permissionStrs.size() > 0) {
				requestPermissions(stringArray, REQUEST_CODE_ASK_PERMISSIONS);
				return;
			}
		}
	}

	//权限设置后的回调函数，判断相应设置，requestPermissions传入的参数为几个权限，则permissions和grantResults为对应权限和设置结果
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
			case REQUEST_CODE_ASK_PERMISSIONS:
				//可以遍历每个权限设置情况
				if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					//这里写你需要相关权限的操作
				} else {
					UiUtil.toast("权限没有开启");
				}
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}

}