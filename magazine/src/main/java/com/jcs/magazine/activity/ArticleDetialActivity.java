package com.jcs.magazine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.jcs.magazine.R;
import com.jcs.magazine.base.BaseActivity;
import com.jcs.magazine.util.HtmlUtil;

import static com.jcs.magazine.R.id.webView;

/**
 * author：Jics
 * 2017/8/1 14:55
 */
public class ArticleDetialActivity extends BaseActivity {
	private TextView tv_title,tv_author;
	private WebView mWebView;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_detial);
		String content=getIntent().getStringExtra("content");
		String title=getIntent().getStringExtra("title");
		String autore=getIntent().getStringExtra("author");
		tv_title= (TextView) findViewById(R.id.tv_title);
		tv_author= (TextView) findViewById(R.id.tv_author);
		tv_title.setText(title);
		tv_author.setText("文 / "+autore);

		mWebView = (WebView) findViewById(webView);
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				//Todo 加载结束
			}
		});
		WebSettings settings = mWebView.getSettings();
		settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		mWebView.loadDataWithBaseURL("about:blank", HtmlUtil.fmt(content), "text/html", "utf-8", null);

	}
}
