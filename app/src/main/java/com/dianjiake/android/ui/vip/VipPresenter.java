package com.dianjiake.android.ui.vip;

import com.dianjiake.android.api.Network;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.data.bean.BaseListBean;
import com.dianjiake.android.data.bean.VipBean;
import com.dianjiake.android.data.db.LoginInfoDBHelper;
import com.dianjiake.android.data.model.LoginInfoModel;

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

public class VipPresenter implements VipContract.Presenter {
    VipContract.View view;
    List<VipBean> items = new ArrayList<>();
    CompositeDisposable cd;
    LoginInfoModel loginInfo;

    public VipPresenter(VipContract.View view) {
        this.view = view;
        cd = new CompositeDisposable();
        loginInfo = LoginInfoDBHelper.newInstance().getLoginInfo();
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
        load(true);
    }

    @Override
    public void load(boolean isReload) {
        cd.clear();
        Network.getInstance().vipList(BSConstant.VIP_LIST, loginInfo.getOpenId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new Observer<BaseListBean<VipBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onNext(@NonNull BaseListBean<VipBean> vip) {
                        items.clear();
                        if (vip.getCode() == 200 && vip.getObj().getList() != null) {
                            items.addAll(vip.getObj().getList());
                            view.loadAll();
                        } else {
                            view.loadEmptyContent();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.loadNetworkError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public List<VipBean> getItems() {
        return items;
    }
}
