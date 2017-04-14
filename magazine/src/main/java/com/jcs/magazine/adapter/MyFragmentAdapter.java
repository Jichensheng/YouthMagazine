package com.jcs.magazine.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Fragment适配器
 */
public class MyFragmentAdapter extends FragmentPagerAdapter {
	private List<Fragment> list;

	public MyFragmentAdapter(FragmentManager fm, List<Fragment> list) {
		super(fm);
		this.list = list;
	}

	/**
	 * 获取position的Fragment
	 *
	 * @param position
	 * @return
	 */
	@Override
	public Fragment getItem(int position) {
		return list.get(position);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return "风物杂谈" + position;
	}
}