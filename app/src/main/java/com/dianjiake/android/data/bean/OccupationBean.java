package com.dianjiake.android.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lfs on 17/2/17.
 */

public class OccupationBean implements Parcelable {

    private String id;
    private String sid;
    private String mid;
    private String mname;

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

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.sid);
        dest.writeString(this.mid);
        dest.writeString(this.mname);
    }

    public OccupationBean() {
    }

    protected OccupationBean(Parcel in) {
        this.id = in.readString();
        this.sid = in.readString();
        this.mid = in.readString();
        this.mname = in.readString();
    }

    public static final Creator<OccupationBean> CREATOR = new Creator<OccupationBean>() {
        @Override
        public OccupationBean createFromParcel(Parcel source) {
            return new OccupationBean(source);
        }

        @Override
        public OccupationBean[] newArray(int size) {
            return new OccupationBean[size];
        }
    };
}
