package com.dianjiake.android.ui.subscribe;

import com.dianjiake.android.api.Network;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.data.bean.BaseListBean;
import com.dianjiake.android.data.bean.ServiceBean;
import com.dianjiake.android.data.bean.UserInfoBean;

import io.reactivex.Observable;

/**
 * Created by lfs on 2017/7/18.
 */

public class StaffServicePresenter extends BaseServiceStaffPresenter<ServiceBean, StaffServiceFragment> {

    String shopId, openId;

    public StaffServicePresenter(StaffServiceFragment view, String shopId, String serviceId) {
        super(view);
        this.shopId = shopId;
        this.openId = serviceId;
    }


    @Override
    protected Observable<BaseListBean<ServiceBean>> provideObservable() {
        return Network.getInstance().staffService(
                BSConstant.STAFF_SERVICE,
                shopId,
                openId,
                page);
    }
}
