package com.dianjiake.android.util;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.dianjiake.android.base.App;

/**
 * Created by lfs on 2017/7/10.
 */

public class AMapUtil {

    /**
     * 格式化距离
     */
    public static String formatDistance(long distance) {
        if (distance < 1000) {
            return distance + "m";
        } else {
            float d = distance / (float) 1000;
            return String.format("%.2f", d) + "km";
        }
    }


    public static AMapLocationClient getClient() {
        AMapLocationClientOption locationClientOption = new AMapLocationClientOption();
        AMapLocationClient client = new AMapLocationClient(App.getInstance());
        locationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        locationClientOption.setInterval(2000);
        client.setLocationOption(locationClientOption);
        return client;
    }
}
