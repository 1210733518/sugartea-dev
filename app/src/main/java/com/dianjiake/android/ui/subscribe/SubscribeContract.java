package com.dianjiake.android.ui.subscribe;

import android.app.Service;
import android.support.v4.util.ArrayMap;

import com.dianjiake.android.base.BasePresenter;
import com.dianjiake.android.base.BaseView;
import com.dianjiake.android.data.bean.OrderBean;
import com.dianjiake.android.data.bean.ServiceBean;
import com.dianjiake.android.data.bean.UserInfoBean;
import com.dianjiake.android.data.model.LoginInfoModel;
import com.dianjiake.android.view.widget.ServiceItemView;

/**
 * Created by lfs on 2017/7/18.
 */

public interface SubscribeContract {
    interface View extends BaseView<Presenter> {
        void setHomeHolderVisible(boolean visible);

        void setSubmitButtonEnable(boolean enable);

        void showProgressDialog();

        void dismissProgressDialog();

        void submitSuccess(OrderBean orderBean);

        void submitFail();
    }

    interface Presenter extends BasePresenter {

        void loadShopDetail();

        LoginInfoModel getLoginInfo();

        void setDefaultValue(ServiceBean serviceBean, UserInfoBean userInfo);


        ServiceBean getDefaultService();

        UserInfoBean getDefaultUserInfo();

        void addServiceItem(ServiceItemView serviceItemView);

        void setServiceType(ServiceBean service);

        String getShopId();

        String getServiceId();

        String getOpenId();

        String getStartTime();

        String getEndTime();

        ArrayMap<Integer, ServiceItemView> getServiceViews();

        ArrayMap<Integer, ServiceBean> getChooseServices();

        ArrayMap<Integer, UserInfoBean> getChooseStaff();

        void putChooseService(Integer key, ServiceBean service);

        void putChooseStaff(Integer key, UserInfoBean staff);

        void setChooseTime(long timestamp);

        void setPhone(String phone);

        void setName(String name);

        void setLocation(String location);

        void setAddress(String address);

        void setGender(int gender);

        void checkSubmitButtonEnable();

        void submit();

        void setShopId(String shopId);
    }
}
