package com.jcs.magazine.base;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * author：Jics
 * 2016/6/23 14:29
 */
public class BaseApplication extends Application {
	private static BaseApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance=this;
		initImageLoader();
	}
	public static BaseApplication getInstance(){
		return instance;
	}
	private void initImageLoader() {
		ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
		ImageLoader.getInstance().init(configuration);

	}
}
