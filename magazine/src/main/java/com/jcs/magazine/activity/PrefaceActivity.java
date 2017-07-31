package com.jcs.magazine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.jcs.magazine.R;

/**
 * 卷首语
 * author：Jics
 * 2017/7/31 19:36
 */
public class PrefaceActivity extends AppCompatActivity {
	private ImageView imageView;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preface);
		imageView= (ImageView) findViewById(R.id.iv_cover);
		imageView.setImageResource(R.drawable.l_content);
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(PrefaceActivity.this, ArticleActivity.class));
			}
		});
	}
}
