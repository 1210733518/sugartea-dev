package com.dianjiake.android.ui.coupon;

import android.view.View;
import android.view.ViewGroup;

import com.dianjiake.android.R;
import com.dianjiake.android.data.bean.MyCouponBean;
import com.dianjiake.android.ui.shopdetail.ShopDetailActivity;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.util.UIUtil;
import com.dianjiake.android.view.coupon.CouponView;
import com.dianjiake.android.view.widget.BaseLoadMoreAdapter;
import com.dianjiake.android.view.widget.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lfs on 2017/7/25.
 */

public class CouponAdapter extends BaseLoadMoreAdapter<MyCouponBean> {


    public CouponAdapter(List<MyCouponBean> items) {
        super(items);
    }

    @Override
    public void myOnBindViewHolder(BaseViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        vh.setItem(getItem(position));
    }

    @Override
    public BaseViewHolder myOnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(UIUtil.inflate(R.layout.item_my_coupon, parent));
    }

    @Override
    public int myGetItemViewType(int position) {
        return 0;
    }

    public static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.coupon)
        CouponView coupon;

        MyCouponBean item;

        public ViewHolder(View itemView) {
            super(itemView);
        }


        public void setItem(MyCouponBean bean) {
            this.item = bean;
            coupon.setCoupon(bean.getKaquan(), bean.getDianpu());
        }

        @OnClick(R.id.coupon)
        void click(View v) {
            if (item != null && item.getDianpu() == null) return;
            IntentUtil.startActivity(v, ShopDetailActivity.getStartIntent(item.getDianpu().getId()));
        }

        @Override
        public void destroy() {

        }
    }
}
