package com.dianjiake.android.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lfs on 2017/7/20.
 */

public class OrderBean implements Parcelable {

    private String id;
    private String ordernum;
    private String openid;
    private String name;
    private String tel;
    private String sex;
    private String dizhi;
    private String fuwuid;
    private String fuwumingcheng;
    private String fuwuopenid;
    private String shanghuid;
    private String danjia;
    private String shuliang;
    private String yingfujine;
    private String shifujine;
    private String pays;
    private String addtime;
    private String paytime;
    private String jiaoyihao;
    private String fuwushijian;
    private String status;
    private String wanchengshijian;
    private String beizhu;
    private String fuwumoshi;
    private String laiyuan;
    private String xianshi;
    private String fuwuzhekou;
    private String shangpinzhekou;
    private String fangjianid;
    private String caozuoopenid;
    private String taimianshu;
    private String daodianshijian;
    private String dengjimingcheng;
    private List<OrderGoodBean> dingdanshangpin;
    private String hejijine;
    private String huiyuanjia;
    private String shifoupinglun;

    public String getService_stuff_name() {
        return service_stuff_name;
    }

    public void setService_stuff_name(String service_stuff_name) {
        this.service_stuff_name = service_stuff_name;
    }

    private HomeShopBean dianpu;
    private String chuzhi;
    private ArrayList<OrderServiceBean> dingdanfuwu;
    private List<DiscountBean> youhui;
    private String service_stuff_name;
    int viewType;

    public List<DiscountBean> getYouhui() {
        return youhui;
    }

    public void setYouhui(List<DiscountBean> youhui) {
        this.youhui = youhui;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDizhi() {
        return dizhi;
    }

    public void setDizhi(String dizhi) {
        this.dizhi = dizhi;
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

    public String getYingfujine() {
        return yingfujine;
    }

    public void setYingfujine(String yingfujine) {
        this.yingfujine = yingfujine;
    }

    public String getShifujine() {
        return shifujine;
    }

    public void setShifujine(String shifujine) {
        this.shifujine = shifujine;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

    public String getJiaoyihao() {
        return jiaoyihao;
    }

    public void setJiaoyihao(String jiaoyihao) {
        this.jiaoyihao = jiaoyihao;
    }

    public String getFuwushijian() {
        return fuwushijian;
    }

    public void setFuwushijian(String fuwushijian) {
        this.fuwushijian = fuwushijian;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWanchengshijian() {
        return wanchengshijian;
    }

    public void setWanchengshijian(String wanchengshijian) {
        this.wanchengshijian = wanchengshijian;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getFuwumoshi() {
        return fuwumoshi;
    }

    public void setFuwumoshi(String fuwumoshi) {
        this.fuwumoshi = fuwumoshi;
    }

    public String getLaiyuan() {
        return laiyuan;
    }

    public void setLaiyuan(String laiyuan) {
        this.laiyuan = laiyuan;
    }

    public String getXianshi() {
        return xianshi;
    }

    public void setXianshi(String xianshi) {
        this.xianshi = xianshi;
    }

    public String getFuwuzhekou() {
        return fuwuzhekou;
    }

    public void setFuwuzhekou(String fuwuzhekou) {
        this.fuwuzhekou = fuwuzhekou;
    }

    public String getShangpinzhekou() {
        return shangpinzhekou;
    }

    public void setShangpinzhekou(String shangpinzhekou) {
        this.shangpinzhekou = shangpinzhekou;
    }

    public String getFangjianid() {
        return fangjianid;
    }

    public void setFangjianid(String fangjianid) {
        this.fangjianid = fangjianid;
    }

    public String getCaozuoopenid() {
        return caozuoopenid;
    }

    public void setCaozuoopenid(String caozuoopenid) {
        this.caozuoopenid = caozuoopenid;
    }

    public String getTaimianshu() {
        return taimianshu;
    }

    public void setTaimianshu(String taimianshu) {
        this.taimianshu = taimianshu;
    }

    public String getDaodianshijian() {
        return daodianshijian;
    }

    public void setDaodianshijian(String daodianshijian) {
        this.daodianshijian = daodianshijian;
    }

    public String getDengjimingcheng() {
        return dengjimingcheng;
    }

    public void setDengjimingcheng(String dengjimingcheng) {
        this.dengjimingcheng = dengjimingcheng;
    }

    public List<OrderGoodBean> getDingdanshangpin() {
        return dingdanshangpin;
    }

    public void setDingdanshangpin(List<OrderGoodBean> dingdanshangpin) {
        this.dingdanshangpin = dingdanshangpin;
    }

    public String getHejijine() {
        return hejijine;
    }

    public void setHejijine(String hejijine) {
        this.hejijine = hejijine;
    }

    public String getHuiyuanjia() {
        return huiyuanjia;
    }

    public void setHuiyuanjia(String huiyuanjia) {
        this.huiyuanjia = huiyuanjia;
    }

    public String getShifoupinglun() {
        return shifoupinglun;
    }

    public void setShifoupinglun(String shifoupinglun) {
        this.shifoupinglun = shifoupinglun;
    }

    public HomeShopBean getDianpu() {
        return dianpu;
    }

    public void setDianpu(HomeShopBean dianpu) {
        this.dianpu = dianpu;
    }

    public String getChuzhi() {
        return chuzhi;
    }

    public void setChuzhi(String chuzhi) {
        this.chuzhi = chuzhi;
    }

    public ArrayList<OrderServiceBean> getDingdanfuwu() {
        return dingdanfuwu;
    }

    public void setDingdanfuwu(ArrayList<OrderServiceBean> dingdanfuwu) {
        this.dingdanfuwu = dingdanfuwu;
    }

    public static Creator<OrderBean> getCREATOR() {
        return CREATOR;
    }

    public OrderBean() {
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
        dest.writeString(this.name);
        dest.writeString(this.tel);
        dest.writeString(this.sex);
        dest.writeString(this.dizhi);
        dest.writeString(this.fuwuid);
        dest.writeString(this.fuwumingcheng);
        dest.writeString(this.fuwuopenid);
        dest.writeString(this.shanghuid);
        dest.writeString(this.danjia);
        dest.writeString(this.shuliang);
        dest.writeString(this.yingfujine);
        dest.writeString(this.shifujine);
        dest.writeString(this.pays);
        dest.writeString(this.addtime);
        dest.writeString(this.paytime);
        dest.writeString(this.jiaoyihao);
        dest.writeString(this.fuwushijian);
        dest.writeString(this.status);
        dest.writeString(this.wanchengshijian);
        dest.writeString(this.beizhu);
        dest.writeString(this.fuwumoshi);
        dest.writeString(this.laiyuan);
        dest.writeString(this.xianshi);
        dest.writeString(this.fuwuzhekou);
        dest.writeString(this.shangpinzhekou);
        dest.writeString(this.fangjianid);
        dest.writeString(this.caozuoopenid);
        dest.writeString(this.taimianshu);
        dest.writeString(this.daodianshijian);
        dest.writeString(this.dengjimingcheng);
        dest.writeTypedList(this.dingdanshangpin);
        dest.writeString(this.hejijine);
        dest.writeString(this.huiyuanjia);
        dest.writeString(this.shifoupinglun);
        dest.writeParcelable(this.dianpu, flags);
        dest.writeString(this.chuzhi);
        dest.writeTypedList(this.dingdanfuwu);
        dest.writeTypedList(this.youhui);
        dest.writeInt(this.viewType);
    }

    protected OrderBean(Parcel in) {
        this.id = in.readString();
        this.ordernum = in.readString();
        this.openid = in.readString();
        this.name = in.readString();
        this.tel = in.readString();
        this.sex = in.readString();
        this.dizhi = in.readString();
        this.fuwuid = in.readString();
        this.fuwumingcheng = in.readString();
        this.fuwuopenid = in.readString();
        this.shanghuid = in.readString();
        this.danjia = in.readString();
        this.shuliang = in.readString();
        this.yingfujine = in.readString();
        this.shifujine = in.readString();
        this.pays = in.readString();
        this.addtime = in.readString();
        this.paytime = in.readString();
        this.jiaoyihao = in.readString();
        this.fuwushijian = in.readString();
        this.status = in.readString();
        this.wanchengshijian = in.readString();
        this.beizhu = in.readString();
        this.fuwumoshi = in.readString();
        this.laiyuan = in.readString();
        this.xianshi = in.readString();
        this.fuwuzhekou = in.readString();
        this.shangpinzhekou = in.readString();
        this.fangjianid = in.readString();
        this.caozuoopenid = in.readString();
        this.taimianshu = in.readString();
        this.daodianshijian = in.readString();
        this.dengjimingcheng = in.readString();
        this.dingdanshangpin = in.createTypedArrayList(OrderGoodBean.CREATOR);
        this.hejijine = in.readString();
        this.huiyuanjia = in.readString();
        this.shifoupinglun = in.readString();
        this.dianpu = in.readParcelable(HomeShopBean.class.getClassLoader());
        this.chuzhi = in.readString();
        this.dingdanfuwu = in.createTypedArrayList(OrderServiceBean.CREATOR);
        this.youhui = in.createTypedArrayList(DiscountBean.CREATOR);
        this.viewType = in.readInt();
    }

    public static final Creator<OrderBean> CREATOR = new Creator<OrderBean>() {
        @Override
        public OrderBean createFromParcel(Parcel source) {
            return new OrderBean(source);
        }

        @Override
        public OrderBean[] newArray(int size) {
            return new OrderBean[size];
        }
    };
}
