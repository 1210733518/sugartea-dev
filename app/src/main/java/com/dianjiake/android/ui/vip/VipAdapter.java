package com.dianjiake.android.ui.vip;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.data.bean.VipBean;
import com.dianjiake.android.ui.shopdetail.ShopDetailActivity;
import com.dianjiake.android.util.CheckEmptyUtil;
import com.dianjiake.android.util.DateUtil;
import com.dianjiake.android.util.FloatUtil;
import com.dianjiake.android.util.FrescoUtil;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.util.TableRowUtil;
import com.dianjiake.android.util.UIUtil;
import com.dianjiake.android.view.widget.BaseLoadMoreAdapter;
import com.dianjiake.android.view.widget.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lfs on 2017/7/25.
 */

public class VipAdapter extends BaseLoadMoreAdapter<VipBean> {


    public VipAdapter(List<VipBean> items) {
        super(items);
    }

    @Override
    public void myOnBindViewHolder(BaseViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        vh.setItem(getItem(position));
    }

    @Override
    public BaseViewHolder myOnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(UIUtil.inflate(R.layout.item_vip, parent));
    }

    @Override
    public int myGetItemViewType(int position) {
        return 0;
    }

    public static class ViewHolder extends BaseViewHolder {
        public final int[] bgs = new int[]{R.drawable.bg_vip_blue, R.drawable.bg_vip_green, R.drawable.bg_vip_orange, R.drawable.bg_vip_purple, R.drawable.bg_vip_red};

        @BindView(R.id.logo)
        SimpleDraweeView logo;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.desc)
        TextView desc;
        @BindView(R.id.table)
        TableLayout table;
        VipBean item;
        @BindView(R.id.holder)
        View holder;

        public ViewHolder(View itemView) {
            super(itemView);

        }

        public void setItem(VipBean vipBean) {
            this.item = vipBean;
            holder.setBackgroundResource(bgs[getAdapterPosition() % bgs.length]);
            logo.setImageURI(FrescoUtil.getShopLogoUri(vipBean.getLogo(), vipBean.getCover()));
            name.setText(vipBean.getShanghumingcheng());
            StringBuilder descSB = new StringBuilder(vipBean.getDengjimingcheng());
            if (FloatUtil.parseFloat(vipBean.getFuwuzhekou()) < 10.0) {
                descSB.append(" 服务折扣").append(vipBean.getFuwuzhekou()).append("折");
            }
            if (FloatUtil.parseFloat(vipBean.getShangpinzhekou()) < 10.0) {
                descSB.append(" 商品折扣").append(vipBean.getShangpinzhekou()).append("折");
            }
            if (!"0".equals(vipBean.getJieshushijian())) {
                descSB.append(" ").append(DateUtil.formatYMD(vipBean.getJieshushijian())).append("到期");
            }
            desc.setText(descSB.toString());
            table.removeAllViews();
            if (FloatUtil.parseFloat(vipBean.getMoney()) > 0) {
                TableRow moneyRow = new TableRow(itemView.getContext());
                moneyRow.setPadding(0, 0, 0, UIUtil.getDimensionPixelSize(R.dimen.base_size2));
                moneyRow.addView(TableRowUtil.getVipListTitle("储值卡", itemView.getContext()));
                moneyRow.addView(TableRowUtil.getVipListEnd(vipBean.getMoney(), itemView.getContext()));
                table.addView(moneyRow);
            }
            if (!CheckEmptyUtil.isEmpty(vipBean.getJicikalist())) {
                for (VipBean.JicikalistBean card : vipBean.getJicikalist()) {
                    TableRow cardRow = new TableRow(itemView.getContext());
                    cardRow.setPadding(0, 0, 0, UIUtil.getDimensionPixelSize(R.dimen.base_size2));
                    cardRow.addView(TableRowUtil.getVipListTitle("计次卡 " + card.getJicikamingcheng(), itemView.getContext()));
                    cardRow.addView(TableRowUtil.getVipListEnd(card.getShengyucishu() + "次", itemView.getContext()));
                    table.addView(cardRow);
                }
            }

        }

        @OnClick(R.id.holder)
        void click(View v) {
            IntentUtil.startActivity(v, ShopDetailActivity.getStartIntent(item.getShanghuid()));
        }

        @Override
        public void destroy() {

        }
    }

}
