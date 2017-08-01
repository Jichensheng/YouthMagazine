package com.jcs.magazine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcs.magazine.R;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.ContentsBean;

/**
 * 卷首语
 * author：Jics
 * 2017/7/31 19:36
 */
public class PrefaceActivity extends AppCompatActivity {
	private ImageView imageView;
	private TextView textView;
	private String TAG = "jcs_net";
	private BaseListTemplet<ContentsBean> contentsBeanListBeanTemplet;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preface);
		initView();
	}

	private void initView() {
		contentsBeanListBeanTemplet= (BaseListTemplet<ContentsBean>) getIntent().getSerializableExtra("contents");
		String contents = decodeContents();
		textView= (TextView) findViewById(R.id.tv_preface);
		imageView = (ImageView) findViewById(R.id.iv_cover);
		imageView.setImageResource(R.drawable.l_content);
		textView.setText(contents);
		textView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(PrefaceActivity.this, ArticleActivity.class);
				Bundle bundle=new Bundle();
				bundle.putSerializable("contents",contentsBeanListBeanTemplet);
				intent.putExtras(bundle);
				startActivity(intent);

				/*YzuClient.getInstance().getContents(1)
						.subscribeOn(Schedulers.newThread())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new Consumer<BaseListTemplet<ContentsBean>>() {
							@Override
							public void accept(BaseListTemplet<ContentsBean> contentsBeanListBeanTemplet) throws Exception {
								Intent intent = new Intent(PrefaceActivity.this, ArticleActivity.class);
								Bundle bundle=new Bundle();
								bundle.putSerializable("contents",contentsBeanListBeanTemplet);
								intent.putExtras(bundle);
								startActivity(intent);
							}
						}, new Consumer<Throwable>() {
							@Override
							public void accept(Throwable throwable) throws Exception {
								UiUtil.toast("网络回调错误："+throwable.toString());
							}
						});*/


			}
		});
	}

	@NonNull
	private String decodeContents() {
		String contents="";
		for (ContentsBean contentsBean : contentsBeanListBeanTemplet.getResults().getBody()) {
			contents+="\n  "+contentsBean.getName();
			for (ContentsBean.ArticlesBean articlesBean : contentsBean.getArticles()) {
				contents+="\n  "+articlesBean.getTitle();
			}
		}
		return contents;
	}
}
