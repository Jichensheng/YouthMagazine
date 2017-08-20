package com.jcs.magazine.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.jcs.magazine.R;
import com.jcs.magazine.adapter.ArtFragmentAdapter;
import com.jcs.magazine.base.BaseActivity;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.ContentsBean;
import com.jcs.magazine.fragment.ArticleFragment;

import java.util.ArrayList;
import java.util.List;

public class ArticleActivity extends BaseActivity {
	private Toolbar tb;
	private TabLayout tlTitle;
	private ViewPager vp;
	private List<ArticleFragment> lists;
	private String TAG = "ArticleActivity";
	private List<ContentsBean> contents;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article);
		initView();
	}

	private void initView() {

		BaseListTemplet<ContentsBean> contentsBeanListBeanTemplet = (BaseListTemplet<ContentsBean>) getIntent().getSerializableExtra("contents");
		Log.e(TAG, "accept: " + contentsBeanListBeanTemplet.getMsg()
				+ "\n目录列表级别：" + contentsBeanListBeanTemplet.getResults().toString()
				+ "\n单个目录下的文章列表级别" + contentsBeanListBeanTemplet.getResults().getBody().get(0).toString()
				+ "\n单个目录下的文章列表下的单篇文章级别" + contentsBeanListBeanTemplet.getResults().getBody().get(0).getArticles().get(0).toString()
		);
		//所有目录
		contents = contentsBeanListBeanTemplet.getResults().getBody();

		String position = getIntent().getStringExtra("position");

		initViewPager(position);
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

	private void initViewPager(String position) {
		lists = new ArrayList<>();
		for (ContentsBean content : contents) {
			ArticleFragment articleFragment = new ArticleFragment(content);
			lists.add(articleFragment);
		}
		vp = (ViewPager) findViewById(R.id.vp);
		//此处的MyFragmentAdapter内部给lists里的每个页面绑定了tablayout的标题
		vp.setAdapter(new ArtFragmentAdapter(getSupportFragmentManager(), lists));
		//position代表第几章
		vp.setCurrentItem(Integer.parseInt(position));

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.main_menu, menu);
		for (int i = 0; i < contents.size(); i++) {
			menu.add(1, i, 1, contents.get(i).getName());
		}
		/*menu.add(1, 100, 1, "获取缓存大小");//动态添加一个按钮；
		menu.add(1, 101, 1, "删除所有缓存");//注意：第二个参数是Item的ID值；
		menu.add(1, 102, 1, "菜单三");
		menu.add(1, 103, 1, "菜单四");
		menu.add(1, 104, 1, "菜单五");*/
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		} else
			vp.setCurrentItem(item.getItemId());
		/*//让toolbar的返回按钮具有返回功能
			switch (item.getItemId()) {
				case android.R.id.home:

				case 100:
					UiUtil.toast();
					break;
				case 101:LocalFileManager.getInstance().getCacheSize()
					LocalFileManager.getInstance().cleanCache();
					break;
		}*/
		return super.onOptionsItemSelected(item);
	}

}
