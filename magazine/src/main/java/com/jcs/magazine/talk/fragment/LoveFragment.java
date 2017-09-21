package com.jcs.magazine.talk.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcs.magazine.R;
import com.jcs.magazine.base.BaseFragment;
import com.jcs.magazine.bean.BannerItem;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.network.YzuClient;
import com.jcs.magazine.talk.adapter.LoveAdapter;
import com.jcs.magazine.talk.interfaces.TabFragmentInterface;
import com.jcs.magazine.widget.banner.BannerView;
import com.jcs.magazine.widget.banner.BannerViewFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author：Jics
 * 2017/9/5 14:32
 */
public class LoveFragment extends BaseFragment {

	private TabLayout tlTitle;
	private ViewPager vp;
	private List<TabFragmentInterface> children;
	private BannerView bannerView;
	private List<BannerItem> list;
	private LoveAdapter adapter;
	/*private ChildTalkFragment talk;
	private ChildRadioFragment radio;
	private ChildEverythingFragment everything;*/

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		children =new ArrayList<>();
		ChildTalkFragment talk = new ChildTalkFragment();
		talk.setTabName("电台");
		ChildRadioFragment radio = new ChildRadioFragment();
		radio.setTabName("说话");
		ChildEverythingFragment everything = new ChildEverythingFragment();
		everything.setTabName("万象");

		children.add(talk);
		children.add(radio);
		children.add(everything);

		adapter = new LoveAdapter(getChildFragmentManager(), children);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_love, container, false);
		initView(view);
		initData();
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private void initData() {
		list=new ArrayList<>();
		YzuClient.getInstance().getLoveBannder()
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<BaseListTemplet<BannerItem>>() {
					@Override
					public void accept(BaseListTemplet<BannerItem> bannerItemBaseListTemplet) throws Exception {
						list.addAll(bannerItemBaseListTemplet.getResults().getBody());
						bannerView.setViewFactory(new BannerViewFactory());
						bannerView.setDataList(list);
						bannerView.start();
					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {

					}
				});
	}

	private void initView(View parent) {
		tlTitle = (TabLayout) parent.findViewById(R.id.tb_main_fragment);
		bannerView= (BannerView) parent.findViewById(R.id.bv_banner);

		vp = (ViewPager) parent.findViewById(R.id.rv_coor);
		vp.setAdapter(adapter);
		vp.setOffscreenPageLimit(3);

		tlTitle.setTabTextColors(ContextCompat.getColor(getContext(), R.color.gray), ContextCompat.getColor(getContext(), R.color.tab_text_selected));
		tlTitle.setTabMode(TabLayout.GRAVITY_CENTER);
		tlTitle.setupWithViewPager(vp);

	}



}