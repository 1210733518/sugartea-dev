package com.dianjiake.android.data.bean;

import java.util.List;

/**
 * 会员卡 bean
 * Created by lfs on 2017/7/25.
 */

public class VipBean {

    private String shanghuid;
    private String huiyuandengji;
    private String money;
    private String jieshushijian;
    private String shanghumingcheng;
    private String shanghujiancheng;
    private String logo;
    private String cover;
    private String dengjimingcheng;
    private String fuwuzhekou;
    private String shangpinzhekou;
    private List<JicikalistBean> jicikalist;

    public String getShanghuid() {
        return shanghuid;
    }

    public void setShanghuid(String shanghuid) {
        this.shanghuid = shanghuid;
    }

    public String getHuiyuandengji() {
        return huiyuandengji;
    }

    public void setHuiyuandengji(String huiyuandengji) {
        this.huiyuandengji = huiyuandengji;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getJieshushijian() {
        return jieshushijian;
    }

    public void setJieshushijian(String jieshushijian) {
        this.jieshushijian = jieshushijian;
    }

    public String getShanghumingcheng() {
        return shanghumingcheng;
    }

    public void setShanghumingcheng(String shanghumingcheng) {
        this.shanghumingcheng = shanghumingcheng;
    }

    public String getShanghujiancheng() {
        return shanghujiancheng;
    }

    public void setShanghujiancheng(String shanghujiancheng) {
        this.shanghujiancheng = shanghujiancheng;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDengjimingcheng() {
        return dengjimingcheng;
    }

    public void setDengjimingcheng(String dengjimingcheng) {
        this.dengjimingcheng = dengjimingcheng;
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

    public List<JicikalistBean> getJicikalist() {
        return jicikalist;
    }

    public void setJicikalist(List<JicikalistBean> jicikalist) {
        this.jicikalist = jicikalist;
    }

    public static class JicikalistBean {

        private String shengyucishu;
        private String jicikamingcheng;

        public String getShengyucishu() {
            return shengyucishu;
        }

        public void setShengyucishu(String shengyucishu) {
            this.shengyucishu = shengyucishu;
        }

        public String getJicikamingcheng() {
            return jicikamingcheng;
        }

        public void setJicikamingcheng(String jicikamingcheng) {
            this.jicikamingcheng = jicikamingcheng;
        }
    }
}
