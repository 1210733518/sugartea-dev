package com.dianjiake.android.view.coupon;

import android.app.FragmentManager;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dianjiake.android.R;
import com.dianjiake.android.api.Network;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.data.bean.BaseBean;
import com.dianjiake.android.data.bean.BaseListBean;
import com.dianjiake.android.data.bean.CouponBean;
import com.dianjiake.android.data.bean.HomeShopBean;
import com.dianjiake.android.data.db.LoginInfoDBHelper;
import com.dianjiake.android.data.model.LoginInfoModel;
import com.dianjiake.android.util.CheckEmptyUtil;
import com.dianjiake.android.util.IntegerUtil;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.util.UIUtil;
import com.dianjiake.android.view.coupon.common.CouponAdapterHelper;
import com.dianjiake.android.view.coupon.common.CouponScaleHelper;
import com.dianjiake.android.view.dialog.GetCouponFailDialog;
import com.dianjiake.android.view.dialog.GetCouponSuccessDialog;
import com.dianjiake.android.view.dialog.NormalProgressDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lfs on 2017/7/24.
 */

public class GetCouponView extends FrameLayout implements CouponView.OnGetListener {
    RecyclerView rv;
    Adapter adapter;
    CompositeDisposable cd;
    LoginInfoModel loginInfo;
    CouponScaleHelper couponScaleHelper;
    FragmentManager fm;
    NormalProgressDialog pd;
    NormalProgressDialog getListPD;
    HomeShopBean shopBean;

    public GetCouponView(@NonNull Context context) {
        super(context);
        init();
    }

    public GetCouponView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GetCouponView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    void init() {
        View view = inflate(getContext(), R.layout.get_coutpon_view, this);
        setClipChildren(false);
        couponScaleHelper = new CouponScaleHelper();
        rv = (RecyclerView) view.findViewById(R.id.coupon_rv);
        cd = new CompositeDisposable();
        adapter = new Adapter(this
        );
        adapter.setOnGetListener(this);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv.setAdapter(adapter);
        couponScaleHelper.attachToRecyclerView(rv);
        loginInfo = LoginInfoDBHelper.newInstance().getLoginInfo();
    }

    public void setFragmentManager(FragmentManager fm) {
        this.fm = fm;
    }

    public void setShop(HomeShopBean shop) {
        if (shop != null) {
            this.shopBean = shop;
            cd.clear();
            adapter.setShopBean(shop);
            Network.getInstance().shopCoupon(
                    BSConstant.SHOP_COUPON,
                    loginInfo.getOpenId(),
                    shop.getId())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new Observer<BaseListBean<CouponBean>>() {
                        @Override
                        public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                            cd.add(d);
                        }

                        @Override
                        public void onNext(@io.reactivex.annotations.NonNull BaseListBean<CouponBean> couponList) {
                            dismissListPD();
                            if (!CheckEmptyUtil.isEmpty(couponList.getObj().getList())) {
                                setVisibility(VISIBLE);
                                adapter.setItems(couponList.getObj().getList());
                            } else {
                                setVisibility(GONE);
                            }
                        }

                        @Override
                        public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                            dismissListPD();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    public void regetList() {
        if (this.shopBean != null) {
//            showListPD();
            setShop(this.shopBean);
        }
    }

    void showListPD() {
        if (getListPD == null) {
            getListPD = NormalProgressDialog.newInstance("获取优惠券列表，请稍候...");
        }

        if (getListPD != null) {
            getListPD.showDialog(fm, "getListPD");
        }
    }

    void dismissListPD() {
        if (getListPD != null && getListPD.isAdded()) {
            getListPD.dismissAllowingStateLoss();
        }
    }

    void showPD() {
        if (pd == null) {
            pd = NormalProgressDialog.newInstance("正在领取，请稍候...");
        }

        if (fm != null) {
            pd.showDialog(fm, "pd");
        }

    }

    void dismissPD() {
        if (pd != null && pd.isAdded()) {
            pd.dismissAllowingStateLoss();
        }
    }

    void showGetSuccessDialog(CouponBean couponBean, int position) {
        if (getContext() != null) {
            IntentUtil.startActivity(getContext(), GetCouponSuccessDialog.getStartIntent(couponBean, shopBean));
        }
        if (adapter != null) {
            setVisibility(adapter.removeItem(position) == 0 ? GONE : VISIBLE);
        }

    }

    void showGetFailDialog(int code) {
        if (getContext() != null) {
            IntentUtil.startActivity(getContext(), GetCouponFailDialog.getStartIntent(code));
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        cd.clear();
        super.onDetachedFromWindow();
    }

    @Override
    public void onGet(final CouponBean coupon, final int position) {
        cd.clear();
        showPD();
        Network.getInstance().getCoupon(BSConstant.GET_COUPON, loginInfo.getOpenId(), coupon.getId(), null, null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull BaseBean baseBean) {
                        dismissPD();
                        if (baseBean.getCode() == 200) {
                            showGetSuccessDialog(coupon, position);
                        } else {
                            showGetFailDialog(baseBean.getCode());
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        dismissPD();
                        showGetFailDialog(4444);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public static class Adapter extends RecyclerView.Adapter<ViewHolder> {
        private List<CouponBean> items = new ArrayList<>();
        private CouponAdapterHelper couponAdapterHelper = new CouponAdapterHelper();
        private HomeShopBean shopBean;
        CouponView.OnGetListener onGetListener;
        GetCouponView getCouponView;

        public Adapter(GetCouponView getCouponView) {
            this.getCouponView = getCouponView;
        }

        public void setShopBean(HomeShopBean shopBean) {
            this.shopBean = shopBean;
        }

        public void setItems(List<CouponBean> items) {
            this.items.clear();
            if (!CheckEmptyUtil.isEmpty(items)) {
                this.items.addAll(items);
            }
            notifyDataSetChanged();
        }

        public int removeItem(int position) {
            if(getCouponView!=null){
                getCouponView.regetList();
            }
//            if (position < items.size()) {
//                int count = IntegerUtil.parseInt(items.get(position).getLingqushuliang());
//                count--;
//                items.get(position).setLingqushuliang(count + "");
//                if (count < 1) {
//                    items.remove(position);
//                }
//            }
//            notifyDataSetChanged();
            return items.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = UIUtil.inflate(R.layout.item_coupon_view, parent);
            couponAdapterHelper.onCreateViewHolder(parent, view);
            return new ViewHolder(view, this);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            couponAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
            holder.setItem(items.get(position), shopBean);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void setOnGetListener(CouponView.OnGetListener onGetListener) {
            this.onGetListener = onGetListener;
        }

        public void onGet(CouponBean couponBean, int position) {
            if (onGetListener != null) {
                onGetListener.onGet(couponBean, position);
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements CouponView.OnGetListener {
        CouponView couponView;
        Adapter adapter;

        public ViewHolder(View itemView, Adapter adapter) {
            super(itemView);
            this.adapter = adapter;
            couponView = (CouponView) itemView.findViewById(R.id.cv);
            couponView.setOnGetListener(this);
        }

        public void setItem(CouponBean couponBean, HomeShopBean shopBean) {
            couponView.setCoupon(couponBean, shopBean, getAdapterPosition());
        }

        @Override
        public void onGet(CouponBean coupon, int position) {
            adapter.onGet(coupon, position);
        }
    }
}
