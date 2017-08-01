package com.jcs.magazine.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jcs.magazine.fragment.ArticleFragment;

import java.util.List;

/**
 * Fragment适配器
 */
public class ArtFragmentAdapter extends FragmentPagerAdapter {
	private List<ArticleFragment> list;

	public ArtFragmentAdapter(FragmentManager fm, List<ArticleFragment> list) {
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

	/**
	 * TabLayout里对应的名字
	 * @param position
	 * @return
	 */
	@Override
	public CharSequence getPageTitle(int position) {
		return list.get(position).getName();
	}
}