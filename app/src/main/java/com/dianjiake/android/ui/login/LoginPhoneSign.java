package com.dianjiake.android.ui.login;

/**
 * Created by lfs on 17/2/22.
 */

public class LoginPhoneSign implements LoginPhoneType {
    @Override
    public String getToolbarTitle() {
        return "手机号登录";
    }

    @Override
    public String getButtonTitle() {
        return "登录";
    }

    @Override
    public String getSuccessMessage() {
        return "登录成功";
    }


}
