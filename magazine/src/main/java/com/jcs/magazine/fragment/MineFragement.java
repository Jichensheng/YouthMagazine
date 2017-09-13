package com.jcs.magazine.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.jcs.magazine.R;
import com.jcs.magazine.activity.mine.CollectionActivity;
import com.jcs.magazine.activity.mine.ContactUsActivity;
import com.jcs.magazine.activity.mine.PartnerActivity;
import com.jcs.magazine.activity.mine.PostActivity;
import com.jcs.magazine.activity.mine.UserInfoActivity;
import com.jcs.magazine.base.BaseFragment;
import com.jcs.magazine.global.LoginUserHelper;
import com.jcs.magazine.global.PermissionHelper;
import com.jcs.magazine.share.CustomShareListener;
import com.jcs.magazine.util.DialogHelper;
import com.jcs.magazine.util.LocalFileManager;
import com.jcs.magazine.util.MessageEvent;
import com.jcs.magazine.util.UiUtil;
import com.jcs.magazine.util.glide.GlideBlurTransform;
import com.jcs.magazine.widget.CircleImageView;
import com.jcs.magazine.widget.IconFontTabView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * author：Jics
 * 2017/8/21 09:16
 */
public class MineFragement extends BaseFragment implements View.OnClickListener {
	private View rootView;
	private SuperTextView stv_catch;
	private CircleImageView civ_avater;
	private ImageView blurImageView;
	private TextView tv_nick,tv_complete;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = LayoutInflater.from(getContext()).inflate(R.layout.main_fragment_mine, container, false);
			initview(rootView);
		} else {
			ViewGroup viewGroup = (ViewGroup) rootView.getParent();
			if (viewGroup != null) {
				viewGroup.removeView(rootView);
			}
		}

		return rootView;
	}

	private void initview(View containor) {
		civ_avater = (CircleImageView) containor.findViewById(R.id.civ_avater);
//		civ_avater.setImageResource(R.drawable.hmm);
		blurImageView = (ImageView) containor.findViewById(R.id.iv_img);
		tv_nick= (TextView) containor.findViewById(R.id.tv_inck_name);
		tv_complete= (TextView) containor.findViewById(R.id.tv_complete);

		String url = "";
		if (LoginUserHelper.getInstance().isLogined()) {
			url=LoginUserHelper.getInstance().getUser().getHead();
			tv_nick.setText(LoginUserHelper.getInstance().getUser().getNick());
		}
//		Picasso.with(getContext()).load(url).error(R.drawable.default_avater).transform(new BlurTransform()).into(blurImageView);
		Glide.with(getContext()).load(url).error(R.drawable.default_avater_blur).transform(new GlideBlurTransform(getContext())).into(blurImageView);
//		Picasso.with(getContext()).load(url).error(R.drawable.default_avater).into(civ_avater);
		Glide.with(getContext()).load(url).error(R.drawable.default_avater).into(civ_avater);


		//开关通知
		SuperTextView superTextView = (SuperTextView) containor.findViewById(R.id.stv_notation);
		superTextView.setSwitchCheckedChangeListener(new SuperTextView.OnSwitchCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				UiUtil.toast(String.valueOf(b));
			}
		});
		//清除缓存
		stv_catch = (SuperTextView) containor.findViewById(R.id.stv_cache);
		stv_catch.setRightString(LocalFileManager.getInstance().getCacheSize());
		stv_catch.setOnClickListener(this);
		//分享
		SuperTextView stv_share = (SuperTextView) containor.findViewById(R.id.stv_share);
		stv_share.setOnClickListener(this);

		SuperTextView stv_user_info = (SuperTextView) containor.findViewById(R.id.stv_user_info);
		stv_user_info.setOnClickListener(this);

		SuperTextView stv_contract_us = (SuperTextView) containor.findViewById(R.id.stv_contract_us);
		stv_contract_us.setOnClickListener(this);

		IconFontTabView iftv_post = (IconFontTabView) containor.findViewById(R.id.iftv_post);
		iftv_post.setOnClickListener(this);

		IconFontTabView iftv_collect = (IconFontTabView) containor.findViewById(R.id.iftv_collect);
		iftv_collect.setOnClickListener(this);

		IconFontTabView iftv_partner = (IconFontTabView) containor.findViewById(R.id.iftv_partner);
		iftv_partner.setOnClickListener(this);

		civ_avater.setOnClickListener(this);
		tv_complete.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
			//个人信息
			case R.id.stv_user_info:
				intent = new Intent(getContext(), UserInfoActivity.class);
				PermissionHelper.getHelper().startActivity(getContext(), intent);
				break;
			//联系我们
			case R.id.stv_contract_us:
				intent = new Intent(getContext(), ContactUsActivity.class);
				PermissionHelper.getHelper().startActivity(getContext(), intent);
				break;
			//我的帖子
			case R.id.iftv_post:
				intent = new Intent(getContext(), PostActivity.class);
				PermissionHelper.getHelper().startActivity(getContext(), intent);
				break;
			//收藏夹
			case R.id.iftv_collect:
				intent = new Intent(getContext(), CollectionActivity.class);
				PermissionHelper.getHelper().startActivity(getContext(), intent);
				break;
			//伙伴
			case R.id.iftv_partner:
				intent = new Intent(getContext(), PartnerActivity.class);
				PermissionHelper.getHelper().startActivity(getContext(), intent);
				break;
			//清缓存
			case R.id.stv_cache:
				new DialogHelper(getContext()).show(new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						LocalFileManager.getInstance().cleanCache();

						stv_catch.setRightString(LocalFileManager.getInstance().getCacheSize());
					}
				}, true, 0, 0, "清除缓存", "确认清除缓存", true);
				break;
			//分享
			case R.id.stv_share:
				umShare();
				break;
			//头像
			case R.id.civ_avater:
				intent = new Intent(getContext(), UserInfoActivity.class);
				PermissionHelper.getHelper().startActivity(getContext(), intent);
				break;
			//资料完善度
			case R.id.tv_complete:
				intent = new Intent(getContext(), UserInfoActivity.class);
				PermissionHelper.getHelper().startActivity(getContext(), intent);
				break;

		}
	}

	private void umShare() {
		final CustomShareListener shareListener = new CustomShareListener(getActivity());
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

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void refreshInfo(MessageEvent messageEvent){
		if (messageEvent.getMessage().equals("success")) {
			String url = "";
			if (LoginUserHelper.getInstance().isLogined()) {
				url=LoginUserHelper.getInstance().getUser().getHead();
				tv_nick.setText(LoginUserHelper.getInstance().getUser().getNick());
			}
			Glide.with(getContext()).load(url).error(R.drawable.default_avater_blur).transform(new GlideBlurTransform(getContext())).into(blurImageView);
			Glide.with(getContext()).load(url).error(R.drawable.default_avater).into(civ_avater);
		}

	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
