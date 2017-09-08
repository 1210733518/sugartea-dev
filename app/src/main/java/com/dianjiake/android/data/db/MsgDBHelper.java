package com.dianjiake.android.data.db;

import com.dianjiake.android.data.model.MsgModel;
import com.dianjiake.android.data.model.MsgModelDao;
import com.dianjiake.android.util.CheckEmptyUtil;
import com.dianjiake.android.util.EventUtil;

import org.greenrobot.greendao.query.DeleteQuery;

import java.util.List;


/**
 * Created by lfs on 2017/7/26.
 */

public class MsgDBHelper {

    public static MsgDBHelper newInstance() {
        return new MsgDBHelper();
    }

    private MsgDBHelper() {
    }

    public MsgModelDao getMsgDao() {
        return DBManager.getInstance().getDaoSession().getMsgModelDao();
    }

    public void save(List<MsgModel> msgModel) {
        if (msgModel != null) {
            getMsgDao().insertOrReplaceInTx(msgModel);
            EventUtil.postCheckMsgUnread();
        }
    }

    public void save(MsgModel msgModel) {
        if (msgModel != null) {
            getMsgDao().insertOrReplace(msgModel);
            EventUtil.postCheckMsgUnread();
        }
    }

    public void delete(MsgModel msgModel) {
        if (msgModel != null) {
            DeleteQuery<MsgModel> deleteQuery= getMsgDao().queryBuilder()
                    .where(MsgModelDao.Properties.Id.eq(msgModel.getId()))
                    .buildDelete();
            deleteQuery.executeDeleteWithoutDetachingEntities();
            EventUtil.postCheckMsgUnread();
        }
    }

    public boolean isUnread() {
        return !CheckEmptyUtil.isEmpty(getMsgDao().queryBuilder().where(MsgModelDao.Properties.Chakan.eq(0)).list());
    }
}
