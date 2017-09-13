package com.jcs.magazine.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.jcs.magazine.R;
import com.jcs.magazine.base.BaseActivity;
import com.jcs.magazine.bean.BaseMgz;
import com.jcs.magazine.bean.UserBean;
import com.jcs.magazine.global.LoginUserHelper;
import com.jcs.magazine.network.YzuClient;
import com.jcs.magazine.util.MessageEvent;
import com.jcs.magazine.util.UiUtil;
import com.jcs.magazine.widget.SuperEditText;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author：Jics
 * 2017/9/12 16:11
 */
public class LoginActicity extends BaseActivity implements View.OnClickListener{
	final private String TAG="LoginActicity";
	private SuperEditText set_nick,set_psw;
	private Button btn_login,btn_regist;
	private TextView tv_forget;
	private Intent planTo;

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
		set_nick= (SuperEditText) findViewById(R.id.set_nick);
		set_psw= (SuperEditText) findViewById(R.id.set_psw);
		btn_login= (Button) findViewById(R.id.btn_login);
		btn_regist= (Button) findViewById(R.id.btn_regist);
		tv_forget= (TextView) findViewById(R.id.tv_forget);

        btn_login.setOnClickListener(this);
        btn_regist.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_login:
				login();
				break;
			case R.id.btn_regist:
				regist();
				break;
			case R.id.tv_forget:
				forget();
				break;
		}
	}

	private void login() {
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

							EventBus.getDefault().post(new MessageEvent("success"));
						}
					}, new Consumer<Throwable>() {
						@Override
						public void accept(Throwable throwable) throws Exception {

						}
					});
		}
	}

	private void regist() {

	}

	private void forget() {

	}
}
