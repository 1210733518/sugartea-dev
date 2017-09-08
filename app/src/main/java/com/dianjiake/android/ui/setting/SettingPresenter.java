package com.dianjiake.android.ui.setting;

import com.dianjiake.android.api.Network;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.data.bean.BaseBean;
import com.dianjiake.android.data.bean.LoginBean;
import com.dianjiake.android.data.db.LoginInfoDBHelper;
import com.dianjiake.android.data.model.LoginInfoModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lfs on 2017/7/20.
 */

public class SettingPresenter implements SettingContract.Presenter {

    LoginInfoModel loginInfo;
    CompositeDisposable cd;
    SettingContract.View view;

    public SettingPresenter(SettingContract.View view) {
        this.view = view;
        cd = new CompositeDisposable();
        loginInfo = LoginInfoDBHelper.newInstance().getLoginInfo();
        view.setPhone(loginInfo.getPhone());
    }


    @Override
    public void start() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void getInfo() {
        cd.clear();
        Network.getInstance().getUserInfo(BSConstant.USER_INFO, loginInfo.getOpenId(), null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new Observer<BaseBean<LoginBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseBean<LoginBean> baseBean) {
                        if (baseBean.getCode() == 200 && baseBean.getObj().getUser() != null) {
                            loginInfo.setPhone(baseBean.getObj().getUser().getPhone());
                            view.setPhone(baseBean.getObj().getUser().getPhone());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public LoginInfoModel getLoginInfo() {
        return loginInfo;
    }
}
