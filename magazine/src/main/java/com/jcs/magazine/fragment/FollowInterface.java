package com.jcs.magazine.fragment;

import com.jcs.magazine.talk.interfaces.TabFragmentInterface;

/**
 * author：Jics
 * 2017/9/21 14:29
 */
public interface FollowInterface extends TabFragmentInterface {
	void setFollowType(int type);
	int getFollowType();
	void setUid(int uid);
	int getUid();
	FollowFragment getFragment();
}
