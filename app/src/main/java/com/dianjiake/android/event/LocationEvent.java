package com.dianjiake.android.event;

/**
 * Created by lfs on 2017/7/11.
 */

public class LocationEvent {
    public String longitude;//经度
    public String latitude;//纬度
    public String locationName;//定位地点名称

    public LocationEvent(String locationName, String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.locationName = locationName;
    }

    public LocationEvent(String locationName, double longitude, double latitude) {
        this.longitude = longitude + "";
        this.latitude = latitude + "";
        this.locationName = locationName;
    }
}
