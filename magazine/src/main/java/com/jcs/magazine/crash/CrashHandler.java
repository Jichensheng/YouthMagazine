package com.jcs.magazine.crash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.DisplayMetrics;

import com.jcs.magazine.util.LocalFileManager;
import com.jcs.magazine.util.UiUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
	private static final String CRASHFILENAME = "error.log";
	private static CrashHandler instance = new CrashHandler();
	private CrashLog crashLog = null;
	private Context mContext;

	public static CrashHandler getInstance() {
		return instance;
	}

	/**
	 * 保存错误信息到文件中
	 *
	 * @param ex Throwable 异常
	 * @return 返回文件名称, 便于将文件传送到服务器
	 */
	private String saveCrashLog2File(Throwable ex) {
		StringBuilder sb = new StringBuilder();
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		sb.append(writer.toString());
		if (crashLog != null) {
			crashLog.setError(sb.toString());
		}
		OutputStreamWriter oStreamWriter = null;
		try {
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				File dir = LocalFileManager.getInstance().getCrashLogDir();
				File file = new File(dir.getPath() + "/" + CRASHFILENAME);
				if (!file.exists()) {
					file.createNewFile();
				}
				oStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
				if (null != crashLog)
					oStreamWriter.append(crashLog.toString());
			}
			return CRASHFILENAME;
		} catch (Exception e) {
		} finally {
			try {
				if (oStreamWriter != null) {
					oStreamWriter.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}


	private void showToast(final Context paramContext, final String paramString) {
		new Thread(new Runnable() {
			public void run() {
				Looper.prepare();
				UiUtil.toast(paramString);
				Looper.loop();
			}
		}).start();
	}

	private void uploadLog(Throwable paramThrowable) {
		StringBuilder localStringBuilder = new StringBuilder();
		StringWriter localStringWriter = new StringWriter();
		PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
		paramThrowable.printStackTrace(localPrintWriter);
		for (paramThrowable = paramThrowable.getCause(); paramThrowable != null; paramThrowable = paramThrowable.getCause())
			paramThrowable.printStackTrace(localPrintWriter);
		localPrintWriter.close();
		localStringBuilder.append(localStringWriter.toString());
		if (this.crashLog != null)
			this.crashLog.setError(localStringBuilder.toString());
		//此处将crashLog封装成json字符串，post出去即可
	}

	/**
	 * 收集设备参数信息
	 *
	 * @param context 上下文
	 */
	@SuppressLint("SimpleDateFormat")
	public void collectDeviceInfo(Context context) {
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null" : pi.versionName;
				String versionCode = pi.versionCode + "";
				crashLog.setVersionName(versionName);
				crashLog.setVersionCode(versionCode);
			}
		} catch (PackageManager.NameNotFoundException e) {
		}
		DisplayMetrics dm ;
		dm = context.getResources().getDisplayMetrics();
		Field[] fields = Build.class.getDeclaredFields();
		crashLog.setWidth(String.valueOf(dm.widthPixels));
		crashLog.setHeight(String.valueOf(dm.heightPixels));
		crashLog.setDpi(String.valueOf(dm.densityDpi));
		crashLog.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				if (field.getName().equalsIgnoreCase("id")) {
				} else if (field.getName().equalsIgnoreCase("display")) {
					crashLog.setDisplay(field.get(null).toString());
				} else if (field.getName().equalsIgnoreCase("product")) {
					crashLog.setProduct(field.get(null).toString());
				} else if (field.getName().equalsIgnoreCase("device")) {
					crashLog.setDevice(field.get(null).toString());
				} else if (field.getName().equalsIgnoreCase("board")) {
					crashLog.setBoard(field.get(null).toString());
				} else if (field.getName().equalsIgnoreCase("cpu_abi")) {
					crashLog.setCpuAbi(field.get(null).toString());
				} else if (field.getName().equalsIgnoreCase("cpu_abi2")) {
					crashLog.setCpuAbi2(field.get(null).toString());
				} else if (field.getName().equalsIgnoreCase("manufacturer")) {
					crashLog.setManufacturer(field.get(null).toString());
				} else if (field.getName().equalsIgnoreCase("brand")) {
					crashLog.setBrand(field.get(null).toString());
				} else if (field.getName().equalsIgnoreCase("model")) {
					crashLog.setModel(field.get(null).toString());
				} else if (field.getName().equalsIgnoreCase("bootloader")) {
					crashLog.setBootloader(field.get(null).toString());
				} else if (field.getName().equalsIgnoreCase("radio")) {
					crashLog.setRadio(field.get(null).toString());
				} else if (field.getName().equalsIgnoreCase("hardware")) {
					crashLog.setHardware(field.get(null).toString());
				} else if (field.getName().equalsIgnoreCase("serial")) {
					crashLog.setSerial(field.get(null).toString());
				} else if (field.getName().equalsIgnoreCase("type")) {
					crashLog.setType(field.get(null).toString());
				} else if (field.getName().equalsIgnoreCase("tags")) {
					crashLog.setTags(field.get(null).toString());
				} else if (field.getName().equalsIgnoreCase("fingerprint")) {
					crashLog.setFingerprint(field.get(null).toString());
				} else if (field.getName().equalsIgnoreCase("user")) {
					crashLog.setUser(field.get(null).toString());
				} else if (field.getName().equalsIgnoreCase("host")) {
					crashLog.setHost(field.get(null).toString());
				}
			} catch (Exception e) {
			}
		}
	}


	public void setCustomCrashHanler(Context paramContext) {
		this.mContext = paramContext;
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 此处可以用RxJava响应式上传
	 *
	 * @param paramThread
	 * @param paramThrowable
	 */
	public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
		showToast(this.mContext, "很抱歉，程序异常即将退出！");
		this.crashLog = new CrashLog();
		collectDeviceInfo(this.mContext);
		saveCrashLog2File(paramThrowable);
		ActivityStack.exic();
/*		//网络允许的话上传
		if (true) {
			uploadLog(paramThrowable);
		}*/
/*		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}
}
