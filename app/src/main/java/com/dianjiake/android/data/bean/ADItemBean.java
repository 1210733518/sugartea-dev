package com.dianjiake.android.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lfs on 2017/7/19.
 */

public class ADItemBean implements Parcelable {


    private String id;
    private String title;
    private String brief;
    private String url;
    private String content;
    private String pic;
    private String addtime;
    private String leixing;
    private String shanghuid;
    private String juli;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
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

    public String getJuli() {
        return juli;
    }

    public void setJuli(String juli) {
        this.juli = juli;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.brief);
        dest.writeString(this.url);
        dest.writeString(this.content);
        dest.writeString(this.pic);
        dest.writeString(this.addtime);
        dest.writeString(this.leixing);
        dest.writeString(this.shanghuid);
        dest.writeString(this.juli);
    }

    public ADItemBean() {
    }

    protected ADItemBean(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.brief = in.readString();
        this.url = in.readString();
        this.content = in.readString();
        this.pic = in.readString();
        this.addtime = in.readString();
        this.leixing = in.readString();
        this.shanghuid = in.readString();
        this.juli = in.readString();
    }

    public static final Parcelable.Creator<ADItemBean> CREATOR = new Parcelable.Creator<ADItemBean>() {
        @Override
        public ADItemBean createFromParcel(Parcel source) {
            return new ADItemBean(source);
        }

        @Override
        public ADItemBean[] newArray(int size) {
            return new ADItemBean[size];
        }
    };
}
