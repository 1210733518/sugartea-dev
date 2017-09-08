package com.dianjiake.android.ui.searchshop;

import com.dianjiake.android.api.Network;
import com.dianjiake.android.base.BaseListPresenter;
import com.dianjiake.android.base.BaseListView;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.data.bean.BaseListBean;
import com.dianjiake.android.data.bean.HomeShopBean;
import com.dianjiake.android.data.db.AppInfoDBHelper;
import com.dianjiake.android.data.db.LoginInfoDBHelper;
import com.dianjiake.android.data.model.AppInfoModel;
import com.dianjiake.android.data.model.LoginInfoModel;
import com.dianjiake.android.event.SearchShopEvent;
import com.dianjiake.android.request.ListObserver;
import com.dianjiake.android.ui.main.ServiceType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lfs on 2017/7/12.
 */

public abstract class BaseSearchPresenter<T, V extends BaseListView> implements BaseListPresenter {
    protected V view;
    List<T> items;
    int page = 1;
    String keyword;
    String typeId;
    CompositeDisposable cd;
    AppInfoModel appInfo;
    LoginInfoModel loginInfo;
    int searchType;

    public BaseSearchPresenter(V view) {
        super();
        this.view = view;
        items = new ArrayList<>();
        cd = new CompositeDisposable();
        appInfo = AppInfoDBHelper.newInstance().getAppInfo();
        loginInfo = LoginInfoDBHelper.newInstance().getLoginInfo();
        EventBus.getDefault().register(this);
    }

    public BaseSearchPresenter(V view, String id) {
        this(view);
        this.typeId = id;
        searchType = SearchType.RETRIEVE;
    }

    private BaseSearchPresenter() {

    }

    @Override
    public void start() {


    }


    @Override
    public void onDestroy() {
        cd.clear();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void reload() {
        items.clear();
        page = 1;
        load(true);
    }

    @Override
    public void load(final boolean isReload) {
        cd.clear();
        if (isReload) {
            view.loading();
        }
        provideNetwork()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ListObserver<T>() {
                    @Override
                    public void onSuccess(List<T> list, boolean isAll) {
                        if (isReload) {
                            items.clear();
                        }
                        items.addAll(list);
                        page++;
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

    public List<T> getItems() {
        return items;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setKeyword(SearchShopEvent event) {
        this.keyword = event.getSearch();
        reload();
    }

    public abstract Observable<BaseListBean<T>> provideNetwork();
}
