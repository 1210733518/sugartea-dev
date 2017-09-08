package com.dianjiake.android.ui.searchshop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dianjiake.android.R;
import com.dianjiake.android.base.BaseListFragment;
import com.dianjiake.android.data.bean.ServiceBean;
import com.dianjiake.android.ui.common.ServiceListAdapter;
import com.dianjiake.android.ui.shopweb.ShopWebActivity;
import com.dianjiake.android.util.CheckEmptyUtil;
import com.dianjiake.android.view.widget.BaseLoadMoreAdapter;

/**
 * Created by lfs on 2017/7/12.
 */

public class ServiceResultFragment extends BaseListFragment<ServiceResultPresenter> implements ServiceResultContract.View, BaseLoadMoreAdapter.OnItemClickListener {

    public static Bundle getBundle(String typeId) {
        Bundle bundle = new Bundle();
        bundle.putString("id", typeId);
        return bundle;
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_comment_list;
    }

    @Override
    protected ServiceResultPresenter getPresenter() {
        Bundle bundle = getArguments();
        if (bundle==null) {
            return new ServiceResultPresenter(this);
        } else {
            return new ServiceResultPresenter(this, bundle.getString("id"));
        }
    }

    @Override
    protected BaseLoadMoreAdapter provideAdapter() {
        BaseLoadMoreAdapter adapter = new ServiceListAdapter(presenter.getItems(), true, true);
        adapter.setOnItemClickListener(this);
        return adapter;
    }

    @Override
    protected void viewCreated(View view, @Nullable Bundle savedInstanceState) {
        ptrListLayout.setNeedPtr(false);
    }

    @Override
    public void onLoadMore() {
        presenter.load(false);
    }

    @Override
    public void onReload() {
        presenter.reload();
    }

    @Override
    public void onClick(Object t, int position) {
        startActivity(ShopWebActivity.getServiceDetail((ServiceBean) t));
    }
}
