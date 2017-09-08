package com.dianjiake.android.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lfs on 2017/7/20.
 */

public class OrderGoodBean implements Parcelable {
    private String goodsid;
    private String mingcheng;
    private String danjia;
    private String shuliang;

    public String getStuff_name() {
        return stuff_name;
    }

    public void setStuff_name(String stuff_name) {
        this.stuff_name = stuff_name;
    }

    private String shifujine;
    private String shangpinjine;
    private String stuff_name;

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getMingcheng() {
        return mingcheng;
    }

    public void setMingcheng(String mingcheng) {
        this.mingcheng = mingcheng;
    }

    public String getDanjia() {
        return danjia;
    }

    public void setDanjia(String danjia) {
        this.danjia = danjia;
    }

    public String getShuliang() {
        return shuliang;
    }

    public void setShuliang(String shuliang) {
        this.shuliang = shuliang;
    }

    public String getShifujine() {
        return shifujine;
    }

    public void setShifujine(String shifujine) {
        this.shifujine = shifujine;
    }

    public String getShangpinjine() {
        return shangpinjine;
    }

    public void setShangpinjine(String shangpinjine) {
        this.shangpinjine = shangpinjine;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.goodsid);
        dest.writeString(this.mingcheng);
        dest.writeString(this.danjia);
        dest.writeString(this.shuliang);
        dest.writeString(this.shifujine);
        dest.writeString(this.shangpinjine);
    }

    public OrderGoodBean() {
    }

    protected OrderGoodBean(Parcel in) {
        this.goodsid = in.readString();
        this.mingcheng = in.readString();
        this.danjia = in.readString();
        this.shuliang = in.readString();
        this.shifujine = in.readString();
        this.shangpinjine = in.readString();
    }

    public static final Parcelable.Creator<OrderGoodBean> CREATOR = new Parcelable.Creator<OrderGoodBean>() {
        @Override
        public OrderGoodBean createFromParcel(Parcel source) {
            return new OrderGoodBean(source);
        }

        @Override
        public OrderGoodBean[] newArray(int size) {
            return new OrderGoodBean[size];
        }
    };
}
