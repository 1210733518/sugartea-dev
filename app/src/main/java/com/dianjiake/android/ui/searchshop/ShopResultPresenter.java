package com.dianjiake.android.ui.searchshop;

import com.dianjiake.android.api.Network;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.data.bean.BaseListBean;
import com.dianjiake.android.data.bean.HomeShopBean;
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

public class ShopResultPresenter extends BaseSearchPresenter<HomeShopBean, ShopResultContract.View> {

    public ShopResultPresenter(ShopResultContract.View view) {
        super(view);
    }

    public ShopResultPresenter(ShopResultContract.View view, String typeId) {
        super(view, typeId);
    }

    @Override
    public Observable<BaseListBean<HomeShopBean>> provideNetwork() {
        if (searchType == SearchType.SEARCH) {
            return Network.getInstance().searchShop(
                    BSConstant.SEARCH_SHOP,
                    loginInfo.getOpenId(),
                    keyword,
                    0,
                    appInfo.getLongitude() + "," + appInfo.getLatitude(),
                    page);
        } else {
            return Network.getInstance().retrieveShop(BSConstant.RETRIEVE_SHOP,
                    loginInfo.getOpenId(),
                    typeId,
                    0,
                    appInfo.getLongitude() + "," + appInfo.getLatitude(),
                    page);
        }
    }


}
