package com.dianjiake.android.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;


import com.dianjiake.android.R;
import com.dianjiake.android.service.InitPushService;
import com.dianjiake.android.service.ReceivePushService;
import com.dianjiake.android.util.UIUtil;
import com.dianjiake.android.view.widget.BaseLoadMoreAdapter;
import com.dianjiake.android.view.widget.LoadMoreRecyclerView;
import com.dianjiake.android.view.widget.LoadingLayout;
import com.dianjiake.android.view.widget.PtrListLayout;
import com.igexin.sdk.PushManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lfs on 2017/6/9.
 */

public abstract class BaseListActivity<P extends BaseListPresenter> extends AppCompatActivity implements BaseListView
        , LoadingLayout.OnReloadListener, LoadMoreRecyclerView.OnLoadMoreListener {
    private Unbinder unbinder;
    protected P presenter;
    protected PtrListLayout ptrListLayout;

    @Override
    public void loading() {
        ptrListLayout.statusLoading();
    }

    @Override
    public void loadComplete() {
        ptrListLayout.statusSuccess();

    }

    @Override
    public void loadAll() {
        ptrListLayout.statusAll();
    }

    @Override
    public void loadEmptyContent() {
        ptrListLayout.statusEmpty();
    }

    @Override
    public void loadNetworkError() {
        ptrListLayout.statusError();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            UIUtil.flymeSetStatusBarLightMode(getWindow(), true);
            UIUtil.miuiSetStatusBarLightMode(getWindow(), true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        }
        setContentView(provideContentView());
        unbinder = ButterKnife.bind(this);
        presenter = getPresenter();
        if (presenter != null) {
            presenter.start();
        }
        ptrListLayout = (PtrListLayout) findViewById(R.id.ptr_list);
        ptrListLayout.setAdapter(provideAdapter());
        ptrListLayout.setOnReloadListener(this);
        ptrListLayout.setOnLoadMoreListener(this);
        create(savedInstanceState);
        onReload();
        PushManager.getInstance().initialize(this.getApplicationContext(), InitPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), ReceivePushService.class);
    }


    public void setNeedLoadMore(boolean need) {
        ptrListLayout.setNeedLoadMore(need);
    }

    public void setNeedPtr(boolean need) {
        ptrListLayout.setNeedPtr(need);
    }

    @LayoutRes
    public abstract int provideContentView();

    public abstract void create(@Nullable Bundle savedInstanceState);

    public abstract P getPresenter();

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.onDestroy();
            ptrListLayout.destroy();
        }
        super.onDestroy();
        unbinder.unbind();
    }

    protected abstract BaseLoadMoreAdapter provideAdapter();
}
