package com.dianjiake.android.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dianjiake.android.R;
import com.dianjiake.android.view.widget.BaseLoadMoreAdapter;
import com.dianjiake.android.view.widget.LoadMoreRecyclerView;
import com.dianjiake.android.view.widget.LoadingLayout;
import com.dianjiake.android.view.widget.PtrListLayout;


/**
 * Created by lfs on 2017/6/7.
 */
                                      //让继承我这个fragment子类 多了一个限定
                                      //必须要继承我这这个BaseListPresenter                              //mvp中的view
public abstract class BaseListFragment<P extends BaseListPresenter> extends BaseFragment<P> implements BaseListView
        , LoadingLayout.OnReloadListener,LoadMoreRecyclerView.OnLoadMoreListener {
    //TODO 没有用      LoadMoreRecyclerView 而用得是PtrListLayout
    protected PtrListLayout ptrListLayout; //下拉刷新
    public BaseLoadMoreAdapter baseLoadMoreAdapter;  //上拉加载
    //钩子
    private int hook=0;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ptrListLayout = (PtrListLayout) view.findViewById(R.id.ptr_list);
        if (hook==0){
            //如果是零的话就添加适配器，内容适配器，包含一个上拉加载更多
        baseLoadMoreAdapter = provideAdapter();
        ptrListLayout.setAdapter(baseLoadMoreAdapter);
        }else{
            //如果不是零的话就不添加
            provideAdapterHook();
        }

        ptrListLayout.setOnReloadListener(this);
        ptrListLayout.setOnLoadMoreListener(this);
        //如果项目中用到了，scrollview 包含listview那么肯定会遇到listview抢焦点，必须执行这个方法
        ptrListLayout.scrollTo(0,0);
        //让子类去用黄油刀倒id
        viewCreated(view, savedInstanceState);
        onReload();
    }
    //加载中
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
    //设置是否要加载更多
    public void setNeedLoadMore(boolean need) {
        ptrListLayout.setNeedLoadMore(need);
    }
    //设置是否要下拉刷新
    public void setNeedPtr(boolean need){
        ptrListLayout.setNeedPtr(need);
    }
    //释放资源
    @Override
    public void onDestroyView() {
        ptrListLayout.destroy();
        super.onDestroyView();
    }
    //想让子类去实现，这儿的意思是想让homeFragment去实现
    protected abstract BaseLoadMoreAdapter provideAdapter();


    //让子类去提供钩子（条件)
    protected  void provideAdapterHook(){

    };
    //设置钩子的状态
    public void setHook(int hook){
          this.hook=hook;
    }
    //让子类去初始化view 或者其他更view相关的操作
    protected abstract void viewCreated(View view, @Nullable Bundle savedInstanceState);


}
