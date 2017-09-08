package com.dianjiake.android.data.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by lfs on 2017/5/15.
 */

@Entity
public class PhoneInfoModel {
    @Id(autoincrement = true)
    private Long id;
    private String ip;
    @Unique
    private String mac;
    private Integer screenWidth;
    private Integer screenHeight;

    @Generated(hash = 1254125865)
    public PhoneInfoModel(Long id, String ip, String mac, Integer screenWidth,
                          Integer screenHeight) {
        this.id = id;
        this.ip = ip;
        this.mac = mac;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    @Generated(hash = 67064571)
    public PhoneInfoModel() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Integer getScreenWidth() {
        return this.screenWidth;
    }

    public void setScreenWidth(Integer screenWidth) {
        this.screenWidth = screenWidth;
    }

    public Integer getScreenHeight() {
        return this.screenHeight;
    }

    public void setScreenHeight(Integer screenHeight) {
        this.screenHeight = screenHeight;
    }

    @Override
    public String toString() {
        return "PhoneInfoModel{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", mac='" + mac + '\'' +
                ", screenWidth=" + screenWidth +
                ", screenHeight=" + screenHeight +
                '}';
    }
}
