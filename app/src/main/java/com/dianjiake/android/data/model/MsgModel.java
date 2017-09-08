package com.dianjiake.android.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lfs on 2017/7/26.
 */
@Entity
public class MsgModel implements Parcelable {
    @Id(autoincrement = true)
    private Long _id;
    @Index
    private String id;
    private String openid;
    private String biaoti;
    private String miaoshu;
    private String neirong;
    private String addtime;
    private String xianshi;
    private String chakan;
    private String leixing;
    private String shanghuid;
    private String xinxiid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getBiaoti() {
        return biaoti;
    }

    public void setBiaoti(String biaoti) {
        this.biaoti = biaoti;
    }

    public String getMiaoshu() {
        return miaoshu;
    }

    public void setMiaoshu(String miaoshu) {
        this.miaoshu = miaoshu;
    }

    public String getNeirong() {
        return neirong;
    }

    public void setNeirong(String neirong) {
        this.neirong = neirong;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getXianshi() {
        return xianshi;
    }

    public void setXianshi(String xianshi) {
        this.xianshi = xianshi;
    }

    public String getChakan() {
        return chakan;
    }

    public void setChakan(String chakan) {
        this.chakan = chakan;
    }

    public String getLeixing() {
        return leixing;
    }

    public void setLeixing(String leixing) {
        this.leixing = leixing;
    }

    public String getShanghuid() {
        return shanghuid;
    }

    public void setShanghuid(String shanghuid) {
        this.shanghuid = shanghuid;
    }

    public String getXinxiid() {
        return xinxiid;
    }

    public void setXinxiid(String xinxiid) {
        this.xinxiid = xinxiid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.openid);
        dest.writeString(this.biaoti);
        dest.writeString(this.miaoshu);
        dest.writeString(this.neirong);
        dest.writeString(this.addtime);
        dest.writeString(this.xianshi);
        dest.writeString(this.chakan);
        dest.writeString(this.leixing);
        dest.writeString(this.shanghuid);
        dest.writeString(this.xinxiid);
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public MsgModel() {
    }

    protected MsgModel(Parcel in) {
        this.id = in.readString();
        this.openid = in.readString();
        this.biaoti = in.readString();
        this.miaoshu = in.readString();
        this.neirong = in.readString();
        this.addtime = in.readString();
        this.xianshi = in.readString();
        this.chakan = in.readString();
        this.leixing = in.readString();
        this.shanghuid = in.readString();
        this.xinxiid = in.readString();
    }

    @Generated(hash = 702483170)
    public MsgModel(Long _id, String id, String openid, String biaoti, String miaoshu, String neirong,
            String addtime, String xianshi, String chakan, String leixing, String shanghuid,
            String xinxiid) {
        this._id = _id;
        this.id = id;
        this.openid = openid;
        this.biaoti = biaoti;
        this.miaoshu = miaoshu;
        this.neirong = neirong;
        this.addtime = addtime;
        this.xianshi = xianshi;
        this.chakan = chakan;
        this.leixing = leixing;
        this.shanghuid = shanghuid;
        this.xinxiid = xinxiid;
    }

    
    public static final Parcelable.Creator<MsgModel> CREATOR = new Parcelable.Creator<MsgModel>() {
        @Override
        public MsgModel createFromParcel(Parcel source) {
            return new MsgModel(source);
        }

        @Override
        public MsgModel[] newArray(int size) {
            return new MsgModel[size];
        }
    };
}
