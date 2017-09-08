package com.dianjiake.android.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lfs on 2017/7/25.
 */

public class MyCouponBean implements Parcelable {
    private CouponBean kaquan;
    private HomeShopBean dianpu;

    public CouponBean getKaquan() {
        return kaquan;
    }

    public void setKaquan(CouponBean kaquan) {
        this.kaquan = kaquan;
    }

    public HomeShopBean getDianpu() {
        return dianpu;
    }

    public void setDianpu(HomeShopBean dianpu) {
        this.dianpu = dianpu;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.kaquan, flags);
        dest.writeParcelable(this.dianpu, flags);
    }

    public MyCouponBean() {
    }

    protected MyCouponBean(Parcel in) {
        this.kaquan = in.readParcelable(CouponBean.class.getClassLoader());
        this.dianpu = in.readParcelable(HomeShopBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<MyCouponBean> CREATOR = new Parcelable.Creator<MyCouponBean>() {
        @Override
        public MyCouponBean createFromParcel(Parcel source) {
            return new MyCouponBean(source);
        }

        @Override
        public MyCouponBean[] newArray(int size) {
            return new MyCouponBean[size];
        }
    };
}
