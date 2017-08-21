package com.jcs.magazine.share;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.jcs.topsnackbar.Prompt;
import com.jcs.topsnackbar.TSnackbar;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * 自定义分享毁掉监听器
 */
public class CustomShareListener implements UMShareListener {
	private View view;

	public CustomShareListener(Activity activity) {
		this.view =  activity.findViewById(android.R.id.content).getRootView();
	}

	@Override
	public void onStart(SHARE_MEDIA platform) {

	}

	@Override
	public void onResult(SHARE_MEDIA platform) {

		if (platform.name().equals("WEIXIN_FAVORITE")) {
//			UiUtil.toast(platform + " 收藏成功啦");
			showSnack(platform + " 收藏成功啦", true);
		} else {
			if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
					&& platform != SHARE_MEDIA.EMAIL
					&& platform != SHARE_MEDIA.FLICKR
					&& platform != SHARE_MEDIA.FOURSQUARE
					&& platform != SHARE_MEDIA.TUMBLR
					&& platform != SHARE_MEDIA.POCKET
					&& platform != SHARE_MEDIA.PINTEREST

					&& platform != SHARE_MEDIA.INSTAGRAM
					&& platform != SHARE_MEDIA.GOOGLEPLUS
					&& platform != SHARE_MEDIA.YNOTE
					&& platform != SHARE_MEDIA.EVERNOTE) {

//				UiUtil.toast(platform + " 分享成功啦");
				showSnack(platform + " 分享成功啦", true);
			}

		}
	}

	@Override
	public void onError(SHARE_MEDIA platform, Throwable t) {
		if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
				&& platform != SHARE_MEDIA.EMAIL
				&& platform != SHARE_MEDIA.FLICKR
				&& platform != SHARE_MEDIA.FOURSQUARE
				&& platform != SHARE_MEDIA.TUMBLR
				&& platform != SHARE_MEDIA.POCKET
				&& platform != SHARE_MEDIA.PINTEREST

				&& platform != SHARE_MEDIA.INSTAGRAM
				&& platform != SHARE_MEDIA.GOOGLEPLUS
				&& platform != SHARE_MEDIA.YNOTE
				&& platform != SHARE_MEDIA.EVERNOTE) {
//			UiUtil.toast(platform + " 分享失败啦");
			showSnack(platform + " 分享失败啦", false);
			if (t != null) {
				Log.d("throw", "throw:" + t.getMessage());
			}
		}

	}

	@Override
	public void onCancel(SHARE_MEDIA platform) {

//		UiUtil.toast(platform + " 分享取消了");
		showSnack(platform + " 分享取消了", false);

	}

	private void showSnack(String info, boolean isSucc) {
		TSnackbar snackBar = TSnackbar.make(view, info, TSnackbar.LENGTH_SHORT, TSnackbar.APPEAR_FROM_TOP_TO_DOWN);
/*		snackBar.setAction("取消", new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});*/
		if (isSucc) {
			snackBar.setPromptThemBackground(Prompt.SUCCESS);

		} else
			snackBar.setPromptThemBackground(Prompt.WARNING);
		snackBar.show();
	}
}