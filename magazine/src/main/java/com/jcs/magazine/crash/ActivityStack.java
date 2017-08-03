package com.jcs.magazine.crash;

import android.app.Activity;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 维护一个Activity栈防止多次崩溃
 */
public class ActivityStack {
	public static List<Activity> activityList = new LinkedList();

	public static void addActivity(Activity paramActivity) {
		activityList.add(paramActivity);
	}

	public static void exic() {
		if (activityList.size() > 0) {
			Iterator localIterator = activityList.iterator();
			while (localIterator.hasNext()) {
				Activity localActivity = (Activity) localIterator.next();
				try {
					localActivity.finish();
				} catch (Exception localException) {
				}
			}
		}
		System.exit(0);
	}

	public static void removeActivity(Activity paramActivity) {
		if (activityList != null)
			for (boolean bool = activityList.remove(paramActivity); bool; bool = activityList.remove(paramActivity))
				;
	}
}
