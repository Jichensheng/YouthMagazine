package com.jcs.magazine.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jcs.magazine.R;
import com.jcs.magazine.adapter.PrefaceRvAdapter;
import com.jcs.magazine.base.BaseActivity;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.ContentsBean;
import com.jcs.magazine.config.BuildConfig;
import com.jcs.magazine.share.CustomShareListener;
import com.jcs.magazine.util.FileUtil;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.io.File;

/**
 * 卷首语
 * author：Jics
 * 2017/7/31 19:36
 */
public class PrefaceActivity extends BaseActivity implements PrefaceRvAdapter.OnPreItemClickListener, View.OnClickListener {
	private ImageView imageView;
	private RecyclerView rv_preface;
	private Button btn_share;
	private BaseListTemplet<ContentsBean> contentsBeanListBeanTemplet;
	private ShareAction mShareAction;
	private UMShareListener mShareListener;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preface);
		initView();
		initShare();
	}


	private void initView() {
		imageView = (ImageView) findViewById(R.id.iv_cover);
		rv_preface = (RecyclerView) findViewById(R.id.rv_preface);
		btn_share = (Button) findViewById(R.id.btn_share);
		btn_share.setOnClickListener(this);

		contentsBeanListBeanTemplet = (BaseListTemplet<ContentsBean>) getIntent().getSerializableExtra("contents");
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
				.resize(BuildConfig.COVER_WIDTH, BuildConfig.COVER_HEIGHT)
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

	}

	private void initShare() {
		mShareListener = new CustomShareListener();
		/*增加自定义按钮的分享面板*/
		mShareAction = new ShareAction(PrefaceActivity.this)
				//各大平台按钮
				.setDisplayList(
						SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
						SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
				//分享面板点击事件
				.setShareboardclickCallback(new ShareBoardlistener() {
					@Override
					public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
						UMImage image = new UMImage(PrefaceActivity.this, new File(FileUtil.getProjectRootFile(), FileUtil.DEFAULT_PIC_SHAER_NAME));
						UMWeb web = new UMWeb("http://weibo.com/yangdaqingnian");
						web.setTitle("《扬大青年》");
						web.setDescription("欢迎下载App查看精彩文章");
						web.setThumb(new UMImage(PrefaceActivity.this, R.drawable.ic_launcher));

						new ShareAction(PrefaceActivity.this).withMedia(image)
								.setPlatform(share_media)
								.setCallback(mShareListener)
								.share();
					}
				});
	}

	@Override
	public void onClick(View view, int position) {
		Intent intent = new Intent(PrefaceActivity.this, ArticleActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("contents", contentsBeanListBeanTemplet);
		bundle.putString("position", String.valueOf(position));
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_share:
				//中部弹出分享页
				ShareBoardConfig config = new ShareBoardConfig();
				config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
				config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR); // 圆角背景
				config.setShareboardBackgroundColor(Color.WHITE);
				config.setTitleVisibility(false);
				config.setIndicatorVisibility(false);
				config.setCancelButtonTextColor(ContextCompat.getColor(this, R.color.btn_red));
				//构造函数open（）是默认下边弹出分享页，带config参数的可以控制位置
				mShareAction.open(config);
				break;
		}
	}

	/**
	 * QQ与新浪不需要添加Activity，但需要在使用QQ分享或者授权的Activity中，添加：
	 *
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** attention to this below ,must add this**/
		UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
	}
}
