package com.dianjiake.android.data.db;


import com.dianjiake.android.data.model.PhoneInfoModel;
import com.dianjiake.android.data.model.PhoneInfoModelDao;
import com.dianjiake.android.util.CheckEmptyUtil;

import java.util.List;

/**
 * Created by lfs on 2017/5/15.
 */

public class PhoneInfoDBHelper {
    public static PhoneInfoModelDao getPhoneInfoDao() {
        return DBManager.getInstance().getDaoSession().getPhoneInfoModelDao();
    }

    public static PhoneInfoModel getPhoneInfoModel() {
        List<PhoneInfoModel> phoneInfoModels = getPhoneInfoDao().queryBuilder().limit(1).build().list();
        if (CheckEmptyUtil.isEmpty(phoneInfoModels)) {
            return new PhoneInfoModel();
        } else {
            return phoneInfoModels.get(0);
        }
    }

    public static void savePhoneInfo(PhoneInfoModel phoneInfo) {
        getPhoneInfoDao().insertOrReplace(phoneInfo);
    }

    public static String getIPAddress() {
        List<PhoneInfoModel> phoneInfoModels = getPhoneInfoDao().queryBuilder().limit(1).build().list();
        if (CheckEmptyUtil.isEmpty(phoneInfoModels)) {
            return "127.0.0.1";
        } else {
            return phoneInfoModels.get(0).getIp();
        }
    }

    public static String getMac() {
        List<PhoneInfoModel> phoneInfoModels = getPhoneInfoDao().queryBuilder().limit(1).build().list();
        if (CheckEmptyUtil.isEmpty(phoneInfoModels)) {
            return "....";
        } else {
            return phoneInfoModels.get(0).getMac();
        }
    }

    public static int getScreenWidth() {
        List<PhoneInfoModel> phoneInfoModels = getPhoneInfoDao().queryBuilder().limit(1).build().list();
        if (CheckEmptyUtil.isEmpty(phoneInfoModels)) {
            return 0;
        } else {
            return phoneInfoModels.get(0).getScreenWidth();
        }
    }

    public static int getScreenHeight() {
        List<PhoneInfoModel> phoneInfoModels = getPhoneInfoDao().queryBuilder().limit(1).build().list();
        if (CheckEmptyUtil.isEmpty(phoneInfoModels)) {
            return 0;
        } else {
            return phoneInfoModels.get(0).getScreenHeight();
        }
    }
}
