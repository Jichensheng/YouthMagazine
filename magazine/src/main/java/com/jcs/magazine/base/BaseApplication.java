package com.jcs.magazine.base;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.jcs.magazine.config.BuildConfig;
import com.jcs.magazine.crash.CrashHandler;
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
		initCrash();
		initImageLoader();
        initApplication();
	}
	public static BaseApplication getInstance(){
		return instance;
	}
	private void initImageLoader() {
		ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
		ImageLoader.getInstance().init(configuration);
	}

	private void initCrash() {
		CrashHandler.getInstance().setCustomCrashHanler(getApplicationContext());
	}
    private void initApplication() {
        try {
            PackageInfo pkgInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            BuildConfig.setLocalVersionCode(pkgInfo.versionCode);
            BuildConfig.setServerVersionCode(pkgInfo.versionCode);// 初始化为与本地版本号一致
            BuildConfig.setLocalVersionName(pkgInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

}
