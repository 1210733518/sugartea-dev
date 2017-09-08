package com.dianjiake.android.ui.shopdetail;

import com.dianjiake.android.base.BasePresenter;
import com.dianjiake.android.base.BaseView;
import com.dianjiake.android.data.bean.ShopDetailBean;

/**
 * Created by lfs on 2017/7/14.
 */

public interface ShopDetailContract {
    interface View extends BaseView<Presenter> {

        String getShopId();

        void setView(ShopDetailBean shopDetail);

        void showCollectionPD();

        void dismissCollectionPD();

        void showCollectSuccessToast();
        void showDeleteSuccessToast();
        void showNetworkErrorToast();

        void setToolbarIconByAlpha();
    }

    interface Presenter extends BasePresenter {
        void load();

        void collect();

        String getPhone();

        boolean isCollect();

        ShopDetailBean getDetailBean();

        boolean isLogin();
    }
}
