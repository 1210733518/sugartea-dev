package com.dianjiake.android.runnabl;

/**
 * Created by guaju on 2017/8/4.
 */

public abstract  class CustomRunnable implements Runnable {
    String info;


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
