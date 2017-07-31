package com.jcs.magazine.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jcs.magazine.R;
import com.jcs.magazine.adapter.MyFragmentAdapter;
import com.jcs.magazine.fragment.ArticleFragment;

import java.util.ArrayList;
import java.util.List;

public class ArticleActivity extends AppCompatActivity {
	private Toolbar tb;
	private TabLayout tlTitle;
	private ViewPager vp;
	private List<Fragment> lists;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article);
		initView();
	}

	private void initView() {
		initViewPager();
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

	private void initViewPager() {
		vp = (ViewPager) findViewById(R.id.vp);
		lists = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			lists.add(new ArticleFragment(i));
		}

		vp.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(), lists));
		vp.setCurrentItem(0);


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
