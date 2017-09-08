package com.dianjiake.android.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lfs on 2017/4/11.
 */

public class ChooseTimeBean implements Parcelable {
    private long mTimestamp;
    private boolean mIsChecked;

    public boolean isChecked() {
        return mIsChecked;
    }

    public void setChecked(boolean checked) {
        mIsChecked = checked;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(long timestamp) {
        mTimestamp = timestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mTimestamp);
        dest.writeByte(this.mIsChecked ? (byte) 1 : (byte) 0);
    }

    public ChooseTimeBean() {
    }

    protected ChooseTimeBean(Parcel in) {
        this.mTimestamp = in.readLong();
        this.mIsChecked = in.readByte() != 0;
    }

    public static final Creator<ChooseTimeBean> CREATOR = new Creator<ChooseTimeBean>() {
        @Override
        public ChooseTimeBean createFromParcel(Parcel source) {
            return new ChooseTimeBean(source);
        }

        @Override
        public ChooseTimeBean[] newArray(int size) {
            return new ChooseTimeBean[size];
        }
    };
}
