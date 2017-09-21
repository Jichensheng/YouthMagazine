package com.jcs.magazine.activity.mine;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jcs.magazine.R;
import com.jcs.magazine.adapter.FollowTabAdapter;
import com.jcs.magazine.base.BaseActivity;
import com.jcs.magazine.fragment.FollowFragment;
import com.jcs.magazine.global.LoginUserHelper;

import java.util.ArrayList;
import java.util.List;

import static com.umeng.socialize.utils.ContextUtil.getContext;

/**
 * author：Jics
 * 2017/9/21 13:52
 */
public class FollowActivity extends BaseActivity implements View.OnClickListener{

	@Override
	protected void onCreate(@Nullable Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_follow);
		initView();
	}

	private void initView() {
		TextView tv_icon= (TextView) findViewById(R.id.tv_icon);
		Typeface iconfont = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
		tv_icon.setTypeface(iconfont);
		tv_icon.setText(getString(R.string.dingyue));
		tv_icon.setOnClickListener(this);

		Toolbar toolbar= (Toolbar) findViewById(R.id.tb_toolbar);
		setSupportActionBar(toolbar);

		ViewPager viewPager= (ViewPager) findViewById(R.id.vp_coor);

		List<FollowFragment> fragmentList=new ArrayList<>();
		FollowFragment follow = new FollowFragment();
		follow.setFollowType(FollowFragment.TYPE_FOLLOW);
		follow.setTabName("已关注");
		fragmentList.add(follow);
		FollowFragment follower = new FollowFragment();
		follower.setFollowType(FollowFragment.TYPE_FOLLOWER);
		follower.setTabName("关注我的");
		fragmentList.add(follower);

		viewPager.setAdapter(new FollowTabAdapter(getSupportFragmentManager(),fragmentList, LoginUserHelper.getInstance().getUser().getUid()));
		TabLayout tb= (TabLayout) findViewById(R.id.tb_other_user);
		tb.setTabTextColors(ContextCompat.getColor(getContext(), R.color.gray), ContextCompat.getColor(getContext(), R.color.tab_text_selected));
		tb.setTabMode(TabLayout.GRAVITY_CENTER);
		tb.setupWithViewPager(viewPager);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case android.R.id.home:
				finish();
				break;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.tv_icon:

				break;
		}
	}
}
