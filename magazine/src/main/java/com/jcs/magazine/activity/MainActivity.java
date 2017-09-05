package com.jcs.magazine.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.jcs.magazine.R;
import com.jcs.magazine.base.BaseActivity;
import com.jcs.magazine.base.BaseFragment;
import com.jcs.magazine.fragment.MagazineFragment;
import com.jcs.magazine.fragment.MineFragement;
import com.jcs.magazine.fragment.MomentFragment;
import com.jcs.magazine.talk.fragment.LoveFragment;
import com.jcs.magazine.util.StatusBarUtil;
import com.jcs.magazine.util.UiUtil;
import com.jcs.magazine.widget.navigatordemo.FragmentAdapter;
import com.jcs.magazine.widget.navigatordemo.IconTabPageIndicator;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

	private ViewPager mViewPager;
	private IconTabPageIndicator mIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		initViews();
	}

	private void initViews() {

		//注意了，这里使用了第三方库 StatusBarUtil
		StatusBarUtil.setTransparentForImageView(this, null);
		mViewPager = (ViewPager) findViewById(R.id.view_pager);

		mIndicator = (IconTabPageIndicator) findViewById(R.id.indicator);
		List<BaseFragment> fragments = initFragments();
		FragmentAdapter adapter = new FragmentAdapter(fragments, getSupportFragmentManager());
		mViewPager.setAdapter(adapter);
		mIndicator.setViewPager(mViewPager);


	}

	private List<BaseFragment> initFragments() {
		List<BaseFragment> fragments = new ArrayList<>();

		MagazineFragment magazineFragment = new MagazineFragment();


		magazineFragment.setTitle("期刊");
		magazineFragment.setIconId(R.drawable.tab_record_selector);
		fragments.add(magazineFragment);

		LoveFragment loveFragment = new LoveFragment();
		loveFragment.setTitle("L.O.V.E");
		loveFragment.setIconId(R.drawable.tab_talk_selector);
		fragments.add(loveFragment);

		MomentFragment momentFragment = new MomentFragment();
		momentFragment.setTitle("广场");
		momentFragment.setIconId(R.drawable.tab_moment_selector);
		fragments.add(momentFragment);

		BaseFragment baseFragment = new BaseFragment();
		baseFragment.setTitle("实验室");
		baseFragment.setIconId(R.drawable.tab_laboratory_selector);
		fragments.add(baseFragment);

		MineFragement mineFragement = new MineFragement();
		mineFragement.setTitle("我的");
		mineFragement.setIconId(R.drawable.tab_user_selector);
		fragments.add(mineFragement);


		return fragments;
	}

	private long pressTime;

	@Override
	public void onBackPressed() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - pressTime > 2000) {
			UiUtil.toast("再按一次退出应用");
			pressTime = currentTime;
		} else {
			System.exit(0);
		}
	}

}
