package com.dianjiake.android.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dianjiake.android.data.model.AppInfoModelDao;
import com.dianjiake.android.data.model.DaoMaster;
import com.dianjiake.android.data.model.LoginInfoModelDao;
import com.dianjiake.android.data.model.MsgModelDao;
import com.dianjiake.android.data.model.PhoneInfoModelDao;
import com.dianjiake.android.data.model.SearchHistoryModelDao;


/**
 * Created by Sacowiw on 2017/6/13.
 */

public class ProductionOpenHelp extends DaoMaster.OpenHelper {

    public ProductionOpenHelp(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, AppInfoModelDao.class, LoginInfoModelDao.class, PhoneInfoModelDao.class, SearchHistoryModelDao.class, MsgModelDao.class);
    }
}
