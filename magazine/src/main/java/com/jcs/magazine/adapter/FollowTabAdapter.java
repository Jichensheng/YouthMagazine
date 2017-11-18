package com.jcs.magazine.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jcs.magazine.fragment.FollowFragment;

import java.util.List;


/**
 * author：Jics
 * 2017/9/5 14:36
 */
public class FollowTabAdapter extends FragmentPagerAdapter {
	final int uid;
	private List<FollowFragment> children;
	public FollowTabAdapter(FragmentManager fm, List<FollowFragment> children,int uid) {
		super(fm);
		this.children =children;
		this.uid = uid;
	}



	@Override
	public Fragment getItem(int position) {

		FollowFragment fragment = children.get(position).getFragment();
		//设置uid来查看是谁的关注
		fragment.setUid(uid);
		return fragment;
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
