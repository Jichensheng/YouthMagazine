package com.jcs.magazine.share;

import android.util.Log;

import com.jcs.magazine.util.UiUtil;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * 自定义分享毁掉监听器
 */
public class CustomShareListener implements UMShareListener {

	@Override
	public void onStart(SHARE_MEDIA platform) {

	}

	@Override
	public void onResult(SHARE_MEDIA platform) {

		if (platform.name().equals("WEIXIN_FAVORITE")) {
			UiUtil.toast(platform + " 收藏成功啦");
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

				UiUtil.toast(platform + " 分享成功啦");
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
			UiUtil.toast(platform + " 分享失败啦");
			if (t != null) {
				Log.d("throw", "throw:" + t.getMessage());
			}
		}

	}

	@Override
	public void onCancel(SHARE_MEDIA platform) {

		UiUtil.toast(platform + " 分享取消了");
	}
}