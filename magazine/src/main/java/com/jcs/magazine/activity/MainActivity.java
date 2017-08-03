package com.jcs.magazine.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.jcs.magazine.R;
import com.jcs.magazine.base.BaseActivity;
import com.jcs.magazine.base.BaseFragment;
import com.jcs.magazine.fragment.MagazineFragment;
import com.jcs.magazine.fragment.MomentFragment;
import com.jcs.magazine.fragment.TalkFragment;
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

/*        Bundle bundle = new Bundle();
        bundle.putSerializable("arg", getIntent().getSerializableExtra("covers"));
        magazineFragment.setArguments(bundle);*/

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
		contactFragment.setTitle("实验1");
		contactFragment.setIconId(R.drawable.tab_user_selector);
		fragments.add(contactFragment);

		BaseFragment recordFragment = new BaseFragment();
		recordFragment.setTitle("实验2");
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
