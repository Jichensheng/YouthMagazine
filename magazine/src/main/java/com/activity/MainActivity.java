package com.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.base.BaseFragment;
import com.fragment.MagazineFragment;
import com.fragment.MomentFragment;
import com.fragment.TalkFragment;
import com.widget.navigatordemo.FragmentAdapter;
import com.widget.navigatordemo.IconTabPageIndicator;
import com.githang.navigatordemo.R;
import com.util.UiUtil;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {

	private ViewPager mViewPager;
	private IconTabPageIndicator mIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		initViews();
	}

	private void initViews() {
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

		TalkFragment talkFragment = new TalkFragment();
		talkFragment.setTitle("尔闻");
		talkFragment.setIconId(R.drawable.tab_user_selector);
		fragments.add(talkFragment);

		MomentFragment momentFragment = new MomentFragment();
		momentFragment.setTitle("广场");
		momentFragment.setIconId(R.drawable.tab_record_selector);
		fragments.add(momentFragment);

		BaseFragment contactFragment = new BaseFragment();
		contactFragment.setTitle("轻办公");
		contactFragment.setIconId(R.drawable.tab_user_selector);
		fragments.add(contactFragment);

		BaseFragment recordFragment = new BaseFragment();
		recordFragment.setTitle("我的");
		recordFragment.setIconId(R.drawable.tab_record_selector);
		fragments.add(recordFragment);


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
