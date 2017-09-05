package com.jcs.magazine.talk.fragment;

import android.support.v4.app.Fragment;

import com.jcs.magazine.talk.interfaces.LoveInterface;

/**
 * author：Jics
 * 2017/9/5 14:38
 */
public class ChildEverything extends Fragment implements LoveInterface{
	private String tabName;


	@Override
	public void setTabName(String tabName) {
		this.tabName=tabName;
	}

	@Override
	public String getTabName() {
		return tabName;
	}

	@Override
	public Fragment getFragment() {
		return this;
	}
}
