package com.jcs.magazine.global;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.jcs.magazine.activity.LoginActicity;
import com.jcs.magazine.activity.mine.CollectionActivity;
import com.jcs.magazine.activity.mine.PartnerActivity;
import com.jcs.magazine.activity.mine.PostActivity;
import com.jcs.magazine.activity.mine.UserInfoActivity;

import java.util.HashMap;

/**
 * 维护需要登录才可使用的单例模块
 */
public class PermissionHelper {
	private static final int VISITOR = 1000;
	private static final int LOGINUSER = 1001;

	private static final HashMap<String, Integer> PERMISSIONMAP = new HashMap<>();

	static {
		PERMISSIONMAP.put(CollectionActivity.class.getName(), LOGINUSER);
		PERMISSIONMAP.put(PartnerActivity.class.getName(), LOGINUSER);
		PERMISSIONMAP.put(PostActivity.class.getName(), LOGINUSER);
		PERMISSIONMAP.put(UserInfoActivity.class.getName(), LOGINUSER);
		PERMISSIONMAP.put("XXActivity.class.getName()", VISITOR);

	}

	private static PermissionHelper instance;

	private PermissionHelper() {

	}

	public static PermissionHelper getHelper() {
		synchronized (PermissionHelper.class) {
			if (null == instance) {
				instance = new PermissionHelper();
			}
			return instance;
		}
	}

	/**
	 * 要跳转的页面是否需要登录权限
	 * @param c
	 * @param intent
	 */
	public void startActivity(Context c, Intent intent) {
		String clazzName = intent.getComponent().getClassName();
		//获取所要跳转的页面是否需要登录
		int flag = PERMISSIONMAP.get(clazzName) != null ? PERMISSIONMAP.get(clazzName) : VISITOR;
		//根据登录与否设置当前的用户类型
		int currentFlag = LoginUserHelper.getInstance().isLogined() ? LOGINUSER : VISITOR;
		//flag如果是不需要登录的，那么两种用户都满足，如果需要那么只有currentFlag为LOGINUSER才可以满足
		if (currentFlag >= flag) {
			c.startActivity(intent);
		} else {
			//设置中转站（将原来要打开的classname替换成中转站classname）
			ComponentName componentName = new ComponentName(c, LoginActicity.class);
			//把原来intent要打开的activity的classname保存为参数
			intent.putExtra("className", intent.getComponent().getClassName());
			intent.setComponent(componentName);
			c.startActivity(intent);

		}
	}

	public void startActivityForResult(Context c, Intent intent, int requestCode) {
		String clazzName = intent.getComponent().getClassName();
		//获取所要跳转的页面是否需要登录
		int flag = PERMISSIONMAP.get(clazzName) != null ? PERMISSIONMAP.get(clazzName) : VISITOR;
		//根据登录与否设置当前的用户类型
		int currentFlag = LoginUserHelper.getInstance().isLogined()? LOGINUSER : VISITOR;
		//flag如果是不需要登录的，那么两种用户都满足，如果需要那么只有currentFlag为LOGINUSER才可以满足
		if (currentFlag >= flag) {
			((Activity) c).startActivityForResult(intent, requestCode);
		} else {
			//否则跳转登录页面
		}
	}

}
