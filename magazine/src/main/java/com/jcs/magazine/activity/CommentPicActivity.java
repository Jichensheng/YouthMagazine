package com.jcs.magazine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.jcs.magazine.R;
import com.jcs.magazine.adapter.CommentPicAdapter;
import com.jcs.magazine.base.BaseActivity;

import java.util.List;

/**
 * author：Jics
 * 2017/4/10 16:24
 */
public class CommentPicActivity extends BaseActivity implements CommentPicAdapter.OnPicClickListener {
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
		CommentPicAdapter adapter = new CommentPicAdapter(this, mUrls);
		adapter.setOnPicClickListener(this);
		viewPager.setAdapter(adapter);
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

	@Override
	public void onPicClick() {
		finish();
	}
}
