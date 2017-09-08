package com.dianjiake.android.ui.searchshop;

import com.dianjiake.android.data.db.SearchHistoryHelper;
import com.dianjiake.android.data.model.SearchHistoryModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.internal.disposables.CancellableDisposable;

/**
 * Created by lfs on 2017/7/12.
 */

public class SearchShopPresenter implements SearchShopContract.Presenter {
    SearchShopContract.View view;
    CompositeDisposable cd;
    SearchHistoryHelper searchHistoryHelper;

    public SearchShopPresenter(SearchShopContract.View view) {
        this.view = view;
        cd = new CompositeDisposable();
        searchHistoryHelper = SearchHistoryHelper.newInstance();
    }

    @Override
    public void start() {

    }

    @Override
    public void onDestroy() {
        cd.clear();
    }

    @Override
    public void addSearchHistory(String search) {
        searchHistoryHelper.addSearchShopEntity(search);
    }

    @Override
    public List<SearchHistoryModel> getSearchHistory() {
        return searchHistoryHelper.getSearchShopHistory();
    }
}
