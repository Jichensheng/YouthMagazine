package com.jcs.magazine.activity.mine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.SharedPreferencesCompat;
import android.view.View;
import android.widget.Button;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.jcs.magazine.R;
import com.jcs.magazine.base.BaseActivity;
import com.jcs.magazine.bean.UserBean;
import com.jcs.magazine.global.LoginUserHelper;
import com.jcs.magazine.util.MessageEvent;
import com.jcs.magazine.util.UiUtil;
import com.jcs.magazine.widget.CircleImageView;

import org.greenrobot.eventbus.EventBus;

/**
 * author：Jics
 * 2017/9/13 10:07
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener{
	@Override
	protected void onCreate(@Nullable Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_user_info);
        initView();
	}

    private void initView() {
        Button btn_exit= (Button) findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(this);

        CircleImageView head= (CircleImageView) findViewById(R.id.civ_avater);
        SuperTextView stv_name= (SuperTextView) findViewById(R.id.stv_name);
        SuperTextView stv_nick= (SuperTextView) findViewById(R.id.stv_nick);
        SuperTextView stv_phone= (SuperTextView) findViewById(R.id.stv_phone);
        SuperTextView stv_college= (SuperTextView) findViewById(R.id.stv_college);

        UserBean userBean=LoginUserHelper.getInstance().getUser();
        if (userBean != null) {
            Glide.with(this).load(userBean.getHead()).error(R.drawable.default_avater).into(head);
            stv_name.setRightString(userBean.getName());
            stv_nick.setRightString(userBean.getNick());
            stv_phone.setRightString(userBean.getPhone());
            stv_college.setRightString(userBean.getCollege());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exit:
                SharedPreferences sp=getSharedPreferences("user_info", Context.MODE_PRIVATE);
                sp.edit().putBoolean("user_info_isloged",false).apply();
                LoginUserHelper.getInstance().setLoginUser(null);
                EventBus.getDefault().post(new MessageEvent("refresh_login_state"));
                finish();
                break;
        }
    }
}
