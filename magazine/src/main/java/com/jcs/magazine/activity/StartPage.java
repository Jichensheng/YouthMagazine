package com.jcs.magazine.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jcs.magazine.R;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.MgzCoverBean;
import com.jcs.magazine.network.YzuClient;
import com.jcs.magazine.util.UiUtil;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class StartPage extends Activity {

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
                        .getMagazineCover(1,3)//第一页，每页3条
                        .subscribeOn(Schedulers.newThread())//网络请求开新线程
                        .observeOn(AndroidSchedulers.mainThread())//网络响应在UI线程
                        .subscribe(new Consumer<BaseListTemplet<MgzCoverBean>>() {
                            @Override
                            public void accept(BaseListTemplet<MgzCoverBean> mgzCoverBeanBaseMgz) throws Exception {
                                Intent intent=new Intent(StartPage.this,MainActivity.class);
                                Bundle bundle=new Bundle();
                                bundle.putSerializable("covers",mgzCoverBeanBaseMgz);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                UiUtil.toast( "回调失败:"+throwable.toString());
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

    private void gotoMainUI() {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        this.finish();
    }


}