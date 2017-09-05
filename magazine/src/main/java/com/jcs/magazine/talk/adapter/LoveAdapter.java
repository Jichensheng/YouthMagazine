package com.jcs.magazine.talk.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jcs.magazine.talk.interfaces.LoveInterface;

import java.util.List;


/**
 * author：Jics
 * 2017/9/5 14:36
 */
public class LoveAdapter extends FragmentPagerAdapter {
	private List<LoveInterface> children;
	public LoveAdapter(FragmentManager fm,List<LoveInterface> children) {
		super(fm);
		this.children =children;
	}

	@Override
	public Fragment getItem(int position) {
		return children.get(position).getFragment();
	}

	@Override
	public int getCount() {
		return children.size();
	}
	/**
	 * 给tablayout设置标题
	 * @param position
	 * @return
	 */
	@Override
	public CharSequence getPageTitle(int position) {
		return children.get(position).getTabName();
	}
}
