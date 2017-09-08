package com.dianjiake.android.event;

/**
 * Created by lfs on 2017/7/13.
 */

public class SearchShopEvent {
    private String search;

    public SearchShopEvent(String search) {
        this.search = search;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
