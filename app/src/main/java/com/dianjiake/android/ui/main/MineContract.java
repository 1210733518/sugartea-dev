package com.dianjiake.android.ui.main;

import com.dianjiake.android.base.BasePresenter;
import com.dianjiake.android.base.BaseView;
import com.dianjiake.android.data.bean.UserInfoBean;

/**
 * Created by lfs on 2017/7/19.
 */

public interface MineContract {
    interface View extends BaseView<Presenter> {
        void setViews(UserInfoBean userInfoBean);

        void setAvatar(String avatar);

        void setName(String nickname,String phone);
    }

    interface Presenter extends BasePresenter {
        void load();
        boolean haveUnreadMsg();
    }
}
