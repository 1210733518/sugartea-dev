package com.dianjiake.android.ui.orderdetail;

import android.content.Context;

import com.dianjiake.android.base.BasePresenter;
import com.dianjiake.android.base.BaseView;
import com.dianjiake.android.data.bean.OrderBean;

/**
 * Created by lfs on 2017/7/20.
 */

public interface OrderDetailContract {
    interface View extends BaseView<Presenter> {
        void setView(OrderBean bean);

        void showCancelPD();

        void dismissCancelPD();

        void showGetPD();

        void dismissGetPD();

        Context provideContext();
    }

    interface Presenter extends BasePresenter {
        void setOrderBean(OrderBean orderBean);

        void cancelOrder();

        void call();

        void reSub();

        void evaluate();

        void getOrderDetail(String shopId, String orderId);
    }
}
