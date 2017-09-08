package com.dianjiake.android.data.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lfs on 2017/7/12.
 */
@Entity
public class SearchHistoryModel {
    @Id
    @Unique
    private Long id;
    private int type;
    @Unique
    private String search;
    private Long create_at;
    private Long update_at;
    @Generated(hash = 361336774)
    public SearchHistoryModel(Long id, int type, String search, Long create_at,
            Long update_at) {
        this.id = id;
        this.type = type;
        this.search = search;
        this.create_at = create_at;
        this.update_at = update_at;
    }
    @Generated(hash = 2050687136)
    public SearchHistoryModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getSearch() {
        return this.search;
    }
    public void setSearch(String search) {
        this.search = search;
    }
    public Long getCreate_at() {
        return this.create_at;
    }
    public void setCreate_at(Long create_at) {
        this.create_at = create_at;
    }
    public Long getUpdate_at() {
        return this.update_at;
    }
    public void setUpdate_at(Long update_at) {
        this.update_at = update_at;
    }

}
