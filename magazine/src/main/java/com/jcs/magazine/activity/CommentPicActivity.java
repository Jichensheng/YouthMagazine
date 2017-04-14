package com.jcs.magazine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


import com.jcs.magazine.adapter.CommentPicAdapter;
import com.githang.navigatordemo.R;

import java.util.List;

/**
 * author：Jics
 * 2017/4/10 16:24
 */
public class CommentPicActivity extends AppCompatActivity {
	private ViewPager viewPager;
	private List<String> mUrls;
	private int index;
	private TextView textView;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment_pic);
		initView();
	}
	private void initView( ){
		mUrls= (List<String>) getIntent().getSerializableExtra("urls");
		index=getIntent().getIntExtra("index",0);
		textView= (TextView) findViewById(R.id.tv_index);
		textView.setText(index+1+"/"+mUrls.size());

		viewPager= (ViewPager) findViewById(R.id.vp_comment_pic);
		viewPager.setAdapter(new CommentPicAdapter(this,mUrls));
		viewPager.setCurrentItem(index);
		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				textView.setText(position+1+"/"+mUrls.size());
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		finish();
	}
}
