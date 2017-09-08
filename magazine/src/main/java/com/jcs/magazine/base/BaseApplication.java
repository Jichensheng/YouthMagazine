package com.jcs.magazine.base;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.jcs.magazine.R;
import com.jcs.magazine.activity.MainActivity;
import com.jcs.magazine.config.BuildConfig;
import com.jcs.magazine.crash.CrashHandler;
import com.jcs.magazine.util.LocalFileManager;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

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
        initApplication();
		initUmengShare();
		initBugly();
//		Picasso picasso = new Picasso.Builder(this).downloader(new OkHttp3Downloader(LocalFileManager.getInstance().getCacheDir("picasso-cache"))).build();
//		Picasso.setSingletonInstance(picasso);
	}



	private void initBugly() {
		Beta.smallIconId = R.drawable.download_small;
		Beta.upgradeDialogLayoutId = R.layout.upgrade_layout;
		Beta.storageDir = LocalFileManager.getInstance().getAppDownloadDir();
		Beta.canShowUpgradeActs.add(MainActivity.class);
		Beta.defaultBannerId=R.drawable.rocket_bg;
		Bugly.init(getApplicationContext(), "0557130f5a", false);
		CrashReport.initCrashReport(getApplicationContext());
	}


	public static BaseApplication getInstance(){
		return instance;
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

	{
		//Umeng-share
		//这里是appid和secret，签名是用软件计算出来的
		PlatformConfig.setWeixin("wx65b8cefeabe59828", "60ab003d09baa17440a92a13ce54f3f4");
		PlatformConfig.setQQZone("1105308430", "syUyuOHhdceCH4lj");
		PlatformConfig.setSinaWeibo("3510796324", "0f7fc6202df0627623b1889a32accdc4", "http://sns.whalecloud.com");
	}
	private void initUmengShare() {
		//开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
		Config.DEBUG = true;
		QueuedWork.isUseThreadPool = false;
		UMShareAPI.get(this);
	}

}
