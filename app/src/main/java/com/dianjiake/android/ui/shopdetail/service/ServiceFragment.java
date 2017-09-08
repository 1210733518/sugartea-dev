package com.dianjiake.android.ui.shopdetail.service;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dianjiake.android.data.bean.ServiceBean;
import com.dianjiake.android.ui.common.ServiceListAdapter;
import com.dianjiake.android.ui.shopdetail.BaseShopContentFragment;
import com.dianjiake.android.ui.shopweb.ShopWebActivity;
import com.dianjiake.android.view.widget.BaseLoadMoreAdapter;

/**
 * Created by lfs on 2017/7/17.
 */

public class ServiceFragment extends BaseShopContentFragment implements ServiceContract.View, BaseLoadMoreAdapter.OnItemClickListener {

    @Override
    protected ServicePresenter getPresenter() {
        return new ServicePresenter(this, shopId);
    }

    @Override
    protected BaseLoadMoreAdapter provideAdapter() {
        ServiceListAdapter adapter = new ServiceListAdapter(presenter.getItems(), false,true);
        adapter.setOnItemClickListener(this);
        return adapter;
    }

    @Override
    protected void viewCreated(View view, @Nullable Bundle savedInstanceState) {

    }


    @Override
    public void onClick(Object t, int position) {
        startActivity(ShopWebActivity.getServiceDetail((ServiceBean) t));
    }
}
