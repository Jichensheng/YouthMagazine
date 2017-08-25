package com.jcs.magazine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jcs.magazine.R;
import com.jcs.magazine.adapter.MomentDetailAdapter;
import com.jcs.magazine.base.BaseActivity;
import com.jcs.magazine.bean.MomentBean;

/**
 * author：Jics
 * 2017/4/11 14:31
 */
public class MomentActivity extends BaseActivity {
	private RecyclerView recyclerView;
	private MomentBean mb;
	private Toolbar toolbar;
	private MomentDetailAdapter adapter;
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moment);
		initView();
	}

	public void initView() {

		toolbar= (Toolbar) findViewById(R.id.tb_toolbar);
		toolbar.setTitle(getIntent().getStringExtra("nickname"));
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mb= (MomentBean) getIntent().getSerializableExtra("mb");
		recyclerView= (RecyclerView) findViewById(R.id.rv_moment_detial);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new MomentDetailAdapter(this, mb);

		recyclerView.setAdapter(adapter);
		recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
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
