package com.dianjiake.android.ui.colloction;

import android.app.FragmentManager;

import com.dianjiake.android.api.Network;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.data.bean.BaseBean;
import com.dianjiake.android.data.bean.BaseListBean;
import com.dianjiake.android.data.bean.HomeShopBean;
import com.dianjiake.android.data.db.AppInfoDBHelper;
import com.dianjiake.android.data.db.LoginInfoDBHelper;
import com.dianjiake.android.data.model.AppInfoModel;
import com.dianjiake.android.data.model.LoginInfoModel;
import com.dianjiake.android.util.CheckEmptyUtil;
import com.dianjiake.android.util.ToastUtil;

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

public class CollectionPresenter implements CollectionContract.Presenter {
    CollectionContract.View view;
    CompositeDisposable cd;
    LoginInfoModel loginInfo;
    List<HomeShopBean> items;
    AppInfoModel appInfo;

    public CollectionPresenter(CollectionContract.View view) {
        this.view = view;
        cd = new CompositeDisposable();
        loginInfo = LoginInfoDBHelper.newInstance().getLoginInfo();
        appInfo = AppInfoDBHelper.newInstance().getAppInfo();
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
        load(true);
    }

    @Override
    public void load(boolean isReload) {
        cd.clear();
        Network.getInstance().myCollection(BSConstant.MY_COLLECTION, loginInfo.getOpenId(), appInfo.getLongitude() + "," + appInfo.getLatitude())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new Observer<BaseListBean<HomeShopBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onNext(@NonNull BaseListBean<HomeShopBean> listBean) {
                        items.clear();
                        if (CheckEmptyUtil.isEmpty(listBean.getObj().getList())) {
                            view.loadEmptyContent();
                        } else {
                            items.addAll(listBean.getObj().getList());
                            view.loadAll();
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
    public List<HomeShopBean> getItems() {
        return items;
    }

    @Override
    public FragmentManager getFM() {
        return view.provideFM();
    }

    @Override
    public void deleteItem(final HomeShopBean shopBean, final int position) {
        view.showProgress();
        Network.getInstance().deleteCollection(BSConstant.DELETE_COLLECTION, loginInfo.getOpenId(), shopBean.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onNext(@NonNull BaseBean bean) {
                        view.dismissProgress();
                        if (bean.getCode() == 200) {
                            removeItem(shopBean, position);
                            ToastUtil.showShortToast("删除成功");
                        } else {
                            ToastUtil.showShortToast("删除失败");

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.dismissProgress();
                        ToastUtil.showShortToast("删除失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void removeItem(HomeShopBean shopBean, int position) {
        if (items.size() > position) {
            items.remove(position);
            if (items.size() > 0) {
                view.loadAll();
            } else {
                view.loadEmptyContent();
            }
        }
    }
}
