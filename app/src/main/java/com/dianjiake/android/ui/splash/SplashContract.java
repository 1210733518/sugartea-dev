package com.dianjiake.android.ui.splash;


import com.dianjiake.android.base.BasePresenter;
import com.dianjiake.android.base.BaseView;

/**
 * Created by lfs on 2017/5/15.
 */

public interface SplashContract {
    interface SplashView extends BaseView<Presenter> {
        void startLoginActivity();

        void startMainActivity();
    }

    interface Presenter extends BasePresenter {
        void getPhoneInfo();

        String getMAC();

        void jump();
    }
}
