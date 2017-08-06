package com.jcs.magazine.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.jcs.magazine.R;
import com.jcs.magazine.base.BaseActivity;
import com.jcs.magazine.base.BaseApplication;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.MgzCoverBean;
import com.jcs.magazine.config.BuildConfig;
import com.jcs.magazine.network.YzuClient;
import com.jcs.magazine.util.DialogHelper;
import com.jcs.magazine.util.UiUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class StartPage extends BaseActivity {
    final private String TAG="生命周期";
    private ValueAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: " );
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 不显示系统的标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        UiUtil.toast("VersionCode: "+BuildConfig.getLocalVersionCode()+"  VersionName: "+BuildConfig.getLocalVersionName());
        animator = ValueAnimator.ofFloat(0, 1);
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
        requestPermission();

    }

    private void requestPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            animator.start();
                        } else {
                            new DialogHelper(StartPage.this).show(new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                    finish();
                                }
                            },"友情提示","没有储存权限用户存取数据会受到影响，是否手动开启权限");

                        }
                    }
                });
    }

}