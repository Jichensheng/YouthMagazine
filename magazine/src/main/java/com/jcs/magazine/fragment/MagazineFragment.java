package com.jcs.magazine.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jcs.magazine.R;
import com.jcs.magazine.activity.PrefaceActivity;
import com.jcs.magazine.activity.StartPage;
import com.jcs.magazine.adapter.YZUPageAdapter;
import com.jcs.magazine.base.BaseFragment;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.ContentsBean;
import com.jcs.magazine.bean.MgzCoverBean;
import com.jcs.magazine.network.YzuClient;
import com.jcs.magazine.util.DialogHelper;
import com.jcs.magazine.util.UiUtil;
import com.jcs.magazine.yzu_viewPager.ScaleInTransformer;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * author：Jics
 * 2016/6/21 14:16
 */
public class MagazineFragment extends BaseFragment {
    private static final String TAG = "jcs_net";
    private ViewPager mViewPager;
    private YZUPageAdapter mAdapter;
    private List<MgzCoverBean> coverBeens;
    private BaseListTemplet<MgzCoverBean> mgzCoverBeanBaseMgz;
    private boolean isFirstClick=true;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_magazine, container, false);
        mgzCoverBeanBaseMgz = (BaseListTemplet<MgzCoverBean>) getActivity().getIntent().getSerializableExtra("covers");
        coverBeens = mgzCoverBeanBaseMgz.getResults().getBody();

        mViewPager = (ViewPager) view.findViewById(R.id.id_viewpager);
        // 设置Page间间距
        mViewPager.setPageMargin(10);
        // 设置缓存的页面数量
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageTransformer(true, new ScaleInTransformer());// 动画进大出小

        //viewPager的父容器把事件拦截了，否则只能拖动最中间的那个view才能左右滑动
        view.findViewById(R.id.container).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });
        mAdapter = new YZUPageAdapter(view.getContext(), coverBeens);
        mAdapter.setOnClickPageListener(new YZUPageAdapter.OnClickPageListener() {

            @Override
            public void onClickPage(final ImageView view, final int position) {
                if (isFirstClick) {//防止多次点击
                    isFirstClick=false;
                    final AlertDialog loading = new DialogHelper(getContext()).show(R.layout.loading);
                    //目录id
                    int contentsId = coverBeens.get(position).getId();
                    YzuClient.getInstance().getContents(contentsId)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<BaseListTemplet<ContentsBean>>() {
                                @Override
                                public void accept(BaseListTemplet<ContentsBean> contentsBeanListBeanTemplet) throws Exception {
                                    loading.dismiss();
                                    isFirstClick=true;
                                    Intent intent = new Intent(getActivity(), PrefaceActivity.class);
                                    intent.putExtra("img", coverBeens.get(position).getImages());
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("contents", contentsBeanListBeanTemplet);
                                    intent.putExtras(bundle);
                                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view, "cover");
                                    startActivity(intent, options.toBundle());
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    isFirstClick=true;
                                    loading.dismiss();
                                    UiUtil.toast("网络回调错误：" + throwable.toString());
                                }
                            });
                }

            }

        });

        mViewPager.setAdapter(mAdapter);
        return view;
    }

}
