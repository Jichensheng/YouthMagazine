package com.jcs.magazine.adapter.ReMake;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcs.magazine.R;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.MomentBeanRefactor;
import com.jcs.magazine.bean.UserBean;
import com.jcs.magazine.global.LoginUserHelper;
import com.jcs.magazine.network.YzuClientDemo;

import io.reactivex.Observable;

/**
 * author：Jics
 * 2017/11/21 16:29
 */
public class TPostActivity extends BaseRefreshActivity<MomentBeanRefactor> {
	@Override
	protected void onCreate(@Nullable Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_post);
		super.init();
		Toolbar tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
		setSupportActionBar(tb_toolbar);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected Observable<BaseListTemplet<MomentBeanRefactor>> getObservable(int page, int count) {
		UserBean user = LoginUserHelper.getInstance().getUser();
		return YzuClientDemo.getInstance().getUserPostLists(user.getUid(), page, count);
	}

	@Override
	public void initData() {
		recyclerView= (XRecyclerView) findViewById(R.id.rv_my_post);
		adapter=new TPostAdapter(this, beanList,true);
	}
}
