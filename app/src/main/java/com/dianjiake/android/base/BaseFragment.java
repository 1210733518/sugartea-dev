package com.dianjiake.android.base;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lfs on 2017/5/19.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment {

    Unbinder unbinder;
    protected P presenter;
    protected Activity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //就不需要getActivity()了
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //提供providelayout()方法返回layout id ，然后返回view
        View view = inflater.inflate(provideLayout(), container, false);
        //通过上一步的view实现ButterKnife的初始化，这样子类就不需要初始化binder了，直接获取id就可以了
        unbinder = ButterKnife.bind(this, view);
        //实例化presenter,通过getPresenter的抽象方法
        presenter = getPresenter();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //初始化好presenter之后，
        if (presenter != null) {
            presenter.start();
        }
    }

    @LayoutRes
    protected abstract int provideLayout();

    protected abstract P getPresenter();

    //释放资源
    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }
    //释放资源
    @Override
    public void onDestroyView() {
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onDestroyView();
        unbinder.unbind();
    }


}
