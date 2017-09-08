package com.dianjiake.android.ui.orderdetail;

import android.content.DialogInterface;
import android.widget.Toast;

import com.dianjiake.android.api.Network;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.data.bean.BaseBean;
import com.dianjiake.android.data.bean.BaseUnrealBean;
import com.dianjiake.android.data.bean.OrderBean;
import com.dianjiake.android.data.db.LoginInfoDBHelper;
import com.dianjiake.android.data.model.LoginInfoModel;
import com.dianjiake.android.ui.subscribe.SubscribeActivity;
import com.dianjiake.android.util.EventUtil;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.util.ToastUtil;
import com.dianjiake.android.view.dialog.NormalAlertDialog;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lfs on 2017/7/20.
 */

public class OrderDetailPresenter implements OrderDetailContract.Presenter {
    OrderDetailContract.View view;
    OrderBean orderBean;
    CompositeDisposable cd;
    LoginInfoModel loginInfo;

    public OrderDetailPresenter(OrderDetailContract.View view) {
        this.view = view;
        cd = new CompositeDisposable();
        loginInfo = LoginInfoDBHelper.newInstance().getLoginInfo();
    }

    @Override
    public void start() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void setOrderBean(OrderBean orderBean) {
        this.orderBean = orderBean;
    }

    public void cancelOrder() {

        cd.clear();
        view.showCancelPD();
        Network.getInstance().orderCancel(BSConstant.ORDER_CANCEL, loginInfo.getOpenId(), orderBean.getOrdernum())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onNext(@NonNull BaseBean baseBean) {
                        view.dismissCancelPD();
                        if (baseBean.getCode() == 200) {
                            ToastUtil.showShortToast("取消预约成功");
                            orderBean.setStatus("4");
                            view.setView(orderBean);
                            EventUtil.postRefreshOrderList();
                        } else {
                            ToastUtil.showShortToast("取消预约失败，请稍候重试");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ToastUtil.showShortToast("取消预约失败，请稍候重试");
                        view.dismissCancelPD();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void call() {
        IntentUtil.startCall(view.provideContext(), orderBean.getDianpu().getDianhua());
    }

    @Override
    public void reSub() {
        IntentUtil.startActivity(view.provideContext(), SubscribeActivity.getStartIntent(orderBean.getDianpu().getId()));
    }

    @Override
    public void evaluate() {

    }

    @Override
    public void getOrderDetail(String shopId, String orderId) {
        cd.clear();
        view.showGetPD();
        Network.getInstance().orderDetail(BSConstant.ORDER_DETAIL, shopId, orderId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new Observer<BaseUnrealBean<OrderBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onNext(@NonNull BaseUnrealBean<OrderBean> bean) {
                        view.dismissGetPD();
                        if (bean.getCode() == 200) {
                            setOrderBean(bean.getObj().getList());
                            view.setView(bean.getObj().getList());
                        } else {
                            ToastUtil.showShortToast("请求失败，请返回重试");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.dismissGetPD();
                        ToastUtil.showShortToast("请求失败，请返回重试");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
