package com.dianjiake.android.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lfs on 2017/7/17.
 */

public class ShopCommentBean implements Parcelable {

    private String id;
    private String ordernum;
    private String openid;
    private String beipinglunopenid;
    private String neirong;
    private String pingfen;
    private String biaoqian;
    private String shijian;
    private String fuwuid;
    private String shanghuid;
    private String xianshi;
    private UserInfoBean user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getBeipinglunopenid() {
        return beipinglunopenid;
    }

    public void setBeipinglunopenid(String beipinglunopenid) {
        this.beipinglunopenid = beipinglunopenid;
    }

    public String getNeirong() {
        return neirong;
    }

    public void setNeirong(String neirong) {
        this.neirong = neirong;
    }

    public String getPingfen() {
        return pingfen;
    }

    public void setPingfen(String pingfen) {
        this.pingfen = pingfen;
    }

    public String getBiaoqian() {
        return biaoqian;
    }

    public void setBiaoqian(String biaoqian) {
        this.biaoqian = biaoqian;
    }

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }

    public String getFuwuid() {
        return fuwuid;
    }

    public void setFuwuid(String fuwuid) {
        this.fuwuid = fuwuid;
    }

    public String getShanghuid() {
        return shanghuid;
    }

    public void setShanghuid(String shanghuid) {
        this.shanghuid = shanghuid;
    }

    public String getXianshi() {
        return xianshi;
    }

    public void setXianshi(String xianshi) {
        this.xianshi = xianshi;
    }

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
        dest.writeString(this.id);
        dest.writeString(this.ordernum);
        dest.writeString(this.openid);
        dest.writeString(this.beipinglunopenid);
        dest.writeString(this.neirong);
        dest.writeString(this.pingfen);
        dest.writeString(this.biaoqian);
        dest.writeString(this.shijian);
        dest.writeString(this.fuwuid);
        dest.writeString(this.shanghuid);
        dest.writeString(this.xianshi);
        dest.writeParcelable(this.user, flags);
    }

    public ShopCommentBean() {
    }

    protected ShopCommentBean(Parcel in) {
        this.id = in.readString();
        this.ordernum = in.readString();
        this.openid = in.readString();
        this.beipinglunopenid = in.readString();
        this.neirong = in.readString();
        this.pingfen = in.readString();
        this.biaoqian = in.readString();
        this.shijian = in.readString();
        this.fuwuid = in.readString();
        this.shanghuid = in.readString();
        this.xianshi = in.readString();
        this.user = in.readParcelable(UserInfoBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ShopCommentBean> CREATOR = new Parcelable.Creator<ShopCommentBean>() {
        @Override
        public ShopCommentBean createFromParcel(Parcel source) {
            return new ShopCommentBean(source);
        }

        @Override
        public ShopCommentBean[] newArray(int size) {
            return new ShopCommentBean[size];
        }
    };
}
