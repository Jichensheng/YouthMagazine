package com.jcs.magazine.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.jcs.magazine.R;
import com.jcs.magazine.base.BaseActivity;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.MgzCoverBean;
import com.jcs.magazine.network.YzuClient;
import com.jcs.magazine.util.DialogHelper;
import com.jcs.magazine.util.NetworkUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class StartPage extends BaseActivity {
    final private String TAG="StartPage";
    private ValueAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: " );
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 不显示系统的标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
//        UiUtil.toast("VersionCode: "+BuildConfig.getLocalVersionCode()+"  VersionName: "+BuildConfig.getLocalVersionName());
        animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(1000);
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
                                if (NetworkUtil.isConnectingToInternet(StartPage.this)) {//服务器原因
                                    new DialogHelper(StartPage.this).show(new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            finish();
                                        }
                                    },"连接失败", "服务器维护中，请稍后重试");
                                }else{//网络原因
                                    new DialogHelper(StartPage.this).show(new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            finish();
                                        }
                                    },"连接失败","请检查网络是否连接");

                                }
                                Log.e(TAG, "accept: "+throwable.toString() );
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

    /**
     * 权限检测
     */
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