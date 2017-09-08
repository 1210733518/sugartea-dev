package com.dianjiake.android.data.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by Sacowiw on 2017/5/17.
 */
@Entity
public class AppInfoModel {
    @Id
    @Unique
    private Long id;
    private String cid;//个推cid
    private String longitude;//经度
    private String latitude;//纬度
    private String locationName;//定位地点名称
    private String cityCode;//定位到的 city code

    @Generated(hash = 996025001)
    public AppInfoModel(Long id, String cid, String longitude, String latitude,
            String locationName, String cityCode) {
        this.id = id;
        this.cid = cid;
        this.longitude = longitude;
        this.latitude = latitude;
        this.locationName = locationName;
        this.cityCode = cityCode;
    }

    @Generated(hash = 1825615946)
    public AppInfoModel() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCityCode() {
        return this.cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

}
