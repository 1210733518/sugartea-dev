package com.dianjiake.android.ui.subscribe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dianjiake.android.R;
import com.dianjiake.android.base.BaseListFragment;
import com.dianjiake.android.base.BaseListView;

/**
 * Created by lfs on 2017/7/18.
 */

public abstract class BaseServiceStaffFragment extends BaseListFragment<BaseServiceStaffPresenter> implements BaseListView {

    protected String shopId, serviceId, openId;


    public static Bundle getBundle(String shopId, String serviceId, String openId) {
        Bundle b = new Bundle();
        b.putString("shopId", shopId);
        b.putString("serviceId", serviceId);
        b.putString("openId", openId);
        return b;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shopId = getArguments().getString("shopId");
        serviceId = getArguments().getString("serviceId");
        openId = getArguments().getString("openId");
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_comment_list;
    }

    @Override
    protected void viewCreated(View view, @Nullable Bundle savedInstanceState) {

    }


    @Override
    public void onLoadMore() {
        presenter.load(false);
    }

    @Override
    public void onReload() {
        presenter.reload();
    }

}
