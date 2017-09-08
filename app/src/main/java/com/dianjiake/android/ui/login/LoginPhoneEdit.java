package com.dianjiake.android.ui.login;


/**
 * Created by lfs on 17/2/22.
 */

public class LoginPhoneEdit implements LoginPhoneType {
    @Override
    public String getToolbarTitle() {
        return "修改手机号";
    }

    @Override
    public String getButtonTitle() {
        return "确认修改";
    }

    @Override
    public String getSuccessMessage() {
        return "手机号修改成功";
    }


}
