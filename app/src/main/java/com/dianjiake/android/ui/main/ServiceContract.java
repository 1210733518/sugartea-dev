package com.dianjiake.android.ui.main;

import com.dianjiake.android.base.BasePresenter;
import com.dianjiake.android.base.BaseView;
import com.dianjiake.android.data.bean.ServiceFirstBean;
import com.dianjiake.android.data.bean.ServiceSecondBean;

import java.util.List;

/**
 * Created by lfs on 2017/7/25.
 */

public interface ServiceContract {
    interface View extends BaseView<Presenter> {
        void setItems(List<ServiceFirstBean> items, List<ServiceSecondBean> seconds);
    }

    interface Presenter extends BasePresenter {
        void load();

        boolean haveUnreadMsg();

        List<ServiceFirstBean> getServiceFirsts();

        List<ServiceSecondBean> getServiceSeconds();
    }
}
