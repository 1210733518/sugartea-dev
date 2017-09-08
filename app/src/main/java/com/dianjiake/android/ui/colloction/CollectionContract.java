package com.dianjiake.android.ui.colloction;

import android.app.FragmentManager;

import com.dianjiake.android.base.BaseListPresenter;
import com.dianjiake.android.base.BaseListView;
import com.dianjiake.android.data.bean.HomeShopBean;

import java.util.List;

/**
 * Created by lfs on 2017/7/25.
 */

public interface CollectionContract {
    interface View extends BaseListView {
        FragmentManager provideFM();

        void showProgress();

        void dismissProgress();
    }

    interface Presenter extends BaseListPresenter {
        List<HomeShopBean> getItems();

        FragmentManager getFM();

        void deleteItem(HomeShopBean shopBean, int position);

    }
}
