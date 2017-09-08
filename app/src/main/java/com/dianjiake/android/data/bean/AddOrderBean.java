package com.dianjiake.android.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lfs on 2017/7/21.
 */

public class AddOrderBean implements Parcelable {
    private OrderBean dingdanxiangqiang;

    public OrderBean getDingdanxiangqiang() {
        return dingdanxiangqiang;
    }

    public void setDingdanxiangqiang(OrderBean dingdanxiangqiang) {
        this.dingdanxiangqiang = dingdanxiangqiang;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.dingdanxiangqiang, flags);
    }

    public AddOrderBean() {
    }

    protected AddOrderBean(Parcel in) {
        this.dingdanxiangqiang = in.readParcelable(OrderBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<AddOrderBean> CREATOR = new Parcelable.Creator<AddOrderBean>() {
        @Override
        public AddOrderBean createFromParcel(Parcel source) {
            return new AddOrderBean(source);
        }

        @Override
        public AddOrderBean[] newArray(int size) {
            return new AddOrderBean[size];
        }
    };
}
