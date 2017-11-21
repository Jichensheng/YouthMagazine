package com.jcs.magazine.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.jcs.magazine.R;
import com.jcs.magazine.base.BaseActivity;
import com.jcs.magazine.util.DialogHelper;
import com.jcs.magazine.util.HtmlUtil;

import static com.jcs.magazine.R.id.webView;


/**
 * author：Jics
 * 2017/8/1 14:55
 */
@Deprecated
public class ArticleDetialActivity extends BaseActivity {
	private int timeOut = 4 * 1000;
	private TextView tv_title, tv_author;
	private WebView mWebView;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_detial);
		String content = getIntent().getStringExtra("content");
		String title = getIntent().getStringExtra("title");
		String autore = getIntent().getStringExtra("author");
		Toolbar toolbar= (Toolbar) findViewById(R.id.tb_toolbar);
		setSupportActionBar(toolbar);
//		TextView textView= (TextView) findViewById(R.id.tv_tb_title);
//		textView.setText(title);

		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_author = (TextView) findViewById(R.id.tv_author);
		tv_title.setText(title);
		tv_author.setText("文 / " + autore);

		mWebView = (WebView) findViewById(webView);
		final AlertDialog loading = new DialogHelper(ArticleDetialActivity.this).show(R.layout.loading);
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				new CountDownTimer(timeOut, 1000) {
					@Override
					public void onTick(long millisUntilFinished) {
					}

					@Override
					public void onFinish() {
						if (loading.isShowing()) {
							loading.dismiss();
						}
					}
				}.start();// 开始计时

			}


			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				//Todo 加载结束
				loading.dismiss();
			}

			@Override
			public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
				super.onReceivedError(view, request, error);
				loading.dismiss();
			}
		});
		WebSettings settings = mWebView.getSettings();
		settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		settings.setJavaScriptEnabled(true);
//		mWebView.loadUrl("https://mp.weixin.qq.com/s?__biz=MzA4NTM3MTA2NQ==&mid=2651391185&idx=1&sn=ea61b912beff537048762bb5a5be821a");
		mWebView.loadDataWithBaseURL("about:blank", HtmlUtil.fmt(content), "text/html", "utf-8", null);
		initComment();
	}

	/**
	 * 评论
	 */
	private void initComment() {

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case android.R.id.home:
				finish();
				break;
		}
		return true;
	}
}
