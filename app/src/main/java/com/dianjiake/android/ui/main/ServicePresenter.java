package com.dianjiake.android.ui.main;

import com.dianjiake.android.api.Network;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.data.bean.BaseBean;
import com.dianjiake.android.data.bean.ServiceFirstBean;
import com.dianjiake.android.data.bean.ServiceSecondBean;
import com.dianjiake.android.data.bean.ServiceTypeBean;
import com.dianjiake.android.data.db.MsgDBHelper;
import com.dianjiake.android.util.CheckEmptyUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lfs on 2017/7/25.
 */

public class ServicePresenter implements ServiceContract.Presenter {
    CompositeDisposable cd;
    ServiceContract.View view;
    List<ServiceSecondBean> serviceSeconds;
    List<ServiceFirstBean> serviceFirsts;
    MsgDBHelper msgDBHelper;

    public ServicePresenter(ServiceContract.View view) {
        this.view = view;
        cd = new CompositeDisposable();
        serviceSeconds = new ArrayList<>();
        serviceFirsts = new ArrayList<>();
        msgDBHelper = MsgDBHelper.newInstance();
    }

    @Override
    public void start() {

    }

    @Override
    public void onDestroy() {
        cd.clear();
    }

    @Override
    public void load() {
        cd.clear();
        Network.getInstance().serviceType(BSConstant.SERVICE_TYPE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new Observer<BaseBean<ServiceTypeBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onNext(@NonNull BaseBean<ServiceTypeBean> service) {
                        if (service.getCode() == 200 && service.getObj().getLeibie() != null) {
                            serviceFirsts.clear();
                            serviceSeconds.clear();
                            for (ServiceFirstBean bean : service.getObj().getLeibie()) {
                                addSecondBean(bean);
                            }
                            serviceFirsts.addAll(service.getObj().getLeibie());
                            view.setItems(service.getObj().getLeibie(), serviceSeconds);

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
    public boolean haveUnreadMsg() {
        return msgDBHelper.isUnread();
    }

    @Override
    public List<ServiceFirstBean> getServiceFirsts() {
        return serviceFirsts;
    }


    private void addSecondBean(ServiceFirstBean bean) {
        ServiceSecondBean titleBean = new ServiceSecondBean();
        titleBean.setViewType(ServiceType.TITLE);
        titleBean.setViewTitle(bean.getMingcheng());
        serviceSeconds.add(titleBean);
        if (!CheckEmptyUtil.isEmpty(bean.getLeibies())) {
            serviceSeconds.addAll(bean.getLeibies());
        }
    }

    @Override
    public List<ServiceSecondBean> getServiceSeconds() {
        return serviceSeconds;
    }
}
