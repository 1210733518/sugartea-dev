package com.dianjiake.android.ui.searchshop;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lfs on 2017/7/12.
 */

public class SearchShopAdapter extends FragmentPagerAdapter {
    List<String> titles;
    List<Fragment> fragments;

    public SearchShopAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
        titles = new ArrayList<>();
        titles.add("项目");
        titles.add("商家");
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public List<String> getTitles() {
        return titles;
    }
}
