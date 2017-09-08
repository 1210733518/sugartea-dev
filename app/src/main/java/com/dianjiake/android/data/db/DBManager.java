package com.dianjiake.android.data.db;

import android.database.sqlite.SQLiteDatabase;

import com.dianjiake.android.base.App;
import com.dianjiake.android.data.model.DaoMaster;
import com.dianjiake.android.data.model.DaoSession;


/**
 * Created by lfs on 2017/5/15.
 */

public class DBManager {
    private static DBManager instance;

    private ProductionOpenHelp devOpenHelper;
    private SQLiteDatabase database;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private DBManager() {
        devOpenHelper = new ProductionOpenHelp(App.getInstance(), "qinlin-db", null);
        database = devOpenHelper.getWritableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }

    public static DBManager getInstance() {
        if (instance == null) {
            synchronized (DBManager.class) {
                if (instance == null) {
                    instance = new DBManager();
                }
            }
        }
        return instance;
    }

    public ProductionOpenHelp getOpenHelper() {
        return devOpenHelper;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public DaoMaster getDaoMaster() {
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
