package com.jcs.magazine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.jcs.magazine.R;
import com.jcs.magazine.adapter.PrefaceRvAdapter;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.ContentsBean;
import com.jcs.magazine.config.BuildConfig;
import com.squareup.picasso.Picasso;

/**
 * 卷首语
 * author：Jics
 * 2017/7/31 19:36
 */
public class PrefaceActivity extends AppCompatActivity implements PrefaceRvAdapter.OnPreItemClickListener{
	private ImageView imageView;
	private RecyclerView rv_preface;
	private BaseListTemplet<ContentsBean> contentsBeanListBeanTemplet;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preface);
		initView();
	}

	private void initView() {
		imageView = (ImageView) findViewById(R.id.iv_cover);
		rv_preface= (RecyclerView) findViewById(R.id.rv_preface);

		contentsBeanListBeanTemplet= (BaseListTemplet<ContentsBean>) getIntent().getSerializableExtra("contents");
		PrefaceRvAdapter prefaceRvAdapter = new PrefaceRvAdapter(this, contentsBeanListBeanTemplet.getResults().getBody());
		prefaceRvAdapter.setOnPreItemClickListener(this);
		rv_preface.setAdapter(prefaceRvAdapter);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		rv_preface.setLayoutManager(linearLayoutManager);
		rv_preface.setItemAnimator(new DefaultItemAnimator());

		Picasso.with(this)
				.load(getIntent().getStringExtra("img"))
				.noFade()
                .resize(BuildConfig.COVER_WIDTH, BuildConfig.COVER_Height)
                .centerCrop()
				.placeholder(R.drawable.l_content)
				.error(R.drawable.l_content)
				.into(imageView);

		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//模拟返回键，直接finish的话没有共享元素动画
				PrefaceActivity.super.onBackPressed();
			}
		});

		//----
		/*String contents = decodeContents();
		textView= (TextView) findViewById(R.id.tv_preface);
		textView.setText(contents);
		textView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(PrefaceActivity.this, ArticleActivity.class);
				Bundle bundle=new Bundle();
				bundle.putSerializable("contents",contentsBeanListBeanTemplet);
				intent.putExtras(bundle);
				startActivity(intent);

				*//*YzuClient.getInstance().getContents(1)
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
						});*//*


			}
		});*/
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

	@Override
	public void onClick(View view, int position) {
		Intent intent = new Intent(PrefaceActivity.this, ArticleActivity.class);
		Bundle bundle=new Bundle();
		bundle.putSerializable("contents",contentsBeanListBeanTemplet);
		bundle.putString("position", String.valueOf(position));
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
