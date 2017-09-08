package com.dianjiake.android.ui.shopdetail.service;

import com.dianjiake.android.base.BaseListPresenter;
import com.dianjiake.android.base.BaseListView;
import com.dianjiake.android.base.BasePresenter;
import com.dianjiake.android.data.bean.ServiceBean;

import java.util.List;

/**
 * Created by lfs on 2017/7/17.
 */

public interface ServiceContract {
    interface View extends BaseListView {
    }

    interface Presenter extends BaseListPresenter {
        List<ServiceBean> getItems();
    }
}
