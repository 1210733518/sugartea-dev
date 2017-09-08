package com.dianjiake.android.ui.common;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.api.Network;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.data.bean.ADBean;
import com.dianjiake.android.data.bean.BaseBean;
import com.dianjiake.android.data.bean.BaseListBean;
import com.dianjiake.android.data.bean.HomeShopBean;
import com.dianjiake.android.data.db.AppInfoDBHelper;
import com.dianjiake.android.data.db.LoginInfoDBHelper;
import com.dianjiake.android.data.model.AppInfoModel;
import com.dianjiake.android.data.model.LoginInfoModel;
import com.dianjiake.android.event.HomeReloadEvent;
import com.dianjiake.android.ui.main.HomeType;
import com.dianjiake.android.ui.shopdetail.ShopDetailActivity;
import com.dianjiake.android.util.AMapUtil;
import com.dianjiake.android.util.CheckEmptyUtil;
import com.dianjiake.android.util.FloatUtil;
import com.dianjiake.android.util.FrescoUtil;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.util.LongUtil;
import com.dianjiake.android.util.UIUtil;
import com.dianjiake.android.view.widget.ADView;
import com.dianjiake.android.view.widget.BaseLoadMoreAdapter;
import com.dianjiake.android.view.widget.BaseViewHolder;
import com.dianjiake.android.view.widget.HomeFilterView;
import com.dianjiake.android.view.widget.RecommendView;
import com.dianjiake.android.view.widget.StarView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lfs on 2017/7/10.
 */

public class ShopListAdapter extends BaseLoadMoreAdapter<HomeShopBean> {
    public ShopListAdapter(List<HomeShopBean> items) {
        super(items);
    }

    //返回不同类型的viewholder
    @Override
    public void myOnBindViewHolder(BaseViewHolder holder, int position) {
        switch (myGetItemViewType(position)) {
            case HomeType.AD:
                break;
            case HomeType.FILTER:
                break;
            case HomeType.COLLECTION:
                break;
            default:
                ViewHolder vh = (ViewHolder) holder;
                vh.setItem(getItem(position));
        }

    }

    @Override
    public BaseViewHolder myOnCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HomeType.AD:
                return ADHolder.newInstance(parent);
            case HomeType.FILTER:
                return FilterHolder.newInstance(parent);
            case HomeType.COLLECTION:
                return CollectionHolder.newInstance(parent);
            default:
                return ViewHolder.newInstance(parent, this);
        }
    }

    @Override
    public int myGetItemViewType(int position) {
        return getItem(position).getViewType();
    }


    public static class ViewHolder extends BaseViewHolder {

        @BindView(R.id.logo)
        SimpleDraweeView logo;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.distance)
        TextView distance;
        @BindView(R.id.desc)
        TextView desc;
        @BindView(R.id.star)
        StarView star;
        @BindView(R.id.promotion_desc)
        TextView promotionDesc;
        @BindView(R.id.promotion_holder)
        LinearLayout promotionHolder;
        @BindView(R.id.card_desc)
        TextView cardDesc;
        @BindView(R.id.card_holder)
        LinearLayout cardHolder;

        ShopListAdapter adapter;
        HomeShopBean item;

        public static ViewHolder newInstance(ViewGroup parent, ShopListAdapter adapter) {
            return new ViewHolder(UIUtil.inflate(R.layout.item_home_shop, parent), adapter);
        }

        private ViewHolder(View itemView, ShopListAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
        }

        public void setItem(HomeShopBean item) {
            this.item = item;
            logo.setImageURI(FrescoUtil.getShopLogoUri(item.getLogo(), item.getCover()));
            name.setText(item.getMingcheng());
            desc.setVisibility(CheckEmptyUtil.isEmpty(item.getJianjie()) ? View.GONE : View.VISIBLE);
            desc.setText(item.getJianjie());
            distance.setText(AMapUtil.formatDistance(LongUtil.parseLong(item.getJuli())));
            star.setScore(FloatUtil.parseFloat(item.getPingfen()));
            promotionHolder.setVisibility("1".equals(item.getCuxiao()) ? View.VISIBLE : View.GONE);
            promotionDesc.setText(item.getCuxiaofuwumingcheng());
            cardHolder.setVisibility("0".equals(item.getGongkaikaquan()) ? View.GONE : View.VISIBLE);
            cardDesc.setText(item.getKaquanmingcheng());
        }

        @OnClick(R.id.holder)
        void click(View v) {
            IntentUtil.startActivity(v, ShopDetailActivity.getStartIntent(item.getId()));
        }

        @Override
        public void destroy() {

        }
    }

    public static class ADHolder extends BaseViewHolder {

        CompositeDisposable cd;
        ADView adView;

        public static ADHolder newInstance(ViewGroup parent) {
            return new ADHolder(UIUtil.inflate(R.layout.item_home_ad, parent));
        }

        private ADHolder(View itemView) {
            super(itemView);
            adView = (ADView) itemView.findViewById(R.id.timeline_ad_view);
            cd = new CompositeDisposable();
            EventBus.getDefault().register(this);
            onLoad(null);
        }

        @Subscribe(threadMode = ThreadMode.MAIN)
        public void onLoad(HomeReloadEvent event) {
            cd.clear();
            AppInfoModel appInfo = AppInfoDBHelper.newInstance().getAppInfo();
            Network.getInstance().ad(BSConstant.AD, appInfo.getLongitude() + "," + appInfo.getLatitude())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                    .subscribeWith(new Observer<BaseBean<ADBean>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull BaseBean<ADBean> ad) {
                            if (ad.getCode() == 200 && !CheckEmptyUtil.isEmpty(ad.getObj().getContent())) {
                                adView.setItems(ad.getObj().getContent());



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
        public void destroy() {
            cd.clear();
            EventBus.getDefault().unregister(this);
        }
    }

    public static class FilterHolder extends BaseViewHolder {

        public static FilterHolder newInstance(ViewGroup parent) {
            return new FilterHolder(new HomeFilterView(parent.getContext()));
        }

        private FilterHolder(View itemView) {
            super(itemView);
            EventBus.getDefault().post(((HomeFilterView) itemView).getFilterGroup());
        }

        @Override
        public void destroy() {

        }
    }

    public static class CollectionHolder extends BaseViewHolder {

        @BindView(R.id.recommend_views)
        RecommendView recommendViews;

        CompositeDisposable cd;
        LoginInfoModel loginInfo;

        public static CollectionHolder newInstance(ViewGroup parent) {
            return new CollectionHolder(UIUtil.inflate(R.layout.item_home_collection, parent));
        }

        private CollectionHolder(View itemView) {
            super(itemView);
            cd = new CompositeDisposable();
            loginInfo = LoginInfoDBHelper.newInstance().getLoginInfo();
            EventBus.getDefault().register(this);
            reLoad(null);
        }

        @Subscribe(threadMode = ThreadMode.MAIN)
        public void reLoad(HomeReloadEvent event) {
            cd.clear();
            Network.getInstance().homeCollection(BSConstant.HOME_COLLECTION, loginInfo.getOpenId())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new Observer<BaseListBean<HomeShopBean>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            cd.add(d);
                        }

                        @Override
                        public void onNext(@NonNull BaseListBean<HomeShopBean> shop) {
                            if (shop.getCode() == 200 && !CheckEmptyUtil.isEmpty(shop.getObj().getList())) {
                                recommendViews.setItems(shop.getObj().getList());
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
        public void destroy() {
            cd.clear();
            EventBus.getDefault().unregister(this);

        }
    }

}
