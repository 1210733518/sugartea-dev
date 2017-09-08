package com.dianjiake.android.ui.shopweb;

import com.dianjiake.android.data.bean.HomeShopBean;

/**
 * Created by lfs on 2017/7/17.
 */

public interface BaseProvider<T> {
    String getTitle();

    String getUrl();

    T getBean();

    HomeShopBean getShopBean();
}
