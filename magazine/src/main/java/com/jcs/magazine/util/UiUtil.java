package com.jcs.magazine.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcs.magazine.R;
import com.jcs.magazine.base.BaseApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UiUtil {

	public static Toast INSTANCE = null;
	private static String PRE_MSG;
	private static long PRE_TIME = 0;
	private static long NEXT_TIME = 0;

	/**
	 * 隐藏视图组件
	 * 
	 * @param view
	 *            要隐藏的组件实例
	 */
	public static void hideView(View view) {
		if (null != view && View.VISIBLE == view.getVisibility())
			view.setVisibility(View.GONE);
	}

	/**
	 * 显示视图组件
	 * 
	 * @param view
	 *            要显示的组件实例
	 */
	public static void showView(View view) {
		if (null != view && View.GONE == view.getVisibility())
			view.setVisibility(View.VISIBLE);
	}

	/**
	 * 在指定View下实例化组件
	 * 
	 * @param textViewId
	 *            组件ID
	 * @param parent
	 *            组件父视图
	 * @return TextView
	 */
	public static TextView getTextView(int textViewId, View parent) {
		return (TextView) parent.findViewById(textViewId);
	}

	/**
	 * Activity界面实例化组件
	 * 
	 * @param activity
	 *            Activity
	 * @param textViewId
	 *            组件ID
	 * @return TextView
	 */
	public static TextView getTextView(Activity activity, int textViewId) {
		return (TextView) activity.findViewById(textViewId);
	}

	/**
	 * 在指定View下实例化组件
	 * 
	 * @param editTextId
	 *            组件ID
	 * @param parent
	 *            组件父视图
	 * @return EditText
	 */
	public static EditText getEditText(int editTextId, View parent) {
		return (EditText) parent.findViewById(editTextId);
	}

	/**
	 * Activity界面实例化组件
	 * 
	 * @param activity
	 *            Activity
	 * @param editTextId
	 *            组件ID
	 * @return EditText
	 */
	public static EditText getEditText(Activity activity, int editTextId) {
		return (EditText) activity.findViewById(editTextId);
	}

	/**
	 * 在指定View下实例化组件
	 * 
	 * @param imageViewId
	 *            组件ID
	 * @param parent
	 *            组件父视图
	 * @return ImageView
	 */
	public static ImageView getImageView(int imageViewId, View parent) {
		return (ImageView) parent.findViewById(imageViewId);
	}

	/**
	 * ctivity界面实例化组件
	 * 
	 * @param activity
	 *            Activity
	 * @param imageViewId
	 *            组件ID
	 * @return ImageView
	 */
	public static ImageView getImageView(Activity activity, int imageViewId) {
		return (ImageView) activity.findViewById(imageViewId);
	}

	/**
	 * Activity界面实例化组件
	 * 
	 * @param activity
	 *            Activity
	 * @param scrollViewId
	 *            组件ID
	 * @return EditText
	 */
	public static ScrollView getScrollView(Activity activity, int scrollViewId) {
		return (ScrollView) activity.findViewById(scrollViewId);
	}

	/**
	 * 在指定父视图中加载按钮
	 * 
	 * @param buttonId
	 *            组件ID
	 * @param parent
	 *            组件父视图
	 * @return Button
	 */
	public static Button getButton(int buttonId, View parent) {
		return (Button) parent.findViewById(buttonId);
	}

	/**
	 * 显示Toast消息
	 * 
	 * @param context
	 *            上下文
	 * @param message
	 *            消息内容
	 */
	private static void toast(Context context, String message) {
		if (null == message) {
			message = "发生未知错误";
		}
		View view = LayoutInflater.from(context).inflate(R.layout.toastlayout, null);

		TextView content = (TextView) view.findViewById(R.id.content);

		if (null == INSTANCE) {
			INSTANCE = new Toast(context);

			content.setText(message);

			INSTANCE.setView(view);
			// INSTANCE.setGravity(Gravity.CENTER, 0, 0);
			INSTANCE.setDuration(Toast.LENGTH_SHORT);

			INSTANCE.show();
			PRE_TIME = System.currentTimeMillis();
		} else {
			view = INSTANCE.getView();
			content = (TextView) view.findViewById(R.id.content);

			NEXT_TIME = System.currentTimeMillis();
			if (message.equals(PRE_MSG)) {
				if (NEXT_TIME - PRE_TIME > Toast.LENGTH_SHORT) {
					INSTANCE.show();
				}
			} else {
				PRE_MSG = message;
				content.setText(message);
				INSTANCE.show();
			}
		}
		PRE_TIME = NEXT_TIME;
	}

	public static void toast(String message) {
		toast(BaseApplication.getInstance(), message);
	}



	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 * 
	 * @param context
	 *            上下文
	 * @param pixels
	 *            pixels
	 * @return DIP转换后的PX值
	 */
	public static int pix2dip(Context context, float pixels) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pixels * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int getScreenWidth(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	public static int getScreenHeight(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	public static float getDensity(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.density;
	}

	/**
	 * 获取一个Dialog
	 * 
	 * @param context
	 * @param positiveBtn
	 * @param negativeBtn
	 * @param strings
	 *            可变的String参数， 第一个参数：Message对应的内容 如果想显示对话框的title ;
	 *            第二个参数：positiveBtn对应的内容; 第三个参数：negativeBtn对应的参数; 第四个参数就是title内容
	 * @return
	 */
	public static AlertDialog showAlertDialog(Context context, OnClickListener positiveBtn,
			OnClickListener negativeBtn, String... strings) {
		String positiveStr, negativeStr, msg, title;
		int length = strings.length;

		if (length <= 0)
			return null;
		else if (length >= 3) {
			positiveStr = strings[1];
			negativeStr = strings[2];
			msg = strings[0];
		}

		OnClickListener mListener;
		if (negativeBtn == null) {
			mListener = new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			};
		} else
			mListener = negativeBtn;

		AlertDialog alertDialog = new AlertDialog.Builder(context).setMessage(strings[0])
				.setPositiveButton(strings[1], positiveBtn).setNegativeButton(strings[2], mListener).create();

		if (length >= 4) {
			title = strings[3];
			alertDialog.setTitle(title);
		}

		alertDialog.show();

		// 修改对话框的蓝色线条颜色 style设置没有效果
		int divierId = context.getResources().getIdentifier("android:id/titleDivider", null, null);
		View divider = alertDialog.findViewById(divierId);
//		divider.setBackgroundColor(context.getResources().getColor(R.color.light_gray));

		return alertDialog;
	}

	public static String getTime(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date(time);
		return sdf.format(d);
	}

	public static String formatTime(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatTime(sdf.format(new Date(time)));
	}

	public static String formatTime(String str) {
		if (str.isEmpty()) {
			return str;
		}
		// 对数据库或缓存里面聊天记录时间做的处理
		String strTime = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		Date mydate = null;
		String date = "";
		int year = 0;
		int month = 0;
		int day = 0;
		int hour = 0;
		int minute = 0;
		String hours = "";
		String minutes = "";
		@SuppressWarnings("unused")
		int second = 0;
		try {
			mydate = sdf.parse(str);
			date = sdf.format(mydate);
			year = mydate.getYear() + 1900;
			month = mydate.getMonth() + 1;
			day = mydate.getDate();
			hour = mydate.getHours();
			minute = mydate.getMinutes();
			second = mydate.getSeconds();
			if (hour >= 10) {
				hours = hour + "";
			} else {
				hours = "0" + hour;
			}
			if (minute >= 10) {
				minutes = minute + "";
			} else {
				minutes = "0" + minute;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 获取系统当前时间作为比较标准
		Calendar calendar = Calendar.getInstance();
		int dayBase = calendar.get(Calendar.DAY_OF_MONTH);
		int monthBase = calendar.get(Calendar.MONTH) + 1;
		int yearBase = calendar.get(Calendar.YEAR);
		String DayStr = getWeek(new Date());
		if (year < yearBase) {
			strTime = date.substring(0, 10).replace("-", "/");
		} else if (year == yearBase) {
			if (month < monthBase) {
				strTime = date.substring(5, 10).replace("-", "/") + " " + hours + ":" + minutes;
			} else if (month == monthBase) {
				if ((dayBase - day) == 0) {
					strTime = strTime + hours + ":" + minutes;
				} else if ((dayBase - day) == 1) {
					strTime = strTime + "昨天";
				} else if ((dayBase - day) > 1) {
					if (DayStr.equals("星期一")) {
						strTime = date.substring(5, 10).replace("-", "/") + " " + hours + ":" + minutes;
					}
					if (DayStr.equals("星期二")) {
						strTime = date.substring(5, 10).replace("-", "/") + " " + hours + ":" + minutes;
					}
					if (DayStr.equals("星期三")) {
						if ((dayBase - day) < 3) {
							strTime = getWeek(str) + " " + hours + ":" + minutes;
						} else {
							strTime = date.substring(5, 10).replace("-", "/") + " " + hours + ":" + minutes;
						}
					}
					if (DayStr.equals("星期四")) {
						if ((dayBase - day) < 4) {
							strTime = getWeek(str) + " " + hours + ":" + minutes;
						} else {
							strTime = date.substring(5, 10).replace("-", "/") + " " + hours + ":" + minutes;
						}
					}
					if (DayStr.equals("星期五")) {
						if ((dayBase - day) < 5) {
							strTime = getWeek(str) + " " + hours + ":" + minutes;
						} else {
							strTime = date.substring(5, 10).replace("-", "/") + " " + hours + ":" + minutes;
						}
					}
					if (DayStr.equals("星期六")) {
						if ((dayBase - day) < 6) {
							strTime = getWeek(str) + " " + hours + ":" + minutes;
						} else {
							strTime = date.substring(5, 10).replace("-", "/") + " " + hours + ":" + minutes;
						}
					}
					if (DayStr.equals("星期天")) {
						if ((dayBase - day) < 7) {
							strTime = getWeek(str) + " " + hours + ":" + minutes;
						} else {
							strTime = date.substring(5, 10).replace("-", "/") + " " + hours + ":" + minutes;
						}
					}
				} else if ((dayBase - day) < 0) {
					System.out.println("此条数据为无效数据！");
					return str;
				}
			}
		} else if (year > yearBase) {
			System.out.println("此条数据为无效数据！");
			return str;
		}
		return strTime;
	}

	public static String getWeek(String str) {
		SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		Date dates = null;
		try {
			dates = sdfs.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.CHINA);
		String week = sdf.format(dates);
		return week;
	}

	public static String getWeek(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.CHINA);
		String week = sdf.format(date);
		return week;
	}
}
