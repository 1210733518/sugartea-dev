package com.dianjiake.android.data.bean;

/**
 * 虚幻bean，非正常bean
 * Created by Sacowiw on 2017/6/13.
 */

public class BaseUnrealBean<T> {
    private int code;
    private ObjBean<T> obj;

    public ObjBean<T> getObj() {
        return obj;
    }

    public void setObj(ObjBean<T> obj) {
        this.obj = obj;
    }

    public int getCode() {

        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class ObjBean<T> {
        private T list;

        public T getList() {
            return list;
        }

        public void setList(T list) {
            this.list = list;
        }
    }
}
