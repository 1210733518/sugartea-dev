package com.dianjiake.android.ui.common;

import android.os.Handler;

import com.dianjiake.android.api.Network;
import com.dianjiake.android.base.BaseListPresenter;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.data.bean.BaseBean;
import com.dianjiake.android.data.bean.BaseListBean;
import com.dianjiake.android.data.bean.OrderBean;
import com.dianjiake.android.data.db.LoginInfoDBHelper;
import com.dianjiake.android.data.db.MsgDBHelper;
import com.dianjiake.android.data.model.LoginInfoModel;
import com.dianjiake.android.event.RefreshOrderListEvent;
import com.dianjiake.android.request.OrderListObserver;
import com.dianjiake.android.ui.main.OrderFragment;
import com.dianjiake.android.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lfs on 2017/7/20.
 */

public abstract class BaseOrderPresenter implements BaseListPresenter {
    public OrderView view;
    public List<OrderBean> items;
    public CompositeDisposable cd;
    public LoginInfoModel loginInfo;
    public int page = 1;
    OrderBean orderItem;
    MsgDBHelper msgDBHelper;
    private Handler handler;
    private OrderFragment ofragment;

    public BaseOrderPresenter(OrderView view) {
        this.view = view;
        items = new ArrayList<>();
        cd = new CompositeDisposable();
        loginInfo = LoginInfoDBHelper.newInstance().getLoginInfo();
        EventBus.getDefault().register(this);
        msgDBHelper = MsgDBHelper.newInstance();
    }

    @Override
    public void start() {

    }

    @Override
    public void onDestroy() {
        cd.clear();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void reload() {
        page = 1;
        load(true);
    }

    @Override
    public void load(final boolean isReload) {
        cd.clear();
        provideApi()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new OrderListObserver<OrderBean>() {
                    @Override
                    public void onSuccess(List<OrderBean> list, final boolean isAll, int noCommentCount) {
                        if (isReload) {
                            items.clear();
                            if (ofragment!=null){

                            ofragment.ptrList.clearAdapter();
                            }
                        }
                        if (isReload && view.getShowNoCommentHolder() && noCommentCount > 0) {
                            OrderBean orderBean = new OrderBean();
                            orderBean.setViewType(OrderViewType.HEADER);
                            items.add(orderBean);
                            view.setNoCommentCount(noCommentCount);
                        }
                        items.addAll(list);
                        page++;
//                        Looper mainLooper = App.getInstance().getMainLooper();
//                        handler = new Handler(mainLooper);
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                        initStuff(isAll);
//                            }
//                        });
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

//    private void initStuff(final boolean isAll) {
//        for (final OrderBean orderItem:items) {
//            Observable<BaseListBean<UserInfoBean>> baseListBeanObservable =
//                    Network.getInstance().serviceStaff(BSConstant.SHOP_STAFF, orderItem.getDianpu().getId());
//            baseListBeanObservable.observeOn(AndroidSchedulers.mainThread())
//                    .subscribeOn(Schedulers.io())
//                    .subscribeWith(new Observer<BaseListBean<UserInfoBean>>() {
//                        @Override
//                        public void onSubscribe(@NonNull Disposable disposable) {
//                        }
//
//                        @Override
//                        public void onNext(@NonNull BaseListBean<UserInfoBean> userInfoBeanBaseListBean) {
//                            BaseListBean.ObjectBean<UserInfoBean> obj = userInfoBeanBaseListBean.getObj();
//                            ArrayList<UserInfoBean> list = obj.getList();
//                            ArrayList<OrderServiceBean> dingdanfuwu = orderItem.getDingdanfuwu();
//                            for (OrderServiceBean it : dingdanfuwu) {
//                                for (UserInfoBean user : list) {
//                                    if (it.getFuwuopenid() != null && it.getFuwuopenid().equals(user.getOpenid())) {
//                                        it.setName(user.getName());
//                                        Log.e("guaju", "onNext: "+it.toString() );
//                                    }
//                                }
//                            }
//                            if (orderItem==items.get(items.size()-1)){
//                                handler.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        //添加适配器
//                                        OrderListAdapter orderListAdapter = new OrderListAdapter(items, BaseOrderPresenter.this);
//                                        ofragment = (OrderFragment)view;
//                                        ofragment.ptrList.setAdapter(orderListAdapter);
//
//                                        if (isAll) {
//                                            view.loadAll();
//                                        } else {
//                                            view.loadComplete();
//                                        }
//                                    }
//                                });
//                            }
//                        }
//
//                        @Override
//                        public void onError(@NonNull Throwable throwable) {
//                        }
//
//                        @Override
//                        public void onComplete() {
//                        }
//                    });
//        }
//
//
//
//    }

    public void cancelOrder(final OrderBean orderBean, final int position) {
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
                            items.set(position, orderBean);
                            view.loadComplete();
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


    public List<OrderBean> getItems() {
        return items;
    }

    public void removeHeaderItem() {
        if (items.size() > 0 && items.get(0).getViewType() == OrderViewType.HEADER) {
            items.remove(0);
            view.loadComplete();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(RefreshOrderListEvent event) {
        reload();
    }

    public boolean haveUnreadMsg() {
        return msgDBHelper.isUnread();
    }

    public abstract void clickHolder(OrderBean orderBean, int position);

    public abstract void clickCall(OrderBean orderBean, int position);

    public abstract void clickReSub(OrderBean orderBean, int position);

    public abstract void clickCancel(OrderBean orderBean, int position);

    public abstract void clickEvaluate(OrderBean orderBean, int position);

    public abstract Observable<BaseListBean<OrderBean>> provideApi();

}
