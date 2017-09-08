package com.dianjiake.android.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lfs on 2017/7/12.
 */

public class ServiceBean implements Parcelable {


    private String id;
    private String yijileibie;
    private String erjileibie;
    private String name;
    private String danwei;
    private String jianjie;
    private String xiangqing;
    private String shijian;
    private String photo;
    private String status;
    private String starttime;
    private String openid;
    private String shanghuid;
    private String tupian;
    private String beizhu;
    private String jinyongshijian;
    private String fuwumoshi;
    private String jine;
    private String cuxiao;
    private String cuxiaojia;
    private String kaishishijian;
    private String jieshushijian;
    private String ticheng;
    private String zhiding;
    private String gengxinshijian;
    private String fuwupingfen;
    private String fuwupinglunshu;
    private String yuyueshu;
    private String cuxiaos;
    private HomeShopBean dianpu;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYijileibie() {
        return yijileibie;
    }

    public void setYijileibie(String yijileibie) {
        this.yijileibie = yijileibie;
    }

    public String getErjileibie() {
        return erjileibie;
    }

    public void setErjileibie(String erjileibie) {
        this.erjileibie = erjileibie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDanwei() {
        return danwei;
    }

    public void setDanwei(String danwei) {
        this.danwei = danwei;
    }

    public String getJianjie() {
        return jianjie;
    }

    public void setJianjie(String jianjie) {
        this.jianjie = jianjie;
    }

    public String getXiangqing() {
        return xiangqing;
    }

    public void setXiangqing(String xiangqing) {
        this.xiangqing = xiangqing;
    }

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getShanghuid() {
        return shanghuid;
    }

    public void setShanghuid(String shanghuid) {
        this.shanghuid = shanghuid;
    }

    public String getTupian() {
        return tupian;
    }

    public void setTupian(String tupian) {
        this.tupian = tupian;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getJinyongshijian() {
        return jinyongshijian;
    }

    public void setJinyongshijian(String jinyongshijian) {
        this.jinyongshijian = jinyongshijian;
    }

    public String getFuwumoshi() {
        return fuwumoshi;
    }

    public void setFuwumoshi(String fuwumoshi) {
        this.fuwumoshi = fuwumoshi;
    }

    public String getJine() {
        return jine;
    }

    public void setJine(String jine) {
        this.jine = jine;
    }

    public String getCuxiao() {
        return cuxiao;
    }

    public void setCuxiao(String cuxiao) {
        this.cuxiao = cuxiao;
    }

    public String getCuxiaojia() {
        return cuxiaojia;
    }

    public void setCuxiaojia(String cuxiaojia) {
        this.cuxiaojia = cuxiaojia;
    }

    public String getKaishishijian() {
        return kaishishijian;
    }

    public void setKaishishijian(String kaishishijian) {
        this.kaishishijian = kaishishijian;
    }

    public String getJieshushijian() {
        return jieshushijian;
    }

    public void setJieshushijian(String jieshushijian) {
        this.jieshushijian = jieshushijian;
    }

    public String getTicheng() {
        return ticheng;
    }

    public void setTicheng(String ticheng) {
        this.ticheng = ticheng;
    }

    public String getZhiding() {
        return zhiding;
    }

    public void setZhiding(String zhiding) {
        this.zhiding = zhiding;
    }

    public String getGengxinshijian() {
        return gengxinshijian;
    }

    public void setGengxinshijian(String gengxinshijian) {
        this.gengxinshijian = gengxinshijian;
    }

    public String getFuwupingfen() {
        return fuwupingfen;
    }

    public void setFuwupingfen(String fuwupingfen) {
        this.fuwupingfen = fuwupingfen;
    }

    public String getFuwupinglunshu() {
        return fuwupinglunshu;
    }

    public void setFuwupinglunshu(String fuwupinglunshu) {
        this.fuwupinglunshu = fuwupinglunshu;
    }

    public String getYuyueshu() {
        return yuyueshu;
    }

    public void setYuyueshu(String yuyueshu) {
        this.yuyueshu = yuyueshu;
    }

    public String getCuxiaos() {
        return cuxiaos;
    }

    public void setCuxiaos(String cuxiaos) {
        this.cuxiaos = cuxiaos;
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
        dest.writeString(this.id);
        dest.writeString(this.yijileibie);
        dest.writeString(this.erjileibie);
        dest.writeString(this.name);
        dest.writeString(this.danwei);
        dest.writeString(this.jianjie);
        dest.writeString(this.xiangqing);
        dest.writeString(this.shijian);
        dest.writeString(this.photo);
        dest.writeString(this.status);
        dest.writeString(this.starttime);
        dest.writeString(this.openid);
        dest.writeString(this.shanghuid);
        dest.writeString(this.tupian);
        dest.writeString(this.beizhu);
        dest.writeString(this.jinyongshijian);
        dest.writeString(this.fuwumoshi);
        dest.writeString(this.jine);
        dest.writeString(this.cuxiao);
        dest.writeString(this.cuxiaojia);
        dest.writeString(this.kaishishijian);
        dest.writeString(this.jieshushijian);
        dest.writeString(this.ticheng);
        dest.writeString(this.zhiding);
        dest.writeString(this.gengxinshijian);
        dest.writeString(this.fuwupingfen);
        dest.writeString(this.fuwupinglunshu);
        dest.writeString(this.yuyueshu);
        dest.writeString(this.cuxiaos);
        dest.writeParcelable(this.dianpu, flags);
    }

    public ServiceBean() {
    }

    protected ServiceBean(Parcel in) {
        this.id = in.readString();
        this.yijileibie = in.readString();
        this.erjileibie = in.readString();
        this.name = in.readString();
        this.danwei = in.readString();
        this.jianjie = in.readString();
        this.xiangqing = in.readString();
        this.shijian = in.readString();
        this.photo = in.readString();
        this.status = in.readString();
        this.starttime = in.readString();
        this.openid = in.readString();
        this.shanghuid = in.readString();
        this.tupian = in.readString();
        this.beizhu = in.readString();
        this.jinyongshijian = in.readString();
        this.fuwumoshi = in.readString();
        this.jine = in.readString();
        this.cuxiao = in.readString();
        this.cuxiaojia = in.readString();
        this.kaishishijian = in.readString();
        this.jieshushijian = in.readString();
        this.ticheng = in.readString();
        this.zhiding = in.readString();
        this.gengxinshijian = in.readString();
        this.fuwupingfen = in.readString();
        this.fuwupinglunshu = in.readString();
        this.yuyueshu = in.readString();
        this.cuxiaos = in.readString();
        this.dianpu = in.readParcelable(HomeShopBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ServiceBean> CREATOR = new Parcelable.Creator<ServiceBean>() {
        @Override
        public ServiceBean createFromParcel(Parcel source) {
            return new ServiceBean(source);
        }

        @Override
        public ServiceBean[] newArray(int size) {
            return new ServiceBean[size];
        }
    };
}
