package com.dianjiake.android.ui.coupon;

import com.dianjiake.android.api.Network;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.data.bean.BaseListBean;
import com.dianjiake.android.data.bean.MyCouponBean;
import com.dianjiake.android.data.db.LoginInfoDBHelper;
import com.dianjiake.android.data.model.LoginInfoModel;
import com.dianjiake.android.request.ListObserver;

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

public class CouponPresenter implements CouponContract.Presenter {
    CompositeDisposable cd;
    LoginInfoModel loginInfo;
    List<MyCouponBean> items;
    CouponContract.View view;
    int page = 1;

    public CouponPresenter(CouponContract.View view) {
        this.view = view;
        cd = new CompositeDisposable();
        loginInfo = LoginInfoDBHelper.newInstance().getLoginInfo();
        items = new ArrayList<>();
    }

    @Override
    public void start() {

    }

    @Override
    public void onDestroy() {
        cd.clear();
    }

    @Override
    public void reload() {
        page = 1;
        load(true);
    }

    @Override
    public void load(final boolean isReload) {
        cd.clear();
        Network.getInstance().myCoupon(BSConstant.MY_COUPON, loginInfo.getOpenId(), page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new ListObserver<MyCouponBean>() {
                    @Override
                    public void onSuccess(List<MyCouponBean> list, boolean isAll) {
                        if (isReload) {
                            items.clear();
                        }
                        items.addAll(list);
                        if (isAll) {
                            view.loadAll();
                        } else {
                            view.loadComplete();
                        }
                    }

                    @Override
                    public void onEmpty() {
                        view.loadEmptyContent();
                    }

                    @Override
                    public void onAll() {
                        view.loadAll();
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.loadNetworkError();
                    }
                });
    }

    @Override
    public List<MyCouponBean> getItems() {
        return items;
    }
}
