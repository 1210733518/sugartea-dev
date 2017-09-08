package com.dianjiake.android.ui.searchshop;

import com.dianjiake.android.base.BaseListPresenter;
import com.dianjiake.android.base.BaseListView;
import com.dianjiake.android.data.bean.HomeShopBean;

import java.util.List;

/**
 * Created by lfs on 2017/7/12.
 */

public interface ShopResultContract {
    interface View extends BaseListView {
    }

    interface Presenter extends BaseListPresenter {
        List<HomeShopBean> getItems();
    }
}
