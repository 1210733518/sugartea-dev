package com.dianjiake.android.ui.subscribe;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.dianjiake.android.api.Network;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.data.bean.AddOrderBean;
import com.dianjiake.android.data.bean.BaseBean;
import com.dianjiake.android.data.bean.ServiceBean;
import com.dianjiake.android.data.bean.ShopDetailBean;
import com.dianjiake.android.data.bean.UserInfoBean;
import com.dianjiake.android.data.db.LoginInfoDBHelper;
import com.dianjiake.android.data.model.LoginInfoModel;
import com.dianjiake.android.util.CheckEmptyUtil;
import com.dianjiake.android.util.CheckStringUtil;
import com.dianjiake.android.view.widget.ServiceItemView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lfs on 2017/7/18.
 */

public class SubscribePresenter implements SubscribeContract.Presenter {
    private static final int SERVICE_TYPE_HOME = 1;//到家
    private static final int SERVICE_TYPE_SHOP = 2;//到店
    CompositeDisposable cd;
    SubscribeContract.View view;
    LoginInfoModel loginInfo;

    ArrayMap<Integer, ServiceItemView> servicesViews;
    ArrayMap<Integer, ServiceBean> chooseServices;
    ArrayMap<Integer, UserInfoBean> chooseStaffs;
    ServiceBean defaultService;
    UserInfoBean defaultUserInfo;
    long chooseTime;
    String phone, name;
    String location = "";
    String address = "";
    int gender;

    int otherServiceTag = 0;
    String shopId, serviceId, staffOpenId;
    int serviceType;

    String startTime = "8:00", endTime = "22:00";


    public SubscribePresenter(SubscribeContract.View view) {
        this.view = view;
        loginInfo = LoginInfoDBHelper.newInstance().getLoginInfo();
        cd = new CompositeDisposable();
        servicesViews = new ArrayMap<>();
        chooseServices = new ArrayMap<>();
        chooseStaffs = new ArrayMap<>();
    }

    @Override
    public void start() {
    }

    @Override
    public void onDestroy() {
        cd.clear();
    }

    @Override
    public void loadShopDetail() {
        cd.clear();
        Network.getInstance().shopDetail(BSConstant.SHOP_DETAIL, loginInfo.getOpenId(), shopId, null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new Observer<BaseBean<ShopDetailBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onNext(@NonNull BaseBean<ShopDetailBean> shopDetail) {
                        if (shopDetail.getCode() == 200 && shopDetail.getObj() != null && shopDetail.getObj().getDianpu() != null) {
                            startTime = shopDetail.getObj().getDianpu().getKaishishijian();
                            endTime = shopDetail.getObj().getDianpu().getJieshushijian();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public LoginInfoModel getLoginInfo() {
        return loginInfo;
    }

    @Override
    public void setDefaultValue(ServiceBean serviceBean, UserInfoBean userInfo) {
        defaultService = serviceBean;
        defaultUserInfo = userInfo;
        if (serviceBean != null) {
            shopId = serviceBean.getShanghuid();
            serviceId = serviceBean.getId();
            setServiceType(serviceBean);
            putChooseService(0, serviceBean);
        }
        if (userInfo != null) {
            shopId = userInfo.getShanghuid();
            staffOpenId = userInfo.getOpenid();
            putChooseStaff(0, userInfo);
        }
        loadShopDetail();
    }

    @Override
    public ServiceBean getDefaultService() {
        return defaultService;
    }

    @Override
    public UserInfoBean getDefaultUserInfo() {
        return defaultUserInfo;
    }

    @Override
    public void addServiceItem(ServiceItemView serviceItemView) {
        serviceItemView.setTag(otherServiceTag);
        if (otherServiceTag == 0) {
            serviceItemView.initServiceAndStaff(defaultService, defaultUserInfo);
        } else {
            serviceItemView.initServiceAndStaff(null, defaultUserInfo);
        }
        if (defaultUserInfo != null) {
            putChooseStaff(otherServiceTag, defaultUserInfo);
        }
        servicesViews.put((int) serviceItemView.getTag(), serviceItemView);
        otherServiceTag++;
    }

    @Override
    public void setServiceType(ServiceBean service) {
        serviceType = "1".equals(service.getFuwumoshi()) ? SERVICE_TYPE_HOME : SERVICE_TYPE_SHOP;
        view.setHomeHolderVisible(serviceType == SERVICE_TYPE_HOME);
    }

    @Override
    public String getShopId() {
        return shopId;
    }

    @Override
    public String getServiceId() {
        return serviceId;
    }

    @Override
    public String getOpenId() {
        return staffOpenId;
    }

    @Override
    public String getStartTime() {
        return startTime;
    }

    @Override
    public String getEndTime() {
        return endTime;
    }

    @Override
    public ArrayMap<Integer, ServiceItemView> getServiceViews() {
        return servicesViews;
    }

    @Override
    public ArrayMap<Integer, ServiceBean> getChooseServices() {
        return chooseServices;
    }

    @Override
    public ArrayMap<Integer, UserInfoBean> getChooseStaff() {
        return chooseStaffs;
    }

    @Override
    public void putChooseService(Integer key, ServiceBean service) {
        if (key == 0) {
            setServiceType(service);
        }
        chooseServices.put(key, service);
        servicesViews.get(key).setService(service);
        checkSubmitButtonEnable();
    }

    @Override
    public void putChooseStaff(Integer key, UserInfoBean staff) {
        chooseStaffs.put(key, staff);
        servicesViews.get(key).setStaff(staff);
        checkSubmitButtonEnable();
    }

    @Override
    public void setChooseTime(long timestamp) {
        this.chooseTime = timestamp;
        checkSubmitButtonEnable();
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
        checkSubmitButtonEnable();

    }

    @Override
    public void setName(String name) {
        this.name = name;
        checkSubmitButtonEnable();

    }

    @Override
    public void setLocation(String location) {
        this.location = location;
        checkSubmitButtonEnable();

    }

    @Override
    public void setAddress(String address) {
        this.address = address;
        checkSubmitButtonEnable();

    }

    @Override
    public void setGender(int gender) {
        this.gender = gender;
        checkSubmitButtonEnable();

    }


    @Override
    public void checkSubmitButtonEnable() {
        if (chooseStaffs.size() < servicesViews.size() || chooseServices.size() < servicesViews.size() || chooseTime == 0 ||
                !CheckStringUtil.isPhoneNumber(phone) || CheckEmptyUtil.isEmpty(name)) {
            view.setSubmitButtonEnable(false);
            return;
        }
        if (serviceType == SERVICE_TYPE_HOME) {
            if (CheckEmptyUtil.isEmpty(location) || CheckEmptyUtil.isEmpty(address)) {
                view.setSubmitButtonEnable(false);
                return;
            }
        }
        view.setSubmitButtonEnable(true);
        return;

    }

    @Override
    public void submit() {
        view.showProgressDialog();
        cd.clear();

        StringBuilder services = new StringBuilder();
        for (Integer key : servicesViews.keySet()) {
            services.append(chooseServices.get(key).getId())
                    .append("-")
                    .append(chooseServices.get(key).getName())
                    .append("-")
                    .append(chooseStaffs.get(key).getOpenid())
                    .append("-")
                    .append(chooseServices.get(key).getJine())
                    .append(",");
            Log.e("guaju", "submit: "+services );
        }
        if (services.length() > 0) {
            services.deleteCharAt(services.length() - 1);
        }

        Network.getInstance().addService(
                BSConstant.ADD_SERVICE,
                loginInfo.getOpenId(),
                name,
                phone,
                chooseTime / 1000,
                servicesViews.size(),
                serviceType,
                1,
                gender,
                location + address,
                shopId,
                services.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new Observer<BaseBean<AddOrderBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        cd.add(d);
                    }


                    @Override
                    public void onNext(@NonNull BaseBean<AddOrderBean> baseBean) {
                        view.dismissProgressDialog();
                        if (baseBean.getCode() == 200) {
                            view.submitSuccess(baseBean.getObj().getDingdanxiangqiang());
                        } else {
                            view.submitFail();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.dismissProgressDialog();
                        view.submitFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
