package com.dianjiake.android.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lfs on 2017/7/19.
 */

public class LoginBean implements Parcelable {
  private UserInfoBean user;

    public UserInfoBean getUser() {
        return user;
    }

    public void setUser(UserInfoBean user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.user, flags);
    }

    public LoginBean() {
    }

    protected LoginBean(Parcel in) {
        this.user = in.readParcelable(UserInfoBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<LoginBean> CREATOR = new Parcelable.Creator<LoginBean>() {
        @Override
        public LoginBean createFromParcel(Parcel source) {
            return new LoginBean(source);
        }

        @Override
        public LoginBean[] newArray(int size) {
            return new LoginBean[size];
        }
    };
}
