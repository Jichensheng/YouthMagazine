package com.jcs.magazine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.jcs.magazine.R;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.ContentsBean;
import com.jcs.magazine.network.YzuClient;
import com.jcs.magazine.util.UiUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 卷首语
 * author：Jics
 * 2017/7/31 19:36
 */
public class PrefaceActivity extends AppCompatActivity {
	private ImageView imageView;
	private String TAG = "jcs_net";

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preface);
		imageView = (ImageView) findViewById(R.id.iv_cover);
		imageView.setImageResource(R.drawable.l_content);
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				YzuClient.getInstance().getContents(1)
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
						});


			}
		});
	}
}
