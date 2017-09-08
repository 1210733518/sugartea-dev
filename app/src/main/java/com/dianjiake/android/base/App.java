package com.dianjiake.android.base;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.dianjiake.android.constant.Constant;
import com.dianjiake.android.data.db.DBManager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.utils.Log;

import timber.log.Timber;

/**
 * Created by lfs on 2017/5/11.
 */

public class App extends MultiDexApplication {
    public static Context instance;

    public static Context getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initLibraries();

    }

    private void initLibraries() {
        Fresco.initialize(this);//初始化fresco
        DBManager.getInstance();
        if (Constant.IS_DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Stetho.initializeWithDefaults(this);
        }
        CrashReport.initCrashReport(getApplicationContext(), "f20630151e", Constant.IS_DEBUG);
        CrashReport.setIsDevelopmentDevice(this, Constant.IS_DEBUG);
        Config.DEBUG = Constant.IS_DEBUG;
        UMShareAPI.get(this);
        Log.LOG = Constant.IS_DEBUG;
    }

    {
        PlatformConfig.setWeixin(Constant.WX_AppID, Constant.WX_AppSecret);
        PlatformConfig.setQQZone(Constant.QQ_ID, Constant.QQ_KEY);
    }
}
