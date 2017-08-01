package com.jcs.magazine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jcs.magazine.R;

import static com.jcs.magazine.R.id.tv_art_content;

/**
 * author：Jics
 * 2017/8/1 14:55
 */
public class ArticleDetialActivity extends AppCompatActivity {
	private TextView textView;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_detial);
		String position=getIntent().getStringExtra("id");
		textView= (TextView) findViewById(tv_art_content);
		textView.setText(position);
	}
}
