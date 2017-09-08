package com.dianjiake.android.ui.searchlocation;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.PoiItem;
import com.dianjiake.android.R;
import com.dianjiake.android.base.App;
import com.dianjiake.android.base.BaseTranslateActivity;
import com.dianjiake.android.common.ActiivtyDataHelper;
import com.dianjiake.android.data.db.AppInfoDBHelper;
import com.dianjiake.android.data.model.AppInfoModel;
import com.dianjiake.android.event.LocationEvent;
import com.dianjiake.android.ui.common.SearchHistoryAdapter;
import com.dianjiake.android.util.EventUtil;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.util.ToastUtil;
import com.dianjiake.android.util.UIUtil;
import com.dianjiake.android.view.widget.ToolbarSpaceView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by lfs on 2017/7/11.
 */

public class SearchLocationActivity extends BaseTranslateActivity<SearchLocationContract.Presenter> implements SearchLocationContract.View {
    private static final String TYPE_CHANGE_LOCATION = "change";//首页跳转过来
    private static final String TYPE_CHOOSE_LOCATION = "choose";//填写资料，下单跳转过来

    public static Intent getChooseLocationIntent() {
        Intent intent = IntentUtil.getIntent(SearchLocationActivity.class);
        intent.setType(TYPE_CHOOSE_LOCATION);
        return intent;
    }

    @BindView(R.id.toolbar_space)
    ToolbarSpaceView toolbarSpace;
    @BindView(R.id.toolbar_input)
    EditText toolbarInput;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.history_holder)
    View historyHolder;
    @BindView(R.id.history)
    ListView history;


    String type;
    AMap map;
    Marker marker;
    SearchResultAdapter searchResultAdapter;
    boolean searchFocus;//搜索框是否获取焦点
    InputMethodManager imm;
    SearchHistoryAdapter searchHistoryAdapter;
    boolean isMoveMapSearch = true;

    int markerPositionX, markerPositionY;


    @Override
    public void setPresenter(SearchLocationContract.Presenter presenter) {

    }

    @Override
    public int provideContentView() {
        return R.layout.activity_search_location;
    }

    @Override
    public void create(@Nullable Bundle savedInstanceState) {
        type = getIntent().getType();
        imm = (InputMethodManager) App.getInstance().getSystemService(INPUT_METHOD_SERVICE);
        mapView.onCreate(savedInstanceState);
        map = mapView.getMap();
        intiMapView();
        markerPositionX = UIUtil.getScreenWidth() / 2;
        markerPositionY = UIUtil.getScreenWidth() / 5;
        searchResultAdapter = new SearchResultAdapter(presenter);
        listView.setAdapter(searchResultAdapter);
        searchHistoryAdapter = new SearchHistoryAdapter();
        history.setAdapter(searchHistoryAdapter);

        toolbarInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchLocation(v.getText().toString());
                }
                return false;
            }
        });

        history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchLocation(searchHistoryAdapter.getItem(position).getSearch());
            }
        });

    }

    private void intiMapView() {
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setRotateGesturesEnabled(false);
        reLocation();
        map.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mapMoveToLocation(latLng);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.draggable(false);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_location)));
                marker = map.addMarker(markerOptions);
                marker.setPositionByPixels(markerPositionX, markerPositionY);
            }
        });

        map.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                if (marker != null && isMoveMapSearch) {
                    Timber.d("position :" + marker.getPosition().toString());
                    presenter.geoSearch(marker.getPosition());
                } else {
                    isMoveMapSearch = true;
                }
            }
        });

    }


    @Override
    public SearchLocationContract.Presenter getPresenter() {
        return new SearchLocationPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        imm = null;
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (searchFocus) {
            setSearchFocus(false);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.toolbar_right)
    void clickCancel(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    @OnClick(R.id.toolbar_title)
    void clickSearch(View v) {
        setSearchFocus(true);
    }

    @OnClick(R.id.history_holder)
    void clickSearchHistoryHolder(View v) {
        setSearchFocus(false);
    }

    void setSearchFocus(boolean focus) {
        searchFocus = focus;
        if (focus) {
            imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
        } else {
            imm.hideSoftInputFromWindow(toolbarInput.getWindowToken(), 0);
        }

        toolbarTitle.setVisibility(focus ? View.GONE : View.VISIBLE);
        toolbarInput.setVisibility(focus ? View.VISIBLE : View.GONE);
        if (focus) {
            toolbarInput.requestFocus();
        }
        historyHolder.setVisibility(focus ? View.VISIBLE : View.GONE);

        searchHistoryAdapter.setItems(presenter.getSearchHistory());
    }

    void searchLocation(String search) {
        isMoveMapSearch = false;
        toolbarTitle.setText(search);
        presenter.addSearchHistory(search);
        presenter.poiSearch(search);
        setSearchFocus(false);
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void mapMoveToLocation(LatLng latLng) {
        map.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 16, 0, 0)));
    }

    @Override
    public void reLocation() {
        MyLocationStyle locationStyle = new MyLocationStyle();
        locationStyle.myLocationType((MyLocationStyle.LOCATION_TYPE_SHOW));
        locationStyle.showMyLocation(true);
        locationStyle.radiusFillColor(UIUtil.getColor(R.color.translate));
        locationStyle.strokeColor(UIUtil.getColor(R.color.translate));
        map.setMyLocationStyle(locationStyle);
        map.setMyLocationEnabled(true);
    }

    @Override
    public void chooseLocation(PoiItem item) {
        if (TYPE_CHOOSE_LOCATION.equals(type)) {
            setResult(RESULT_OK, ActiivtyDataHelper.getPoiItemData(item));
        } else {
            EventUtil.postLocationEvent(new LocationEvent(item.getTitle(),
                    item.getLatLonPoint().getLongitude(),
                    item.getLatLonPoint().getLatitude()));
            AppInfoDBHelper.newInstance().saveLocationInfo(item.getTitle(),
                    item.getLatLonPoint().getLongitude()+"",
                    item.getLatLonPoint().getLatitude()+"",
                    item.getCityCode())
            ;
        }
        finish();
    }

    @Override
    public void setItems(List<PoiItem> items) {
        searchResultAdapter.setItems(items);
    }


}
