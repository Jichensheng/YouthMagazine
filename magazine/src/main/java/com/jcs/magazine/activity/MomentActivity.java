package com.jcs.magazine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jcs.magazine.R;
import com.jcs.magazine.adapter.MomentDetailAdapter;
import com.jcs.magazine.base.BaseActivity;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.CommentBean;
import com.jcs.magazine.bean.MomentBean;
import com.jcs.magazine.network.YzuClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 说说详情页
 * author：Jics
 * 2017/4/11 14:31
 */
public class MomentActivity extends BaseActivity {
	private RecyclerView recyclerView;
	private MomentBean mb;
	private Toolbar toolbar;
	private MomentDetailAdapter adapter;
	private List<CommentBean> commentList;
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moment);
		initView();
	}

	public void initView() {
		commentList=new ArrayList<>();

		toolbar= (Toolbar) findViewById(R.id.tb_toolbar);
		toolbar.setTitle(getIntent().getStringExtra("nickname"));
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mb= (MomentBean) getIntent().getSerializableExtra("mb");
		recyclerView= (RecyclerView) findViewById(R.id.rv_moment_detial);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new MomentDetailAdapter(this, mb,commentList);

		recyclerView.setAdapter(adapter);
		initData();
//		recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
	}

	private void initData() {
		YzuClient.getInstance().getCommentLists(mb.getMid().trim(),1,10)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<BaseListTemplet<CommentBean>>() {
					@Override
					public void accept(BaseListTemplet<CommentBean> commentBeanBaseListTemplet) throws Exception {
						commentList.clear();
						commentList.addAll(commentBeanBaseListTemplet.getResults().getBody());
						adapter.notifyDataSetChanged();
					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {

					}
				});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId()==android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
