package com.dianjiake.android.ui.main;

import android.support.annotation.IdRes;
import android.widget.RadioGroup;

import com.dianjiake.android.R;
import com.dianjiake.android.api.Network;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.data.bean.HomeShopBean;
import com.dianjiake.android.data.db.AppInfoDBHelper;
import com.dianjiake.android.data.db.LoginInfoDBHelper;
import com.dianjiake.android.data.db.MsgDBHelper;
import com.dianjiake.android.data.model.AppInfoModel;
import com.dianjiake.android.data.model.LoginInfoModel;
import com.dianjiake.android.event.HomeReloadEvent;
import com.dianjiake.android.event.LocationEvent;
import com.dianjiake.android.event.LoginEvent;
import com.dianjiake.android.request.ListObserver;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lfs on 2017/7/7.
 */

public class HomePresenter implements HomeContract.Presenter, RadioGroup.OnCheckedChangeListener {
    int order;//0默认 2距离 1评分
    int page = 1;
    HomeContract.View view;
    LoginInfoModel loginInfo;
    CompositeDisposable cd;
    List<HomeShopBean> items = new ArrayList<>();
    AppInfoModel appInfo;

    String locationName;
    String longitude;
    String latitude;
    HomeTabHelper homeTabHelper;
    MsgDBHelper msgDBHelper;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
        loginInfo = LoginInfoDBHelper.newInstance().getLoginInfo();
        appInfo = AppInfoDBHelper.newInstance().getAppInfo();
        cd = new CompositeDisposable();

        locationName = appInfo.getLocationName();
        longitude = appInfo.getLongitude();
        latitude = appInfo.getLatitude();
        view.setLocationName(locationName);
        EventBus.getDefault().register(this);
        homeTabHelper = new HomeTabHelper();
        homeTabHelper.setCheckedChangeListener(this);
        msgDBHelper = MsgDBHelper.newInstance();
    }

    @Override
    public void start() {

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        cd.clear();
    }

    @Override
    public void filterLoad() {
        view.moveRecyclerView();
        page = 1;
        load(true);
    }

    @Override
    public void reload() {
        page = 1;
        load(true);
    }

    @Override
    public void load(final boolean isReload) {
        cd.clear();

//        Network.getInstance().homeShop(BSConstant.SHOP_LIST, loginInfo.getOpenId(), order, longitude + "," + latitude, page)
        Network.getInstance().homeShop(BSConstant.SHOP_LIST, "104cca5fad614b53e494e5198f4cdb47", order, longitude + "," + latitude, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new ListObserver<HomeShopBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.loadNetworkError();
                    }

                    @Override
                    public void onSuccess(List<HomeShopBean> list, boolean isAll) {
                        view.dismissPD();
                        if (isReload) {
                            EventBus.getDefault().post(new HomeReloadEvent());
                            items.clear();
                            HomeShopBean temp = new HomeShopBean();
                            temp.setViewType(HomeType.AD);
                            items.add(0, temp);
                            HomeShopBean collections = new HomeShopBean();
                            collections.setViewType(HomeType.COLLECTION);
                            items.add(collections);
                            //zai zhe ge di fang tian jia xin bu ju
                            HomeShopBean filter = new HomeShopBean();
                            filter.setViewType(HomeType.FILTER);
                            items.add(filter);

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
                        view.dismissPD();
                        view.loadEmptyContent();
                    }

                    @Override
                    public void onAll() {
                        view.dismissPD();
                        view.loadAll();
                    }
                });
    }


    @Override
    public List<HomeShopBean> getItems() {
        return items;
    }

    @Override
    public void addRG(RadioGroup rg) {
        homeTabHelper.addRG(rg);

    }

    @Override
    public boolean haveUnreadMsg() {
        return msgDBHelper.isUnread();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocationChange(LocationEvent event) {
        locationName = event.locationName;
        longitude = event.longitude;
        latitude = event.latitude;
        view.setLocationName(event.locationName);
        reload();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBindTabEvent(RadioGroup radioGroup) {
        homeTabHelper.addRG(radioGroup);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        loginInfo = LoginInfoDBHelper.newInstance().getLoginInfo();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.filter_base:
                order = 0;
                break;
            case R.id.filter_distance:
                order = 2;
                break;
            case R.id.filter_score:
                order = 1;
                break;
        }
        view.showPD();
        filterLoad();
    }
}
