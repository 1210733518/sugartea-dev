package com.dianjiake.android.ui.main;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.dianjiake.android.base.App;
import com.dianjiake.android.data.db.AppInfoDBHelper;
import com.dianjiake.android.data.db.LoginInfoDBHelper;
import com.dianjiake.android.event.LocationEvent;
import com.dianjiake.android.util.AMapUtil;
import com.dianjiake.android.util.EventUtil;
import com.igexin.sdk.PushManager;

import timber.log.Timber;

/**
 * Created by lfs on 2017/6/7.
 */

public class MainPresenter implements MainContract.Presenter {
    AMapLocationClient client;

    @Override
    public void start() {
        client = AMapUtil.getClient();
        PushManager.getInstance().bindAlias(App.getInstance(), LoginInfoDBHelper.newInstance().getLoginInfo().getOpenId());
        startLocation();
    }

    @Override
    public void onDestroy() {
        client.stopLocation();
        client.onDestroy();
    }


    @Override
    public void startLocation() {
        client.setLocationListener(listener);
        client.startLocation();
    }

    AMapLocationListener listener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation.getErrorCode() == 0) {
                Timber.d("location:" + aMapLocation.getLongitude() + " " + aMapLocation.getLatitude() + " " + aMapLocation.toString());
                AppInfoDBHelper.newInstance().saveLocationInfo(aMapLocation.getPoiName(), aMapLocation.getLongitude() + "",
                        aMapLocation.getLatitude() + "", aMapLocation.getCityCode());
                EventUtil.postLocationEvent(new LocationEvent(aMapLocation.getPoiName(), aMapLocation.getLongitude() + "", aMapLocation.getLatitude() + ""));
                client.setLocationListener(null);
                client.stopLocation();
            }
        }
    };
}
