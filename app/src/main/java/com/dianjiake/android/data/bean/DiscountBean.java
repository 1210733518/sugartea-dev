package com.dianjiake.android.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lfs on 2017/7/28.
 */

public class DiscountBean implements Parcelable {

    private String leixing;
    private String kaquanmingcheng;
    private String fuwumingcheng;
    private String kaquanleixing;
    private String youhuijine;
    private String lipinmingcheng;

    public String getLeixing() {
        return leixing;
    }

    public void setLeixing(String leixing) {
        this.leixing = leixing;
    }

    public String getKaquanmingcheng() {
        return kaquanmingcheng;
    }

    public void setKaquanmingcheng(String kaquanmingcheng) {
        this.kaquanmingcheng = kaquanmingcheng;
    }

    public String getFuwumingcheng() {
        return fuwumingcheng;
    }

    public void setFuwumingcheng(String fuwumingcheng) {
        this.fuwumingcheng = fuwumingcheng;
    }

    public String getKaquanleixing() {
        return kaquanleixing;
    }

    public void setKaquanleixing(String kaquanleixing) {
        this.kaquanleixing = kaquanleixing;
    }

    public String getYouhuijine() {
        return youhuijine;
    }

    public void setYouhuijine(String youhuijine) {
        this.youhuijine = youhuijine;
    }

    public String getLipinmingcheng() {
        return lipinmingcheng;
    }

    public void setLipinmingcheng(String lipinmingcheng) {
        this.lipinmingcheng = lipinmingcheng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.leixing);
        dest.writeString(this.kaquanmingcheng);
        dest.writeString(this.fuwumingcheng);
        dest.writeString(this.kaquanleixing);
        dest.writeString(this.youhuijine);
        dest.writeString(this.lipinmingcheng);
    }

    public DiscountBean() {
    }

    protected DiscountBean(Parcel in) {
        this.leixing = in.readString();
        this.kaquanmingcheng = in.readString();
        this.fuwumingcheng = in.readString();
        this.kaquanleixing = in.readString();
        this.youhuijine = in.readString();
        this.lipinmingcheng = in.readString();
    }

    public static final Parcelable.Creator<DiscountBean> CREATOR = new Parcelable.Creator<DiscountBean>() {
        @Override
        public DiscountBean createFromParcel(Parcel source) {
            return new DiscountBean(source);
        }

        @Override
        public DiscountBean[] newArray(int size) {
            return new DiscountBean[size];
        }
    };
}
