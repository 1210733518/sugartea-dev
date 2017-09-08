package com.dianjiake.android.data.bean;

import java.util.ArrayList;

/**
 * Created by lfs on 2017/5/18.
 */

public class BaseListBean<T> {

    private int code;
    private ObjectBean<T> obj;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ObjectBean<T> getObj() {
        return obj;
    }

    public void setObj(ObjectBean<T> obj) {
        this.obj = obj;
    }

    public static class ObjectBean<O> {
        private ArrayList<O> list;
        private int weipinglunshu;
        private int zongtiaoshu;
        private int zongyeshu;
        private int page;

        public int getWeipinglunshu() {
            return weipinglunshu;
        }

        public void setWeipinglunshu(int weipinglunshu) {
            this.weipinglunshu = weipinglunshu;
        }

        public ArrayList<O> getList() {
            return list;
        }

        public void setList(ArrayList<O> list) {
            this.list = list;
        }

        public int getZongtiaoshu() {
            return zongtiaoshu;
        }

        public void setZongtiaoshu(int zongtiaoshu) {
            this.zongtiaoshu = zongtiaoshu;
        }

        public int getZongyeshu() {
            return zongyeshu;
        }

        public void setZongyeshu(int zongyeshu) {
            this.zongyeshu = zongyeshu;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }
    }
}
