package com.dianjiake.android.ui.setting;

import com.dianjiake.android.base.BasePresenter;
import com.dianjiake.android.base.BaseView;
import com.dianjiake.android.data.model.LoginInfoModel;

/**
 * Created by lfs on 2017/7/20.
 */

public interface SettingContract {
    interface View extends BaseView<Presenter> {
        void setPhone(String phone);
    }

    interface Presenter extends BasePresenter {
        void getInfo();

        LoginInfoModel getLoginInfo();
    }

}
