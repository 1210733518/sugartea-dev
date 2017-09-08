package com.dianjiake.android.ui.searchshop;

import com.dianjiake.android.api.Network;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.data.bean.BaseListBean;
import com.dianjiake.android.data.bean.ServiceBean;
import com.dianjiake.android.request.ListObserver;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lfs on 2017/7/12.
 */

public class ServiceResultPresenter extends BaseSearchPresenter<ServiceBean, ServiceResultContract.View> {


    public ServiceResultPresenter(ServiceResultContract.View view) {
        super(view);
    }

    public ServiceResultPresenter(ServiceResultContract.View view, String typeId) {
        super(view, typeId);
    }

    @Override
    public Observable<BaseListBean<ServiceBean>> provideNetwork() {
        if (searchType == SearchType.SEARCH) {
            return Network.getInstance().searchService(
                    BSConstant.SEARCH_SERVICE,
                    loginInfo.getOpenId(),
                    keyword,
                    0,
                    appInfo.getLongitude() + "," + appInfo.getLatitude(),
                    page);
        } else {
            return Network.getInstance().retrieveService(
                    BSConstant.RETRIEVE_SERVICE,
                    loginInfo.getOpenId(),
                    typeId,
                    0,
                    appInfo.getLongitude() + "," + appInfo.getLatitude(),
                    page);
        }

    }

}
