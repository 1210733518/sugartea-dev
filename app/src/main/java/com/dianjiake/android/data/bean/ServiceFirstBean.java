package com.dianjiake.android.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lfs on 2017/7/25.
 */

public class ServiceFirstBean implements Parcelable {

    private int id;
    private String mingcheng;
    private String logo;
    private List<ServiceSecondBean> leibies;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<ServiceSecondBean> getLeibies() {
        return leibies;
    }

    public void setLeibies(List<ServiceSecondBean> leibies) {
        this.leibies = leibies;
    }

    public ServiceFirstBean() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.mingcheng);
        dest.writeString(this.logo);
        dest.writeTypedList(this.leibies);
    }

    protected ServiceFirstBean(Parcel in) {
        this.id = in.readInt();
        this.mingcheng = in.readString();
        this.logo = in.readString();
        this.leibies = in.createTypedArrayList(ServiceSecondBean.CREATOR);
    }

    public static final Creator<ServiceFirstBean> CREATOR = new Creator<ServiceFirstBean>() {
        @Override
        public ServiceFirstBean createFromParcel(Parcel source) {
            return new ServiceFirstBean(source);
        }

        @Override
        public ServiceFirstBean[] newArray(int size) {
            return new ServiceFirstBean[size];
        }
    };
}
