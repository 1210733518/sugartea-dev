package com.dianjiake.android.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by lfs on 2017/7/19.
 */

public class ADBean implements Parcelable {
    private int num;
    private List<ADItemBean> content;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<ADItemBean> getContent() {
        return content;
    }

    public void setContent(List<ADItemBean> content) {
        this.content = content;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.num);
        dest.writeTypedList(this.content);
    }

    public ADBean() {
    }

    protected ADBean(Parcel in) {
        this.num = in.readInt();
        this.content = in.createTypedArrayList(ADItemBean.CREATOR);
    }

    public static final Parcelable.Creator<ADBean> CREATOR = new Parcelable.Creator<ADBean>() {
        @Override
        public ADBean createFromParcel(Parcel source) {
            return new ADBean(source);
        }

        @Override
        public ADBean[] newArray(int size) {
            return new ADBean[size];
        }
    };
}
