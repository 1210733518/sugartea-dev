package com.dianjiake.android.ui.login;


import com.dianjiake.android.util.EventUtil;

/**
 * 登录页面管理类
 * Created by lfs on 17/2/23.
 */

public class LoginActivitiesManger {

    public static LoginActivitiesManger newInstance() {
        return new LoginActivitiesManger();
    }

    /**
     * 手机号第一次登录
     */
    public void phoneRegisterSuccess() {
        if (LoginPhoneActivity.sInstance != null) {
            LoginPhoneActivity.sInstance.finish();
        }
        if (LoginChooseActivity.sInstance != null) {
            LoginChooseActivity.sInstance.finish();
        }
    }

    /**
     * 手机号登录成功
     */
    public void phoneLoginSuccess() {
        if (LoginPhoneActivity.sInstance != null) {
            LoginPhoneActivity.sInstance.finish();
        }
        if (LoginChooseActivity.sInstance != null) {
            LoginChooseActivity.sInstance.finish();
        }
        EventUtil.postLoginEvent();
    }

    /**
     * 完善资料
     */
    public void completeInfoSuccess() {
        if (LoginChooseActivity.sInstance != null) {
            LoginChooseActivity.sInstance.finish();
        }
        if (LoginPhoneActivity.sInstance != null) {
            LoginPhoneActivity.sInstance.finish();
        }
        EventUtil.postLoginEvent();
    }

}