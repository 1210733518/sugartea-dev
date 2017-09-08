package com.dianjiake.android.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lfs on 2017/7/20.
 */

public class EvaluateBean implements Parcelable {
    private int pingfen;
    private String neirong;
    private String fuwuid;
    private String beipinglunopenid;

    public int getPingfen() {
        return pingfen;
    }

    public void setPingfen(int pingfen) {
        this.pingfen = pingfen;
    }

    public String getNeirong() {
        return neirong;
    }

    public void setNeirong(String neirong) {
        this.neirong = neirong;
    }

    public String getFuwuid() {
        return fuwuid;
    }

    public void setFuwuid(String fuwuid) {
        this.fuwuid = fuwuid;
    }

    public String getBeipinglunopenid() {
        return beipinglunopenid;
    }

    public void setBeipinglunopenid(String beipinglunopenid) {
        this.beipinglunopenid = beipinglunopenid;
    }

    @Override
    public String toString() {
        return "EvaluateBean{" +
                "pingfen=" + pingfen +
                ", neirong='" + neirong + '\'' +
                ", fuwuid='" + fuwuid + '\'' +
                ", beipinglunopenid='" + beipinglunopenid + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.pingfen);
        dest.writeString(this.neirong);
        dest.writeString(this.fuwuid);
        dest.writeString(this.beipinglunopenid);
    }

    public EvaluateBean() {
    }

    protected EvaluateBean(Parcel in) {
        this.pingfen = in.readInt();
        this.neirong = in.readString();
        this.fuwuid = in.readString();
        this.beipinglunopenid = in.readString();
    }

    public static final Creator<EvaluateBean> CREATOR = new Creator<EvaluateBean>() {
        @Override
        public EvaluateBean createFromParcel(Parcel source) {
            return new EvaluateBean(source);
        }

        @Override
        public EvaluateBean[] newArray(int size) {
            return new EvaluateBean[size];
        }
    };
}
