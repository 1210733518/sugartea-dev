package com.dianjiake.android.ui.searchshop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.view.View;

import com.dianjiake.android.R;
import com.dianjiake.android.base.BaseListFragment;
import com.dianjiake.android.ui.common.ShopListAdapter;
import com.dianjiake.android.util.CheckEmptyUtil;
import com.dianjiake.android.view.widget.BaseLoadMoreAdapter;

/**
 * Created by lfs on 2017/7/12.
 */

public class ShopResultFragment extends BaseListFragment<ShopResultPresenter> implements ShopResultContract.View {
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
    protected ShopResultPresenter getPresenter() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            return new ShopResultPresenter(this);
        } else {
            return new ShopResultPresenter(this, bundle.getString("id"));
        }
    }

    @Override
    protected BaseLoadMoreAdapter provideAdapter() {
        return new ShopListAdapter(presenter.getItems());
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
}
