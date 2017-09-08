package com.dianjiake.android.ui.common;

import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.data.bean.HomeShopBean;
import com.dianjiake.android.data.bean.ServiceBean;
import com.dianjiake.android.data.db.LoginInfoDBHelper;
import com.dianjiake.android.ui.main.ServiceType;
import com.dianjiake.android.ui.shopdetail.ShopDetailActivity;
import com.dianjiake.android.ui.shopweb.ShopWebActivity;
import com.dianjiake.android.ui.subscribe.SubscribeActivity;
import com.dianjiake.android.util.FloatUtil;
import com.dianjiake.android.util.FrescoUtil;
import com.dianjiake.android.util.IntegerUtil;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.util.UIUtil;
import com.dianjiake.android.view.widget.BaseLoadMoreAdapter;
import com.dianjiake.android.view.widget.BaseViewHolder;
import com.dianjiake.android.view.widget.StarView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static android.view.View.GONE;

/**
 * Created by lfs on 2017/7/13.
 */

public class ServiceListAdapter extends BaseLoadMoreAdapter<ServiceBean> {
    boolean showShop, showSub;

    public ServiceListAdapter(List<ServiceBean> items) {
        super(items);
    }

    public ServiceListAdapter(List<ServiceBean> items, boolean showShop, boolean showSub) {
        super(items);
        this.showShop = showShop;
        this.showSub = showSub;
    }

    @Override
    public void myOnBindViewHolder(BaseViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        vh.setItem(getItem(position));
    }

    @Override
    public BaseViewHolder myOnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(UIUtil.inflate(R.layout.item_service, parent), showShop, showSub, this);
    }

    @Override
    public int myGetItemViewType(int position) {
        return 0;
    }

    public static class ViewHolder extends BaseViewHolder {

        @BindView(R.id.logo)
        SimpleDraweeView logo;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.service_promotion)
        View servicePromotion;
        @BindView(R.id.sail_count)
        TextView sailCount;
        @BindView(R.id.title_holder)
        LinearLayout titleHolder;
        @BindView(R.id.desc)
        TextView desc;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.price_unit)
        TextView priceUnit;
        @BindView(R.id.old_price)
        TextView oldPrice;
        @BindView(R.id.subscribe)
        TextView subscribe;
        @BindView(R.id.price_holder)
        LinearLayout priceHolder;
        @BindView(R.id.divider)
        ImageView divider;
        @BindView(R.id.shop_name)
        TextView shopName;
        @BindView(R.id.shop_promotion)
        View shopPromotion;
        @BindView(R.id.shop_card)
        View shopCard;
        @BindView(R.id.star)
        StarView star;
        @BindView(R.id.shop_holder)
        LinearLayout shopHolder;
        @BindView(R.id.mark)
        View mark;


        boolean showShop, showSub;
        int size;
        ServiceBean serviceBean;
        ServiceListAdapter adapter;

        public ViewHolder(View itemView, boolean showShop, boolean showSub, ServiceListAdapter adapter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.showShop = showShop;
            this.adapter = adapter;
            this.showSub = showSub;

            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) logo.getLayoutParams();
            if (showShop) {
                size = (int) UIUtil.dp2px(100);
                if (lp == null) {
                    lp = new ConstraintLayout.LayoutParams(size, size);
                } else {
                    lp.width = lp.height = size;
                }
                logo.setLayoutParams(lp);
                divider.setVisibility(View.VISIBLE);
                shopHolder.setVisibility(View.VISIBLE);

            } else {
                size = (int) UIUtil.dp2px(80);
                if (lp == null) {
                    lp = new ConstraintLayout.LayoutParams(size, size);
                } else {
                    lp.width = lp.height = size;
                }
                logo.setLayoutParams(lp);
                divider.setVisibility(GONE);
                shopHolder.setVisibility(GONE);
            }

            subscribe.setVisibility(showSub ? View.VISIBLE : GONE);
        }

        public void setItem(ServiceBean serviceBean) {
            this.serviceBean = serviceBean;
            int sailCountWidth = 0;
            title.setText(serviceBean.getName());
            servicePromotion.setVisibility("1".equals(serviceBean.getCuxiao()) ? View.VISIBLE : GONE);
            logo.setImageURI(FrescoUtil.getServiceUri(serviceBean.getPhoto()));
            desc.setText(serviceBean.getJianjie());
            if (IntegerUtil.parseInt(serviceBean.getYuyueshu()) > 0) {
                sailCountWidth = 80;
                sailCount.setVisibility(View.VISIBLE);
                sailCount.setText("已约" + serviceBean.getYuyueshu() + "单");
            } else {
                sailCountWidth = 0;
                sailCount.setVisibility(GONE);
            }

            title.setMaxWidth(UIUtil.getScreenWidth() - (int) UIUtil.dp2px(sailCountWidth + 32) + size);

            priceUnit.setText(serviceBean.getDanwei());
            if ("1".equals(serviceBean.getCuxiao())) {
                price.setText(serviceBean.getCuxiaojia());
                oldPrice.setVisibility(View.VISIBLE);
                oldPrice.setText(serviceBean.getJine() + serviceBean.getDanwei());
                oldPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            } else {
                price.setText(serviceBean.getJine());
                oldPrice.setVisibility(GONE);
            }

            HomeShopBean shopBean = serviceBean.getDianpu();
            if (shopBean != null) {
                int maxWidth =UIUtil.getScreenWidth() - (int) UIUtil.dp2px(150) -  size;
                shopPromotion.setVisibility("1".equals(shopBean.getCuxiao()) ? View.VISIBLE : GONE);
                shopCard.setVisibility("1".equals(shopBean.getGongkaikaquan()) ? View.VISIBLE : GONE);
                if("1".equals(shopBean.getCuxiao())){
                    maxWidth -= UIUtil.dp2px(10);
                }
                if("1".equals(shopBean.getGongkaikaquan())){
                    maxWidth -= UIUtil.dp2px(10);
                }
                shopName.setMaxWidth(maxWidth);
                shopName.setText(shopBean.getMingcheng());
                star.setScore(FloatUtil.parseFloat(shopBean.getPingfen()));

                Timber.e("size:" + size);
                Timber.e("dp2px:" + UIUtil.dp2px(size + 90));
            }

            mark.setVisibility("1".equals(serviceBean.getFuwumoshi())?View.VISIBLE:GONE);
        }

        @OnClick(R.id.holder)
        void click(View v) {
            adapter.onClick(serviceBean, getAdapterPosition());
        }

        @OnClick(R.id.subscribe)
        void clickSub(View v) {
            if (LoginInfoDBHelper.newInstance().isLogin()) {
                IntentUtil.startActivity(v, SubscribeActivity.getStartIntent(serviceBean, null));
            } else {
                IntentUtil.startLoginActivity(v);
            }
        }

        @Override
        public void destroy() {

        }
    }
}
