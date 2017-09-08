package com.dianjiake.android.data.db;

import com.dianjiake.android.data.model.SearchHistoryModel;
import com.dianjiake.android.data.model.SearchHistoryModelDao;
import com.dianjiake.android.util.CheckEmptyUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lfs on 2017/7/12.
 */

public class SearchHistoryHelper {
    private static final int TYPE_LOCATION = 0;
    private static final int TYPE_SHOP = 1;

    public static SearchHistoryHelper newInstance() {
        return new SearchHistoryHelper();
    }

    public SearchHistoryModelDao getDao() {
        return DBManager.getInstance().getDaoSession().getSearchHistoryModelDao();
    }

    public List<SearchHistoryModel> getSearchLocationHistory() {
        return getSearchHistory(TYPE_LOCATION);
    }

    public List<SearchHistoryModel> getSearchShopHistory() {
        return getSearchHistory(TYPE_SHOP);
    }

    private List<SearchHistoryModel> getSearchHistory(int type) {
        List<SearchHistoryModel> list = getDao().queryBuilder().where(SearchHistoryModelDao.Properties.Type.eq(type))
                .orderDesc(SearchHistoryModelDao.Properties.Update_at)
                .list();
        if (CheckEmptyUtil.isEmpty(list)) {
            list = new ArrayList<>();
        }
        return list;
    }

    public void addSearchLocationEntity(String search) {
        SearchHistoryModel searchHistoryModel = new SearchHistoryModel();
        searchHistoryModel.setSearch(search);
        searchHistoryModel.setUpdate_at(System.currentTimeMillis());
        searchHistoryModel.setType(TYPE_LOCATION);
        getDao().insertOrReplace(searchHistoryModel);
    }

    public void addSearchShopEntity(String search) {
        SearchHistoryModel searchHistoryModel = new SearchHistoryModel();
        searchHistoryModel.setSearch(search);
        searchHistoryModel.setUpdate_at(System.currentTimeMillis());
        searchHistoryModel.setType(TYPE_SHOP);
        getDao().insertOrReplace(searchHistoryModel);
    }
}
