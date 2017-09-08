package com.dianjiake.android.ui.main;


import com.dianjiake.android.base.BasePresenter;
import com.dianjiake.android.base.BaseView;

/**
 * Created by lfs on 2017/6/6.
 */

public interface MainContract {
    interface MainView extends BaseView<Presenter> {
        void initFragments();
    }

    interface Presenter extends BasePresenter {
        void startLocation();
    }
}
