package com.jcs.magazine.fragment;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.allen.library.SuperTextView;
import com.jcs.magazine.R;
import com.jcs.magazine.base.BaseFragment;
import com.jcs.magazine.share.CustomShareListener;
import com.jcs.magazine.util.DialogHelper;
import com.jcs.magazine.util.LocalFileManager;
import com.jcs.magazine.util.UiUtil;
import com.jcs.magazine.util.picasso.BlurTransform;
import com.jcs.magazine.widget.Bitmaptest;
import com.jcs.magazine.widget.CircleImageView;
import com.jcs.magazine.widget.FastBlur;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

/**
 * author：Jics
 * 2017/8/21 09:16
 */
public class MineFragement extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.main_fragment_mine, container, false);

		initview(view);
		return view;
	}

	private void initview(View containor) {
		CircleImageView civ_avater= (CircleImageView) containor.findViewById(R.id.civ_avater);
//		civ_avater.setImageResource(R.drawable.hmm);
		final ImageView blurImageView = (ImageView) containor.findViewById(R.id.iv_img);
		String url="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503316211276&di=12a0c990c0d6eea75c36c27db2f2aac1&imgtype=0&src=http%3A%2F%2Fpic.ffpic.com%2Ffiles%2F2015%2F0117%2F0117fbbjmjzsjbzdq10.jpg";
		Picasso.with(getContext()).load(url).transform(new BlurTransform()).into(blurImageView);
		Picasso.with(getContext()).load(url).into(civ_avater);

		//开关通知
		SuperTextView superTextView= (SuperTextView) containor.findViewById(R.id.stv_notation);
		superTextView.setSwitchCheckedChangeListener(new SuperTextView.OnSwitchCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				UiUtil.toast(String.valueOf(b));
			}
		});
		//清除缓存
		final SuperTextView stv_catch=(SuperTextView) containor.findViewById(R.id.stv_cache);
		stv_catch.setRightString(LocalFileManager.getInstance().getCacheSize());
		stv_catch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				new DialogHelper(getContext()).show(new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						LocalFileManager.getInstance().cleanCache();

						stv_catch.setRightString(LocalFileManager.getInstance().getCacheSize());
					}
				},true,0,0,"清除缓存","确认清除缓存",true);
			}
		});
		//分享
		SuperTextView stv_share=(SuperTextView)containor.findViewById(R.id.stv_share);
		stv_share.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final CustomShareListener	shareListener = new CustomShareListener(getActivity());
		/*增加自定义按钮的分享面板*/
				ShareAction shareAction = new ShareAction(getActivity())
						//各大平台按钮
						.setDisplayList(
								SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
								SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
						//分享面板点击事件
						.setShareboardclickCallback(new ShareBoardlistener() {
							@Override
							public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
								UMWeb web = new UMWeb("http://weibo.com/yangdaqingnian");
								web.setTitle("《扬大青年》");
								web.setDescription("欢迎下载App查看精彩文章");
								web.setThumb(new UMImage(getContext(), R.drawable.ic_share));

								new ShareAction(getActivity()).withMedia(web)
										.setPlatform(share_media)
										.setCallback(shareListener)
										.share();
							}
						});
				//中部弹出分享页
				ShareBoardConfig config = new ShareBoardConfig();
				config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
				config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR); // 圆角背景
				config.setShareboardBackgroundColor(Color.WHITE);
				config.setTitleVisibility(false);
				config.setIndicatorVisibility(false);
				config.setCancelButtonTextColor(ContextCompat.getColor(getContext(), R.color.btn_red));
				//构造函数open（）是默认下边弹出分享页，带config参数的可以控制位置
				shareAction.open(config);
			}
		});
	}


	private void blur(Bitmap bkg, ImageView view) {
		float radius = 20;
		view.setImageBitmap(FastBlur.doBlur(Bitmaptest.fitBitmap(bkg, 200), (int) radius, true));
	}
}
