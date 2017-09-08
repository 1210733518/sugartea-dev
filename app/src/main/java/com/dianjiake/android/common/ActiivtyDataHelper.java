package com.dianjiake.android.common;


import android.app.Service;
import android.content.Intent;

import com.amap.api.services.core.PoiItem;
import com.dianjiake.android.data.bean.OrderBean;
import com.dianjiake.android.data.bean.ServiceBean;
import com.dianjiake.android.data.bean.UserInfoBean;

/**
 * Created by Sacowiw on 2017/6/15.
 */

public class ActiivtyDataHelper {

    private static final String KEY_SS = "keyss";
    private static final String KEY_STAFF_SERVICE = "keysts";
    private static final String KEY_TIME = "keytime";
    private static final String KEY_POI = "keypoi";
    private static final String KEY_ORDER = "keyorder";
    private static final String KEY_TEXT = "keytext";

    public static Intent getServiceStaffData(UserInfoBean item) {
        Intent intent = new Intent();
        intent.putExtra(KEY_SS, item);
        return intent;
    }

    public static UserInfoBean getServiceStaffBean(Intent intent) {
        return intent.getParcelableExtra(KEY_SS);
    }

    public static Intent getStaffServiceData(ServiceBean item) {
        Intent intent = new Intent();
        intent.putExtra(KEY_STAFF_SERVICE, item);
        return intent;
    }

    public static ServiceBean getStaffServiceBean(Intent intent) {
        return intent.getParcelableExtra(KEY_STAFF_SERVICE);
    }

    public static Intent getChooseTimeData(long timestamp) {
        Intent intent = new Intent();
        intent.putExtra(KEY_TIME, timestamp);
        return intent;
    }

    public static Long getChooseTime(Intent data) {
        return data.getLongExtra(KEY_TIME, System.currentTimeMillis());
    }

    public static Intent getPoiItemData(PoiItem item) {
        Intent intent = new Intent();
        intent.putExtra(KEY_POI, item);
        return intent;
    }

    public static PoiItem getPoiItem(Intent intent) {
        return intent.getParcelableExtra(KEY_POI);
    }

    public static Intent getOrderData(OrderBean order) {
        Intent intent = new Intent();
        intent.putExtra(KEY_ORDER, order);
        return intent;
    }

    public static OrderBean getOrderBean(Intent intent) {
        return intent.getParcelableExtra(KEY_ORDER);
    }

    public static Intent getTextIntent(String str) {
        Intent intent = new Intent();
        intent.putExtra(KEY_TEXT, str);
        return intent;
    }

    public static String getText(Intent intent) {
        return intent.getStringExtra(KEY_TEXT);
    }
}
