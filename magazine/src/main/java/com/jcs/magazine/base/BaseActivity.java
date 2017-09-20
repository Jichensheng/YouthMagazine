package com.jcs.magazine.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.jcs.magazine.bean.BaseMgz;
import com.jcs.magazine.bean.UserBean;
import com.jcs.magazine.config.BuildConfig;
import com.jcs.magazine.crash.ActivityStack;
import com.jcs.magazine.global.LoginUserHelper;
import com.jcs.magazine.network.YzuClient;
import com.jcs.magazine.util.UiUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class BaseActivity extends AppCompatActivity
{
  protected void onCreate(@Nullable Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    ActivityStack.addActivity(this);
    initWindow();
  }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp=getSharedPreferences("user_info", Context.MODE_PRIVATE);
        if (sp.getBoolean("user_info_isloged",false)) {//登陆过就判断是否超时
            long user_info_time=sp.getLong("user_info_time",0);
            //超时就清空
            if (System.currentTimeMillis()-user_info_time> BuildConfig.DEAD_TIME) {
                sp.edit().putBoolean("user_info_isloged",false).apply();
                LoginUserHelper.getInstance().logout();
            }
        }
    }

    protected void onDestroy()
  {
    ActivityStack.removeActivity(this);
    super.onDestroy();
  }
  private void initWindow() {
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && android.os.Build.VERSION.SDK_INT<=Build.VERSION_CODES.LOLLIPOP) {
      Window window = getWindow();
      window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
  }
}
