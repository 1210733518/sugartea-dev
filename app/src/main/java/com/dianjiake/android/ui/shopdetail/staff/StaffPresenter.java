package com.dianjiake.android.ui.shopdetail.staff;

import com.dianjiake.android.api.Network;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.data.bean.BaseListBean;
import com.dianjiake.android.data.bean.UserInfoBean;
import com.dianjiake.android.ui.shopdetail.BaseShopContentPresenter;

import io.reactivex.Observable;

/**
 * Created by lfs on 2017/7/17.
 */

public class StaffPresenter extends BaseShopContentPresenter<UserInfoBean, StaffContract.View> {


    public StaffPresenter(StaffContract.View view, String shopId) {
        super(view, shopId);
    }

    @Override
    protected Observable<BaseListBean<UserInfoBean>> provideObservable() {
        return Network.getInstance().shopStaff(
                BSConstant.SHOP_STAFF,
                shopId,
                page);
    }
}
