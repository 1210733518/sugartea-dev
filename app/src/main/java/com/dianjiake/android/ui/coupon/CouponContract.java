package com.dianjiake.android.ui.coupon;

import com.dianjiake.android.base.BaseListPresenter;
import com.dianjiake.android.base.BaseListView;
import com.dianjiake.android.data.bean.MyCouponBean;

import java.util.List;

/**
 * Created by lfs on 2017/7/25.
 */

public interface CouponContract {
    interface View extends BaseListView {
    }

    interface Presenter extends BaseListPresenter {
        List<MyCouponBean> getItems();
    }
}
