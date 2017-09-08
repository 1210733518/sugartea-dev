package com.dianjiake.android.request;


import com.dianjiake.android.data.bean.BaseListBean;
import com.dianjiake.android.util.CheckEmptyUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;

/**
 * Created by Sacowiw on 2017/6/8.
 */

public abstract class OrderListObserver<O> implements Observer<BaseListBean<O>> {

    @Override
    public void onNext(@NonNull BaseListBean<O> t) {
        if (t == null) {
            onError(new Throwable("no data"));
        } else if (t.getCode() == 200) {
            if (CheckEmptyUtil.isEmpty(t.getObj().getList())) {
                if (t.getObj().getPage() == 1 || t.getObj().getPage() == 0) {
                    onEmpty();
                } else {
                    onAll();
                }
            } else {
                onSuccess(t.getObj().getList(), t.getObj().getPage() == t.getObj().getZongyeshu(), t.getObj().getWeipinglunshu());
            }
        } else {
            onError(new Throwable("error code: " + t.getCode()));
        }
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(List<O> list, boolean isAll, int noCommentCount);

    public abstract void onEmpty();

    public abstract void onAll();
}
