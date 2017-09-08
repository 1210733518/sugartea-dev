package com.dianjiake.android.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by lfs on 2017/7/14.
 */

public class ShopDetailBean implements Parcelable {
    private HomeShopBean dianpu;
    private List<CardBean> kaquanlist;

    public HomeShopBean getDianpu() {
        return dianpu;
    }

    public void setDianpu(HomeShopBean dianpu) {
        this.dianpu = dianpu;
    }

    public List<CardBean> getKaquanlist() {
        return kaquanlist;
    }

    public void setKaquanlist(List<CardBean> kaquanlist) {
        this.kaquanlist = kaquanlist;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.dianpu, flags);
        dest.writeTypedList(this.kaquanlist);
    }

    public ShopDetailBean() {
    }

    protected ShopDetailBean(Parcel in) {
        this.dianpu = in.readParcelable(HomeShopBean.class.getClassLoader());
        this.kaquanlist = in.createTypedArrayList(CardBean.CREATOR);
    }

    public static final Parcelable.Creator<ShopDetailBean> CREATOR = new Parcelable.Creator<ShopDetailBean>() {
        @Override
        public ShopDetailBean createFromParcel(Parcel source) {
            return new ShopDetailBean(source);
        }

        @Override
        public ShopDetailBean[] newArray(int size) {
            return new ShopDetailBean[size];
        }
    };
}
