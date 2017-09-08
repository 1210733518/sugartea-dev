package com.dianjiake.android.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lfs on 2017/7/25.
 */

public class ServiceTypeBean implements Parcelable {
    List<ServiceFirstBean> leibie;

    public List<ServiceFirstBean> getLeibie() {
        return leibie;
    }

    public void setLeibie(List<ServiceFirstBean> leibie) {
        this.leibie = leibie;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.leibie);
    }

    public ServiceTypeBean() {
    }

    protected ServiceTypeBean(Parcel in) {
        this.leibie = new ArrayList<ServiceFirstBean>();
        in.readList(this.leibie, ServiceFirstBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ServiceTypeBean> CREATOR = new Parcelable.Creator<ServiceTypeBean>() {
        @Override
        public ServiceTypeBean createFromParcel(Parcel source) {
            return new ServiceTypeBean(source);
        }

        @Override
        public ServiceTypeBean[] newArray(int size) {
            return new ServiceTypeBean[size];
        }
    };
}
