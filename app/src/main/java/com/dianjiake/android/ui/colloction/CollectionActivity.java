package com.dianjiake.android.ui.colloction;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.base.BaseListActivity;
import com.dianjiake.android.view.dialog.NormalProgressDialog;
import com.dianjiake.android.view.widget.BaseLoadMoreAdapter;
import com.dianjiake.android.view.widget.PtrListLayout;
import com.dianjiake.android.view.widget.ToolbarSpaceView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lfs on 2017/7/25.
 */

public class CollectionActivity extends BaseListActivity<CollectionPresenter> implements CollectionContract.View {
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

    NormalProgressDialog progressDialog;

    @Override
    public int provideContentView() {
        return R.layout.activity_list;
    }

    @Override
    public void create(@Nullable Bundle savedInstanceState) {
        ptrListLayout.setNeedLoadMore(false);
        toolbarTitle.setText("收藏店铺");
    }

    @Override
    public CollectionPresenter getPresenter() {
        return new CollectionPresenter(this);
    }

    @Override
    protected BaseLoadMoreAdapter provideAdapter() {
        return new CollectionAdapter(presenter.getItems(), presenter);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onReload() {
        presenter.reload();
    }

    @Override
    public FragmentManager provideFM() {
        return getFragmentManager();
    }

    @Override
    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = NormalProgressDialog.newInstance("正在删除，请稍候...");
        }
        progressDialog.showDialog(getFragmentManager(), "progress");
    }

    @Override
    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isAdded()) {
            progressDialog.dismissAllowingStateLoss();
        }
    }

    @OnClick(R.id.toolbar_icon_left)
    void clickBack(View v) {
        finish();
    }
}
