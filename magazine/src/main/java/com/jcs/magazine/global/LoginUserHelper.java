package com.jcs.magazine.global;

import com.jcs.magazine.bean.UserBean;

/**
 * Created by Jcs on 2017/9/13.
 */

public class LoginUserHelper {
    private static LoginUserHelper instance;
    private UserBean userBean;

    private LoginUserHelper() {

    }

    public static LoginUserHelper getInstance() {
        synchronized (LoginUserHelper.class) {
            if (instance == null) {
                instance = new LoginUserHelper();
            }
            return instance;
        }
    }

    public UserBean getUser() {
        return userBean;
    }

    public void setLoginUser(UserBean userBean) {
        this.userBean = userBean;
    }

    public boolean isLogined() {
        return userBean != null;
    }

    public void logout() {
        userBean = null;
    }

}
