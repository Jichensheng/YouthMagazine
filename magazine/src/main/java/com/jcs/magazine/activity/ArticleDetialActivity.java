package com.jcs.magazine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jcs.magazine.R;

/**
 * author：Jics
 * 2017/8/1 14:55
 */
public class ArticleDetialActivity extends AppCompatActivity {
	private TextView textView,tv_title,tv_author;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_detial);
		String position=getIntent().getStringExtra("id");
		String title=getIntent().getStringExtra("title");
		String autore=getIntent().getStringExtra("author");
		textView= (TextView) findViewById(R.id.tv_art_content);
		tv_title= (TextView) findViewById(R.id.tv_title);
		tv_author= (TextView) findViewById(R.id.tv_author);
		tv_title.setText(title);
		tv_author.setText("文 / "+autore);
		textView.setText(position);
	}
}
