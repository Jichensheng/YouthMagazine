package com.jcs.magazine.config;

import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * author：Jics
 * 2017/7/31 14:33
 */
public class BuildConfig {
	public static final long DEAD_TIME=1*60*60*1000;
	public static final Boolean DEBUG = true;
	/**
	 * 封面默认宽高px
	 */
	public static final int COVER_WIDTH = 503;
	public static final int COVER_HEIGHT = 593;
	/**
	 * 文章预览图默认款高px
	 */
	public static final int COVER_WIDTH_ARTICLE = 1080;
	public static final int COVER_HEIGHT_ARTICLE = 515;

	/**
	 * 应用名称
	 */
	public final String APP_NAME = "YzuMagazine";


	/**
	 * 应用缓存根目录
	 */
	public static final String APP_ROOT_DIR = ".YzuMagazine";

	/**
	 * 应用缓存根目录
	 */
	public static final String APP_CACHE_DIR = "cache";

	/**
	 * 应用数据目录
	 */
	public static final String APP_DATA_DIR = "data";

	/**
	 * 临时文件夹
	 */
	public static final Object APP_TEMP_DIR = "temp";
	/**
	 * 图片缓存目录
	 */
	public static final String IMAGE_CACHE_DIR = "image";

	/**
	 * 系统日志目录
	 */
	public static final String APP_LOG_DIR = "log";

	/**
	 * 系统崩溃日志目录
	 */
	public static final String APP_LOG_CRASH_DIR = "crash";

	/**
	 * 下载目录
	 */
	public static final String APP_DOWNLOAD_DIR = "download";

	/**
	 * 应用列表下载时的存放目录
	 */
	public static final String APP_DOWNLOAD_SUB_DIR = "apps";

	public static int THREAD_COUNT = 3;

	public static final String RETROFIT_CACHE_DIR = "retrofit_cache";

	public static final int RETROFIT_CACHE_SIZE = 10 * 1024 * 1024;
	/**
	 * 缓存时间
	 */
	public static final int CATCH_MAXAGE = 60 * 60; // 有网络时 设置缓存超时时间1个小时
	public static final int CATCH_MAXSTALE = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
	public static final int DEFAULT_TIMEOUT = 5 * 1000;


	// 客户端变量
	private static int serverVersionCode = 1;
	private static String serverVersionName;
	private static int localVersionCode = 1;
	private static String localVersionName;
	private static String serverApkDownloadUrl;
	private static String serverApkSize;
	private static String deviceName;
	private static String deviceMac;
	private static long orgId = 50;

	public static int getLocalVersionCode() {
		return localVersionCode;
	}

	public static void setLocalVersionCode(int localVersionCode) {
		BuildConfig.localVersionCode = localVersionCode;
	}

	public static int getServerVersionCode() {
		return serverVersionCode;
	}

	public static void setServerVersionCode(int serverVersionCode) {
		BuildConfig.serverVersionCode = serverVersionCode;
	}

	public static String getServerVersionName() {
		return serverVersionName;
	}

	public static void setServerVersionName(String serverVersionName) {
		BuildConfig.serverVersionName = serverVersionName;
	}

	public static String getLocalVersionName() {
		return localVersionName;
	}

	public static void setLocalVersionName(String localVersionName) {
		BuildConfig.localVersionName = localVersionName;
	}

	public static String getServerApkDownloadUrl() {
		return serverApkDownloadUrl;
	}

	public static void setServerApkDownloadUrl(String serverApkDownloadUrl) {
		BuildConfig.serverApkDownloadUrl = serverApkDownloadUrl;
	}

	public static String getServerApkSize() {
		return serverApkSize;
	}

	public static void setServerApkSize(String serverApkSize) {
		BuildConfig.serverApkSize = serverApkSize;
	}

	public static String getDeviceName() {
		return deviceName;
	}

	public static void setDeviceName(String deviceName) {
		BuildConfig.deviceName = deviceName;
	}

	public static String getDeviceMac() {
		return deviceMac;
	}

	public static void setDeviceMac(String deviceMac) {
		BuildConfig.deviceMac = deviceMac;
	}

	/**
	 * 获取系统异常退出日志名称
	 *
	 * @return String生成唯一日志名称
	 */
	public static String getCrashLogName() {
		return String.format("%1$s_%2$s_%3$d.log", getDeviceName(),
				new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), ((int) (Math.random() * 1000)) + 1000);
	}

	/**
	 * 获取cpu核心数
	 *
	 * @return
	 */
	public static int getNumCores() {
		class CpuFilter implements FileFilter {
			@Override
			public boolean accept(File pathname) {
				if (Pattern.matches("cpu[0-9]", pathname.getName())) {
					return true;
				}
				return false;
			}
		}

		try {
			File dir = new File("/sys/devices/system/cpu/");
			File[] files = dir.listFiles(new CpuFilter());
			Log.d("OnlineStudy", "CPU Count: " + files.length);
			return files.length;
		} catch (Exception e) {
			Log.d("OnlineStudy", "CPU Count: Failed.");
			e.printStackTrace();
			return 1;
		}
	}

	public static long getOrgId() {
		return orgId;
	}

	public static void setOrgId(long orgId) {
		BuildConfig.orgId = orgId;
	}
}
