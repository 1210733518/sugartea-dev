package com.dianjiake.android.ui.login;


/**
 * Created by lfs on 17/2/22.
 */

public class LoginPhoneBind implements LoginPhoneType {
    @Override
    public String getToolbarTitle() {
        return "绑定手机号";
    }

    @Override
    public String getButtonTitle() {
        return "登录";
    }

    @Override
    public String getSuccessMessage() {
        return "手机号绑定成功";
    }


}
