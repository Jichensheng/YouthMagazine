package com.jcs.magazine.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.jcs.magazine.R;
import com.jcs.magazine.activity.mine.UserInfoActivity;
import com.jcs.magazine.base.BaseActivity;
import com.jcs.magazine.bean.BaseMgz;
import com.jcs.magazine.bean.UserBean;
import com.jcs.magazine.global.LoginUserHelper;
import com.jcs.magazine.network.YzuClient;
import com.jcs.magazine.util.MessageEvent;
import com.jcs.magazine.util.UiUtil;
import com.jcs.magazine.widget.SuperEditText;
import com.jcs.topsnackbar.Prompt;
import com.jcs.topsnackbar.TSnackbar;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

/**
 * author：Jics
 * 2017/9/12 16:11
 */
public class LoginActicity extends BaseActivity implements View.OnClickListener{
	private final static int MODE_LOGIN=0x200;
	private final static int MODE_REGIST_NORMAL=0x201;
	private final static int MODE_REGIST_SMS=0x202;
	private final static int CUTDOWN_TIME=60*1000;
	private final static String REGIST_TYPE_NICK="nick";
	private final static String REGIST_TYPE_PHONE="phone";
	final private String TAG="LoginActicity";
	private SuperEditText set_nick,set_psw,set_psw_re,set_sms,set_phone;
	private Button btn_login,btn_regist,btn_get_sms;
	private TextView tv_forget;
	private TimeCount time;
	private boolean isCountDown;
	private long  currMills;
	private int currentMode=MODE_LOGIN;

	@Override
	protected void onCreate(@Nullable Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 不显示系统的标题栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_login);
		initView();

	}

	private void initView() {

		time = new TimeCount(CUTDOWN_TIME, 1000);
		set_nick= (SuperEditText) findViewById(R.id.set_nick);
		set_psw= (SuperEditText) findViewById(R.id.set_psw);
		set_psw_re= (SuperEditText) findViewById(R.id.set_psw_re);
		set_sms= (SuperEditText) findViewById(R.id.set_sms);
		set_phone= (SuperEditText) findViewById(R.id.set_phone);
		btn_login= (Button) findViewById(R.id.btn_withe);
		btn_regist= (Button) findViewById(R.id.btn_trans);
		btn_get_sms= (Button) findViewById(R.id.btn_get_sms);
		tv_forget= (TextView) findViewById(R.id.tv_forget);

        btn_login.setOnClickListener(this);
        btn_regist.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
		btn_get_sms.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_withe:
				whiteButton();
				break;
			case R.id.btn_trans:
				transButton();
				break;
			case R.id.tv_forget:
				forget();
				break;
			case R.id.btn_get_sms:
				getSMS();
				break;
		}
	}

	/**
	 * 登录业务
	 */
	private void whiteButton() {
		switch (currentMode) {
			case MODE_LOGIN:
				if (!TextUtils.isEmpty(set_nick.getText())&&!TextUtils.isEmpty(set_psw.getText())) {
					YzuClient.getInstance()
							.login(set_nick.getText().toString(),set_psw.getText().toString())
							.subscribeOn(Schedulers.newThread())
							.observeOn(AndroidSchedulers.mainThread())
							.subscribe(new Consumer<BaseMgz<UserBean>>() {
								@Override
								public void accept(BaseMgz<UserBean> userBeanBaseMgz) throws Exception {

									UiUtil.toast(userBeanBaseMgz.getResults().getToken());

									UserBean user = userBeanBaseMgz.getResults();
									//更新全局变量
									LoginUserHelper.getInstance().setLoginUser(user);
									/**
									 * 登录成功更新本地数据
									 */
									SharedPreferences sp=getSharedPreferences("user_info", Context.MODE_PRIVATE);
									SharedPreferences.Editor editor=sp.edit();
									editor.putBoolean("user_info_isloged",true);
									editor.putString("user_info_nicname",user.getNick());
									editor.putString("user_info_psw",user.getPsw());
									editor.putLong("user_info_time", System.currentTimeMillis());
									editor.putString("user_info_token",user.getToken());
									editor.apply();

									//判断是否有计划跳转的页面
									if (getIntent().getExtras() != null && getIntent().getExtras().getString("className") != null) {
										String className = getIntent().getExtras().getString("className");
										getIntent().removeExtra("className");
										if (className != null ) {
											try {
												ComponentName componentName = new ComponentName(LoginActicity.this, Class.forName(className));
												startActivity(getIntent().setComponent(componentName));
												finish();
											} catch (ClassNotFoundException e) {
												e.printStackTrace();
											}
										}
									}else{//没有计划跳转的页面就finish掉
										finish();
									}

									EventBus.getDefault().post(new MessageEvent("refresh_login_state"));
								}
							}, new Consumer<Throwable>() {
								@Override
								public void accept(Throwable throwable) throws Exception {
									snack("网络出故障了");
								}
							});
				}else{
					snack("输入信息不完整");
				}
				break;
			case MODE_REGIST_NORMAL://短信注册功能
				refreshLayout(MODE_REGIST_SMS);
				break;
			case MODE_REGIST_SMS://短信注册功能
//				UiUtil.toast("验证码提交服务\n"+set_phone.getText()+"\n"+set_sms.getText());
				boolean smsCurrect = true;
				if (!TextUtils.isEmpty(set_phone.getText())&&!TextUtils.isEmpty(set_sms.getText())&& smsCurrect) {
						MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
						builder.addFormDataPart("type",REGIST_TYPE_NICK);
						builder.addFormDataPart("phone",set_phone.getText().toString());
						registUser(builder.build());
				}else{
					if (!smsCurrect){
						snack("验证码错误");
					}
					snack("输入信息不完整");
				}
				break;
		}

	}


	/**
	 * 透明按钮逻辑(下边的按钮)
	 */
	private void transButton() {
		switch(currentMode){
			case MODE_LOGIN:
				refreshLayout(MODE_REGIST_NORMAL);
				break;
			case MODE_REGIST_NORMAL://当前已经是注册模式的话就提交数据
//				UiUtil.toast("账号注册提交服务\n"+set_nick.getText()+"\n"+set_psw.getText()+"\n"+set_psw_re.getText());
				if (!TextUtils.isEmpty(set_nick.getText())&&!TextUtils.isEmpty(set_psw.getText())&&!TextUtils.isEmpty(set_psw_re.getText())) {
					if (!set_psw.getText().toString().equals(set_psw_re.getText().toString())) {
						snack("两次密码不一样");
					}else {
						MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
						builder.addFormDataPart("type",REGIST_TYPE_PHONE);
						builder.addFormDataPart("nick",set_nick.getText().toString());
						builder.addFormDataPart("psw",set_psw.getText().toString());
						registUser(builder.build());
					}
				}else{
					snack("输入信息不完整");
				}

				break;
			case MODE_REGIST_SMS:
				refreshLayout(MODE_REGIST_NORMAL);
				break;
		}
	}

	private void registUser(MultipartBody requestbody) {
		YzuClient.getInstance().registUserInfo(requestbody)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<UserBean>() {
					@Override
					public void accept(UserBean userBean) throws Exception {
						LoginUserHelper.getInstance().setLoginUser(userBean);
						/**
						 * 注册成功更新本地数据
						 */
						SharedPreferences sp=getSharedPreferences("user_info", Context.MODE_PRIVATE);
						SharedPreferences.Editor editor=sp.edit();
						editor.putBoolean("user_info_isloged",true);
						editor.putString("user_info_nicname",userBean.getNick());
						editor.putString("user_info_psw",userBean.getPsw());
						editor.putLong("user_info_time", System.currentTimeMillis());
						editor.putString("user_info_token",userBean.getToken());
						editor.apply();

						EventBus.getDefault().post(new MessageEvent("refresh_login_state"));
						startActivity(new Intent(LoginActicity.this, UserInfoActivity.class));
						finish();
					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(Throwable throwable) throws Exception {
						snack("请求失败，请稍后重试");
					}
				});
	}

	/**
	 * 忘记按钮逻辑
	 */
	private void forget() {
		//现在是登录状态的话是找密码功能
		if (currentMode==MODE_LOGIN) {

		}else {//注册状态的话就转换成登录页
			refreshLayout(MODE_LOGIN);
		}

	}
	private void refreshLayout(int mode){
		if (currentMode!=mode) {
			currentMode=mode;
			switch(mode){
				case MODE_LOGIN:
					tv_forget.setText("忘记密码？");
					if (set_psw_re.getVisibility()!= View.GONE) set_psw_re.setVisibility(View.GONE);
					btn_regist.setText("注　册");
					btn_login.setText("登　录");

					set_nick.setVisibility(View.VISIBLE);
					set_psw.setVisibility(View.VISIBLE);

					set_phone.setVisibility(View.GONE);
					set_sms.setVisibility(View.GONE);
					btn_get_sms.setVisibility(View.GONE);
					break;
				case MODE_REGIST_NORMAL:
					tv_forget.setText("有账号去登录");
					if (set_psw_re.getVisibility()!= View.VISIBLE) set_psw_re.setVisibility(View.VISIBLE);
					btn_regist.setText("完成注册");
					btn_login.setText("短信注册");

					set_nick.setVisibility(View.VISIBLE);
					set_psw.setVisibility(View.VISIBLE);

					set_phone.setVisibility(View.GONE);
					set_sms.setVisibility(View.GONE);
					btn_get_sms.setVisibility(View.GONE);
					break;
				case MODE_REGIST_SMS:
					tv_forget.setText("有账号去登录");
					if (set_psw_re.getVisibility()!= View.VISIBLE) set_psw_re.setVisibility(View.VISIBLE);
					btn_regist.setText("账号注册");
					btn_login.setText("完成注册");

					set_nick.setVisibility(View.GONE);
					set_psw.setVisibility(View.GONE);
					set_psw_re.setVisibility(View.GONE);

					set_phone.setVisibility(View.VISIBLE);
					set_sms.setVisibility(View.VISIBLE);
					btn_get_sms.setVisibility(View.VISIBLE);
					break;
			}
		}


	}

	/**
	 * 倒计时功能
	 */
	public void getSMS() {
		time.start();
	}

	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {// 计时完毕
			btn_get_sms.setText("获取验证码");
			btn_get_sms.setClickable(true);
			time = new TimeCount(CUTDOWN_TIME, 1000);
			isCountDown = false;
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程
			btn_get_sms.setClickable(false);// 防止重复点击
			btn_get_sms.setText(millisUntilFinished / 1000 + "s");
			currMills = millisUntilFinished;
			isCountDown = true;
		}
	}

	@Override
	protected void onStop() {

		if (isCountDown) {
			// 当前系统时间
			long currTime = System.currentTimeMillis();
			// 获取倒数时间
			getPreferences(Activity.MODE_PRIVATE).edit()
					.putLong("lastTimeChange", currMills)
					.putLong("currentTimeChange", currTime)
					.apply();
			time.cancel();
		}
		super.onStop();
	}
	@Override
	protected void onResume() {
		SharedPreferences sharedPreferences1 = getPreferences(Activity.MODE_PRIVATE);
		long lastTime = sharedPreferences1.getLong("lastTimeChange", 0);
		long oriTime = sharedPreferences1.getLong("currentTimeChange", 0);

		long newTime = System.currentTimeMillis();

		if ((newTime - oriTime) >= lastTime) {
			btn_get_sms.setText("获取验证码");
			btn_get_sms.setClickable(true);
			time = new TimeCount(CUTDOWN_TIME, 1000);
		} else {
			btn_get_sms.setClickable(false);
			btn_get_sms.setText((lastTime - (newTime - oriTime)) / 1000 + " s");
			time = new TimeCount((lastTime - (newTime - oriTime)), 1000);
			time.start();
		}
		super.onResume();
	}
	private void snack(String s) {
		TSnackbar.make(findViewById(android.R.id.content).getRootView(),
				s, TSnackbar.LENGTH_SHORT, TSnackbar.APPEAR_FROM_TOP_TO_DOWN)
				.setPromptThemBackground(Prompt.WARNING).show();
	}
}
