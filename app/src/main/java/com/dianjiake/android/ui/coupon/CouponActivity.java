package com.dianjiake.android.ui.coupon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.base.BaseListActivity;
import com.dianjiake.android.view.widget.BaseLoadMoreAdapter;
import com.dianjiake.android.view.widget.PtrListLayout;
import com.dianjiake.android.view.widget.ToolbarSpaceView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lfs on 2017/7/25.
 */

public class CouponActivity extends BaseListActivity<CouponContract.Presenter> implements CouponContract.View {
    @BindView(R.id.toolbar_space)
    ToolbarSpaceView toolbarSpace;
    @BindView(R.id.toolbar_icon_left)
    ImageView toolbarIconLeft;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_icon_right)
    ImageView toolbarIconRight;
    @BindView(R.id.toolbar_divider)
    ImageView toolbarDivider;
    @BindView(R.id.toolbar_holder)
    ConstraintLayout toolbarHolder;
    @BindView(R.id.ptr_list)
    PtrListLayout ptrList;

    @Override
    public int provideContentView() {
        return R.layout.activity_list;
    }

    @Override
    public void create(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("我的优惠券");
    }

    @Override
    public CouponContract.Presenter getPresenter() {
        return new CouponPresenter(this);
    }

    @Override
    protected BaseLoadMoreAdapter provideAdapter() {
        return new CouponAdapter(presenter.getItems());
    }

    @Override
    public void onLoadMore() {
        presenter.load(false);
    }

    @Override
    public void onReload() {
        presenter.reload();
    }

    @OnClick(R.id.toolbar_icon_left)
    void clickBack(View v) {
        finish();
    }

}
