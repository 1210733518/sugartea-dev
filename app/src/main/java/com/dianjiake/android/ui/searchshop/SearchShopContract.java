package com.dianjiake.android.ui.searchshop;

import com.dianjiake.android.base.BasePresenter;
import com.dianjiake.android.base.BaseView;
import com.dianjiake.android.data.model.SearchHistoryModel;

import java.util.List;

/**
 * Created by lfs on 2017/7/12.
 */

public interface SearchShopContract {
    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {
        void addSearchHistory(String search);

        List<SearchHistoryModel> getSearchHistory();
    }
}
