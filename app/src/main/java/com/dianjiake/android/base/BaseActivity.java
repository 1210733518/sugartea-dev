package com.dianjiake.android.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dianjiake.android.service.InitPushService;
import com.dianjiake.android.service.ReceivePushService;
import com.igexin.sdk.PushManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lfs on 2017/5/11.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    private Unbinder unbinder;
    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideContentView());
        unbinder = ButterKnife.bind(this);
        presenter = getPresenter();
        if (presenter != null) {
            presenter.start();
        }
        create(savedInstanceState);
        PushManager.getInstance().initialize(this.getApplicationContext(), InitPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), ReceivePushService.class);
    }

    @LayoutRes
    public abstract int provideContentView();

    public abstract void create(@Nullable Bundle savedInstanceState);

    public abstract P getPresenter();

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onDestroy();
        unbinder.unbind();
    }
}
