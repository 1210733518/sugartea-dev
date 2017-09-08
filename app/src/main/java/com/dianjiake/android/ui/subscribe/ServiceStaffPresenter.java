package com.dianjiake.android.ui.subscribe;

import com.dianjiake.android.api.Network;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.data.bean.BaseListBean;
import com.dianjiake.android.data.bean.UserInfoBean;

import io.reactivex.Observable;

/**
 * Created by lfs on 2017/7/18.
 */

public class ServiceStaffPresenter extends BaseServiceStaffPresenter<UserInfoBean, ServiceStaffFragment> {

    String shopId, serviceId;

    public ServiceStaffPresenter(ServiceStaffFragment view, String shopId, String serviceId) {
        super(view);
        this.shopId = shopId;
        this.serviceId = serviceId;
    }


    @Override
    protected Observable<BaseListBean<UserInfoBean>> provideObservable() {
        return Network.getInstance().serviceStaff(
                BSConstant.SERVICE_STAFF,
                shopId,
                serviceId,
                page);
    }
}
