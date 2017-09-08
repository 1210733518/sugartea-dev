package com.dianjiake.android.ui.shopdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dianjiake.android.R;
import com.dianjiake.android.base.BaseListFragment;
import com.dianjiake.android.base.BaseListView;

/**
 * Created by lfs on 2017/7/17.
 */

public abstract class BaseShopContentFragment extends BaseListFragment<BaseShopContentPresenter> implements BaseListView {
    protected String shopId;


    public static Bundle getBundle(String shopId) {
        Bundle b = new Bundle();
        b.putString("shopId", shopId);
        return b;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shopId = getArguments().getString("shopId");
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_comment_list;
    }

    @Override
    protected void viewCreated(View view, @Nullable Bundle savedInstanceState) {
        setNeedPtr(false);
    }


    @Override
    public void onLoadMore() {
        presenter.load(false);
    }

    @Override
    public void onReload() {
        presenter.reload();
    }


    public void setAppbarOffset(int appbarOffset) {
        if (ptrListLayout != null) {
            ptrListLayout.setAppBarOffset(appbarOffset);
        }

    }
}
