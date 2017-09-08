package com.dianjiake.android.base;

/**
 * Created by lfs on 2017/6/7.
 */

public interface BaseListPresenter extends BasePresenter {
    //现在此presenter拥有四个抽象方法即：start  destory 还有下边这个两个
    void reload();

    void load(boolean isReload);
}
