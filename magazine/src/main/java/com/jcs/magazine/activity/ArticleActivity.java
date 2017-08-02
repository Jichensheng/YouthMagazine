package com.jcs.magazine.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.jcs.magazine.R;
import com.jcs.magazine.adapter.ArtFragmentAdapter;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.ContentsBean;
import com.jcs.magazine.fragment.ArticleFragment;

import java.util.ArrayList;
import java.util.List;

public class ArticleActivity extends AppCompatActivity {
	private Toolbar tb;
	private TabLayout tlTitle;
	private ViewPager vp;
	private List<ArticleFragment> lists;
	private String TAG="ArticleActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article);
		initView();
	}

	private void initView() {

		BaseListTemplet<ContentsBean> contentsBeanListBeanTemplet= (BaseListTemplet<ContentsBean>) getIntent().getSerializableExtra("contents");
		Log.e(TAG, "accept: "+contentsBeanListBeanTemplet.getMsg()
				+"\n目录列表级别："+contentsBeanListBeanTemplet.getResults().toString()
				+"\n单个目录下的文章列表级别"+contentsBeanListBeanTemplet.getResults().getBody().get(0).toString()
				+"\n单个目录下的文章列表下的单篇文章级别"+contentsBeanListBeanTemplet.getResults().getBody().get(0).getArticles().get(0).toString()
		);
		//所有目录
		List<ContentsBean> contents=contentsBeanListBeanTemplet.getResults().getBody();

//		UiUtil.toast(contentsBeanListBeanTemplet.print());
		String position=getIntent().getStringExtra("position");
		initViewPager(contents,position);
		initToolbar();
	}

	private void initToolbar() {
		tb = (Toolbar) findViewById(R.id.tb_toolbar);
		setTitle("");
		setSupportActionBar(tb);
		//设置默认的返回键
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//		自定义返回键
//		tb.setNavigationIcon(R.mipmap.backup);

		tlTitle = (TabLayout) findViewById(R.id.tlTitle);
		tlTitle.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.transparent));
		tlTitle.setTabTextColors(ContextCompat.getColor(this, R.color.light_gray), ContextCompat.getColor(this, R.color.text_main_black));
		tlTitle.setTabMode(TabLayout.MODE_SCROLLABLE);
		tlTitle.setupWithViewPager(vp);

	}

	private void initViewPager(List<ContentsBean> contents, String position) {
		vp = (ViewPager) findViewById(R.id.vp);
		lists = new ArrayList<>();
		for (ContentsBean content : contents) {
			lists.add(new ArticleFragment(content));
		}
		//此处的MyFragmentAdapter内部给lists里的每个页面绑定了tablayout的标题
		vp.setAdapter(new ArtFragmentAdapter(getSupportFragmentManager(), lists));
		vp.setCurrentItem(Integer.parseInt(position));

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//让toolbar的返回按钮具有返回功能
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
