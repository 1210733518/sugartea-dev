package com.dianjiake.android.ui.shopdetail.service;

import com.dianjiake.android.api.Network;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.data.bean.BaseListBean;
import com.dianjiake.android.data.bean.ServiceBean;
import com.dianjiake.android.ui.shopdetail.BaseShopContentPresenter;

import io.reactivex.Observable;

/**
 * Created by lfs on 2017/7/17.
 */

public class ServicePresenter extends BaseShopContentPresenter<ServiceBean, ServiceContract.View> {


    public ServicePresenter(ServiceContract.View view, String shopId) {
        super(view, shopId);
    }

    @Override
    protected Observable<BaseListBean<ServiceBean>> provideObservable() {
        return Network.getInstance().serviceList(
                BSConstant.SERVICE_LIST,
                shopId,
                page);
    }
}
