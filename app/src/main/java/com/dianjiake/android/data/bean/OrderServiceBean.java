package com.dianjiake.android.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lfs on 2017/7/20.
 */

public class OrderServiceBean implements Parcelable {
    private String id;
    private String dingdanid;
    private String fuwuid;
    private String fuwumingcheng;
    private String fuwuopenid;
    private String shanghuid;
    private String danjia;
    private String shuliang;
    private String ticheng;
    private String status;
    private String kaishishijian;
    //姓名
    private String name;
    private String wanchengshijian;
    private String photo;
    private int viewType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int tempStar;

    public int getTempStar() {
        return tempStar;
    }

    public void setTempStar(int tempStar) {
        this.tempStar = tempStar;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDingdanid() {
        return dingdanid;
    }

    public void setDingdanid(String dingdanid) {
        this.dingdanid = dingdanid;
    }

    public String getFuwuid() {
        return fuwuid;
    }

    public void setFuwuid(String fuwuid) {
        this.fuwuid = fuwuid;
    }

    public String getFuwumingcheng() {
        return fuwumingcheng;
    }

    public void setFuwumingcheng(String fuwumingcheng) {
        this.fuwumingcheng = fuwumingcheng;
    }

    public String getFuwuopenid() {
        return fuwuopenid;
    }

    public void setFuwuopenid(String fuwuopenid) {
        this.fuwuopenid = fuwuopenid;
    }

    public String getShanghuid() {
        return shanghuid;
    }

    public void setShanghuid(String shanghuid) {
        this.shanghuid = shanghuid;
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

    public String getTicheng() {
        return ticheng;
    }

    public void setTicheng(String ticheng) {
        this.ticheng = ticheng;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKaishishijian() {
        return kaishishijian;
    }

    public void setKaishishijian(String kaishishijian) {
        this.kaishishijian = kaishishijian;
    }

    public String getWanchengshijian() {
        return wanchengshijian;
    }

    public void setWanchengshijian(String wanchengshijian) {
        this.wanchengshijian = wanchengshijian;
    }

    @Override
    public String toString() {
        return "OrderServiceBean{" +
                "id='" + id + '\'' +
                ", dingdanid='" + dingdanid + '\'' +
                ", fuwuid='" + fuwuid + '\'' +
                ", fuwumingcheng='" + fuwumingcheng + '\'' +
                ", fuwuopenid='" + fuwuopenid + '\'' +
                ", shanghuid='" + shanghuid + '\'' +
                ", danjia='" + danjia + '\'' +
                ", shuliang='" + shuliang + '\'' +
                ", ticheng='" + ticheng + '\'' +
                ", status='" + status + '\'' +
                ", kaishishijian='" + kaishishijian + '\'' +
                ", name='" + name + '\'' +
                ", wanchengshijian='" + wanchengshijian + '\'' +
                ", photo='" + photo + '\'' +
                ", viewType=" + viewType +
                ", tempStar=" + tempStar +
                '}';
    }

    public OrderServiceBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.dingdanid);
        dest.writeString(this.fuwuid);
        dest.writeString(this.fuwumingcheng);
        dest.writeString(this.fuwuopenid);
        dest.writeString(this.shanghuid);
        dest.writeString(this.danjia);
        dest.writeString(this.shuliang);
        dest.writeString(this.ticheng);
        dest.writeString(this.status);
        dest.writeString(this.kaishishijian);
        dest.writeString(this.wanchengshijian);
        dest.writeString(this.photo);
        dest.writeInt(this.viewType);
        dest.writeInt(this.tempStar);
    }

    protected OrderServiceBean(Parcel in) {
        this.id = in.readString();
        this.dingdanid = in.readString();
        this.fuwuid = in.readString();
        this.fuwumingcheng = in.readString();
        this.fuwuopenid = in.readString();
        this.shanghuid = in.readString();
        this.danjia = in.readString();
        this.shuliang = in.readString();
        this.ticheng = in.readString();
        this.status = in.readString();
        this.kaishishijian = in.readString();
        this.wanchengshijian = in.readString();
        this.photo = in.readString();
        this.viewType = in.readInt();
        this.tempStar = in.readInt();
    }

    public static final Creator<OrderServiceBean> CREATOR = new Creator<OrderServiceBean>() {
        @Override
        public OrderServiceBean createFromParcel(Parcel source) {
            return new OrderServiceBean(source);
        }

        @Override
        public OrderServiceBean[] newArray(int size) {
            return new OrderServiceBean[size];
        }
    };
}
