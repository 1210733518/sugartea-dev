package com.dianjiake.android.ui.msg;

import com.dianjiake.android.api.Network;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.data.bean.BaseBean;
import com.dianjiake.android.data.model.MsgModel;
import com.dianjiake.android.data.db.LoginInfoDBHelper;
import com.dianjiake.android.data.db.MsgDBHelper;
import com.dianjiake.android.data.model.LoginInfoModel;
import com.dianjiake.android.request.ListObserver;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lfs on 2017/7/26.
 */

public class MsgPresenter implements MsgContract.Presenter {
    CompositeDisposable cd;
    List<MsgModel> items;
    LoginInfoModel loginInfo;
    MsgContract.View view;
    int page = 1;
    MsgDBHelper msgDBHelper;

    public MsgPresenter(MsgContract.View view) {
        this.view = view;
        cd = new CompositeDisposable();
        items = new ArrayList<>();
        loginInfo = LoginInfoDBHelper.newInstance().getLoginInfo();
        msgDBHelper = MsgDBHelper.newInstance();
    }

    @Override
    public void start() {

    }

    @Override
    public void onDestroy() {
        cd.clear();
    }

    @Override
    public void reload() {
        page = 1;
        load(true);
    }

    @Override
    public void load(final boolean isReload) {
        Network.getInstance().msgList(BSConstant.MSG_LIST, loginInfo.getOpenId(), page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new ListObserver<MsgModel>() {
                    @Override
                    public void onSuccess(List<MsgModel> list, boolean isAll) {
                        if (isReload) {
                            items.clear();
                        }
                        items.addAll(list);
                        msgDBHelper.save(list);

                        if (isAll) {
                            view.loadAll();
                        } else {
                            view.loadComplete();
                        }
                    }

                    @Override
                    public void onEmpty() {
                        view.loadEmptyContent();
                    }

                    @Override
                    public void onAll() {
                        view.loadAll();
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.loadNetworkError();
                    }
                });
    }

    @Override
    public List<MsgModel> getItems() {
        return items;
    }

    @Override
    public void clickItem(MsgModel msgModel, int position) {
        view.open(msgModel);
        msgDBHelper.delete(msgModel);
        markRead(msgModel);
        if (items.size() > position) {
            items.get(position).setChakan("1");
            view.loadComplete();
        }
    }

    private void markRead(MsgModel msgModel) {
        Network.getInstance().readMsg(BSConstant.MARK_MSG, loginInfo.getOpenId(), msgModel.getId())
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onNext(@NonNull BaseBean baseBean) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
