package com.dianjiake.android.ui.searchlocation;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.dianjiake.android.data.db.AppInfoDBHelper;
import com.dianjiake.android.data.db.SearchHistoryHelper;
import com.dianjiake.android.data.model.AppInfoModel;
import com.dianjiake.android.data.model.SearchHistoryModel;
import com.dianjiake.android.util.CheckEmptyUtil;

import java.util.List;

/**
 * Created by lfs on 2017/7/11.
 */

public class SearchLocationPresenter implements SearchLocationContract.Presenter, PoiSearch.OnPoiSearchListener {
    SearchLocationContract.View view;
    PoiSearch poiSearch;
    SearchHistoryHelper searchHistoryHelper;
    AppInfoModel appInfo;

    boolean isGeoSearch;

    public SearchLocationPresenter(SearchLocationContract.View view) {
        this.view = view;

        PoiSearch.Query query = new PoiSearch.Query("", "120302");
        poiSearch = new PoiSearch(view.getContext(), query);
        poiSearch.setOnPoiSearchListener(this);
        searchHistoryHelper = SearchHistoryHelper.newInstance();
        appInfo = AppInfoDBHelper.newInstance().getAppInfo();
    }

    @Override
    public void start() {

    }

    @Override
    public void onDestroy() {
        poiSearch.setOnPoiSearchListener(null);
    }

    @Override
    public void geoSearch(LatLng latLng) {
        isGeoSearch = true;
        PoiSearch.Query query = new PoiSearch.Query("", "120302");
        poiSearch.setQuery(query);
        LatLonPoint latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
        poiSearch.setBound(new PoiSearch.SearchBound(latLonPoint, 1000));
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void poiSearch(String keyword) {
        isGeoSearch = false;
        PoiSearch.Query query = new PoiSearch.Query(keyword, "", appInfo.getCityCode());
        poiSearch.setBound(null);
        poiSearch.setQuery(query);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void addSearchHistory(String search) {
        searchHistoryHelper.addSearchLocationEntity(search);
    }

    @Override
    public List<SearchHistoryModel> getSearchHistory() {
        return searchHistoryHelper.getSearchLocationHistory();
    }

    @Override
    public void reLocation() {
        view.reLocation();
    }

    @Override
    public void chooseLocation(PoiItem item) {
        view.chooseLocation(item);
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (!CheckEmptyUtil.isEmpty(poiResult.getPois())) {
            LatLonPoint latLonPoint = poiResult.getPois().get(0).getLatLonPoint();
            LatLng latLng = new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
            if (!isGeoSearch) {
                view.mapMoveToLocation(latLng);
            }
        }
        view.setItems(poiResult.getPois());
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
