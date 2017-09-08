package com.dianjiake.android.base;

/**
 * Created by lfs on 2017/6/7.
 */

public interface BaseListView {
    //加载中
    void loading();
    //加载完成
    void loadComplete();
    //加载完所有的数据了
    void loadAll();
    //加载的东西是空的
    void loadEmptyContent();
    //网络异常
    void loadNetworkError();
}
