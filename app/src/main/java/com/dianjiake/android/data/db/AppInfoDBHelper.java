package com.dianjiake.android.data.db;


import com.dianjiake.android.data.model.AppInfoModel;
import com.dianjiake.android.data.model.AppInfoModelDao;
import com.dianjiake.android.util.CheckEmptyUtil;

import java.util.List;

/**
 * Created by Sacowiw on 2017/5/17.
 */

public class AppInfoDBHelper {
    private static final String DEFAULT_LOCAION_NAME = "SOHO现代城";
    private static final String DEFAULT_LONGITUDE = "116.4758400000";//默认经度
    private static final String DEFAULT_LATITUDE = "39.9060700000";//默认纬度


    public static AppInfoDBHelper newInstance() {
        return new AppInfoDBHelper();
    }

    private AppInfoDBHelper() {

    }

    public AppInfoModelDao getAppInfoDao() {
        return DBManager.getInstance().getDaoSession().getAppInfoModelDao();
    }

    public AppInfoModel getAppInfo() {
        List<AppInfoModel> appInfoModelList = getAppInfoDao().queryBuilder().limit(1).list();
        if (CheckEmptyUtil.isEmpty(appInfoModelList)) {
            return new AppInfoModel();
        } else {
            return appInfoModelList.get(0);
        }
    }

    public void save(AppInfoModel appInfoModel) {
        if (appInfoModel != null) {
            appInfoModel.setId(1L);
        }
        getAppInfoDao().insertOrReplace(appInfoModel);
    }

    public String getCid() {
        return getAppInfo().getCid();
    }

    public void saveCid(String cid) {
        AppInfoModel infoModel = getAppInfo();
        infoModel.setCid(cid);
        save(infoModel);
    }

    public void saveLocationInfo(String name, String longitude, String latitude, String cityCode) {
        AppInfoModel infoModel = getAppInfo();
        if (infoModel == null) {
            infoModel = new AppInfoModel();
            infoModel.setId(1l);
        }
        infoModel.setLongitude(longitude);
        infoModel.setLatitude(latitude);
        infoModel.setLocationName(name);
        infoModel.setCityCode(cityCode);
        getAppInfoDao().insertOrReplace(infoModel);
    }

    public String getLocationName() {
        if (CheckEmptyUtil.isEmpty(getAppInfo().getLocationName())) {
            return DEFAULT_LOCAION_NAME;
        } else {
            return getAppInfo().getLocationName();
        }
    }

    public String getLongitude() {
        if (CheckEmptyUtil.isEmpty(getAppInfo().getLocationName())) {
            return DEFAULT_LONGITUDE;
        } else {
            return getAppInfo().getLongitude();
        }
    }

    public String getLatitude() {
        if (CheckEmptyUtil.isEmpty(getAppInfo().getLocationName())) {
            return DEFAULT_LATITUDE;
        } else {
            return getAppInfo().getLatitude();
        }
    }
}
