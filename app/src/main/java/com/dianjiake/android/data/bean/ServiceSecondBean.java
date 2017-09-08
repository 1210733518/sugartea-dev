package com.dianjiake.android.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lfs on 2017/7/25.
 */

public class ServiceSecondBean implements Parcelable {
    private String id;
    private String sid;
    private String mingcheng;
    private String logo;
    private String shanghushu;
    private int viewType;
    private String viewTitle;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getViewTitle() {
        return viewTitle;
    }

    public void setViewTitle(String viewTitle) {
        this.viewTitle = viewTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMingcheng() {
        return mingcheng;
    }

    public void setMingcheng(String mingcheng) {
        this.mingcheng = mingcheng;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getShanghushu() {
        return shanghushu;
    }

    public void setShanghushu(String shanghushu) {
        this.shanghushu = shanghushu;
    }

    public ServiceSecondBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.sid);
        dest.writeString(this.mingcheng);
        dest.writeString(this.logo);
        dest.writeString(this.shanghushu);
        dest.writeInt(this.viewType);
        dest.writeString(this.viewTitle);
    }

    protected ServiceSecondBean(Parcel in) {
        this.id = in.readString();
        this.sid = in.readString();
        this.mingcheng = in.readString();
        this.logo = in.readString();
        this.shanghushu = in.readString();
        this.viewType = in.readInt();
        this.viewTitle = in.readString();
    }

    public static final Creator<ServiceSecondBean> CREATOR = new Creator<ServiceSecondBean>() {
        @Override
        public ServiceSecondBean createFromParcel(Parcel source) {
            return new ServiceSecondBean(source);
        }

        @Override
        public ServiceSecondBean[] newArray(int size) {
            return new ServiceSecondBean[size];
        }
    };
}
