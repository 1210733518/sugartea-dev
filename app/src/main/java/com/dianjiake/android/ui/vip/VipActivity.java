package com.dianjiake.android.ui.vip;

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

public class VipActivity extends BaseListActivity<VipPresenter> implements VipContract.View {
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
        ptrListLayout.setNeedLoadMore(false);
        toolbarTitle.setText("会员卡");
    }

    @Override
    public VipPresenter getPresenter() {
        return new VipPresenter(this);
    }

    @Override
    protected BaseLoadMoreAdapter provideAdapter() {
        return new VipAdapter(presenter.getItems());
    }

    @Override
    public void onLoadMore() {

    }

    @OnClick(R.id.toolbar_icon_left)
    void clickBack(View v) {
        finish();
    }

    @Override
    public void onReload() {
        presenter.reload();
    }


}
