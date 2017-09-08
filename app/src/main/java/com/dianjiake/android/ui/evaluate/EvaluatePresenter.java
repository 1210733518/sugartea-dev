package com.dianjiake.android.ui.evaluate;

import com.dianjiake.android.api.Network;
import com.dianjiake.android.api.params.EvaluateParams;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.data.bean.BaseBean;
import com.dianjiake.android.data.bean.EvaluateBean;
import com.dianjiake.android.data.bean.OrderBean;
import com.dianjiake.android.data.bean.OrderServiceBean;
import com.dianjiake.android.data.db.LoginInfoDBHelper;
import com.dianjiake.android.data.model.LoginInfoModel;
import com.dianjiake.android.util.CheckEmptyUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lfs on 2017/7/20.
 */

public class EvaluatePresenter implements EvaluateContract.Presenter {
    EvaluateContract.View view;
    ArrayList<OrderServiceBean> items;
    List<EvaluateBean> evaluates;
    CompositeDisposable cd;
    String orderNum, shopId;
    LoginInfoModel loginInfo;
    OrderBean orderBean;

    public EvaluatePresenter(EvaluateContract.View view) {
        this.view = view;
        items = new ArrayList<>();
        evaluates = new ArrayList<>();
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
    public void reload() {

    }

    @Override
    public void load(boolean isReload) {

    }

    @Override
    public void setServices(OrderBean orderBean) {
        this.orderBean = orderBean;
        orderNum = orderBean.getOrdernum();
        shopId = orderBean.getDianpu().getId();
        ArrayList<OrderServiceBean> services = orderBean.getDingdanfuwu();
        items.clear();
        if (CheckEmptyUtil.isEmpty(services)) {
            view.loadEmptyContent();
        } else {
            items.addAll(services);
            for (OrderServiceBean b : services) {
                EvaluateBean evaluateBean = new EvaluateBean();
                evaluateBean.setPingfen(5);
                evaluateBean.setBeipinglunopenid(b.getFuwuopenid());
                evaluateBean.setFuwuid(b.getFuwuid());
                evaluates.add(evaluateBean);
            }
            OrderServiceBean bean = new OrderServiceBean();
            bean.setViewType(EvaluateType.BUTTON);
            items.add(bean);
            view.loadAll();
        }
    }

    @Override
    public void setComment(String comment, int position) {
        if (evaluates.size() > position) {
            evaluates.get(position).setNeirong(comment);
        }
    }

    @Override
    public void setRate(int rate, int position) {
        if (evaluates.size() > position) {
            evaluates.get(position).setPingfen(rate);
            items.get(position).setTempStar(rate);
        }
    }

    @Override
    public ArrayList<OrderServiceBean> getItems() {
        return items;
    }

    @Override
    public void submit() {
        view.showPD();
        cd.clear();
        EvaluateParams evaluateParams = new EvaluateParams();
        evaluateParams.setOpenId(loginInfo.getOpenId());
        evaluateParams.setOrderNum(orderNum);
        evaluateParams.setShopId(shopId);
        evaluateParams.setPinglunlist(new Gson().toJson(evaluates));
        Network.getInstance().evaluateOrder(evaluateParams.getRequestParams()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onNext(@NonNull BaseBean baseBean) {
                        view.dismissPD();
                        if (baseBean.getCode() == 200) {
                            orderBean.setStatus("3");
                            orderBean.setShifoupinglun("1");
                            view.evaluateSuccess();
                        } else {
                            view.evaluateFail();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.dismissPD();
                        view.evaluateFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public OrderBean getOrderBean() {
        return orderBean;
    }

}
