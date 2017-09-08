package com.dianjiake.android.ui.common;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.data.bean.OrderBean;
import com.dianjiake.android.data.bean.OrderGoodBean;
import com.dianjiake.android.data.bean.OrderServiceBean;
import com.dianjiake.android.util.CheckEmptyUtil;
import com.dianjiake.android.util.FrescoUtil;
import com.dianjiake.android.util.TableRowUtil;
import com.dianjiake.android.util.UIUtil;
import com.dianjiake.android.view.widget.BaseLoadMoreAdapter;
import com.dianjiake.android.view.widget.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lfs on 2017/7/20.
 */

public class OrderListAdapter extends BaseLoadMoreAdapter<OrderBean> {
    BaseOrderPresenter presenter;

    public OrderListAdapter(List<OrderBean> items, BaseOrderPresenter presenter) {
        super(items);
        this.presenter = presenter;
    }

    @Override
    public void myOnBindViewHolder(BaseViewHolder holder, int position) {
        if (OrderViewType.NORMAL == myGetItemViewType(position)) {
            ViewHolder vh = (ViewHolder) holder;
            vh.setItem(getItem(position));
        }
    }

    @Override
    public BaseViewHolder myOnCreateViewHolder(ViewGroup parent, int viewType) {
        if (OrderViewType.HEADER == viewType) {
            return HeaderVH.newInstance(parent);
        } else {
            return ViewHolder.newInstance(parent, presenter);
        }
    }

    @Override
    public int myGetItemViewType(int position) {
        return getItem(position).getViewType();
    }

    public static class ViewHolder extends BaseViewHolder {

        OrderBean item;
        BaseOrderPresenter presenter;

        @BindView(R.id.logo)
        SimpleDraweeView logo;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.logo_divider)
        ImageView logoDivider;
        @BindView(R.id.table_layout)
        TableLayout tableLayout;
        @BindView(R.id.table_divider)
        ImageView tableDivider;
        @BindView(R.id.button1)
        Button button1;
        @BindView(R.id.button2)
        Button button2;

        int orderStatus;

        public static ViewHolder newInstance(ViewGroup parent, BaseOrderPresenter presenter) {
            return new ViewHolder(UIUtil.inflate(R.layout.item_order, parent), presenter);
        }

        private ViewHolder(View itemView, BaseOrderPresenter presenter) {
            super(itemView);
            this.presenter = presenter;
        }

        public void setItem(OrderBean item) {
            this.item = item;
            if (item.getDianpu() != null) {
                logo.setImageURI(FrescoUtil.getShopLogoUri(item.getDianpu().getLogo(), item.getDianpu().getCover()));
                name.setText(item.getDianpu().getMingcheng());
            }
            orderStatus = OrderStatus.getStatus(item.getStatus());
            status.setText(OrderStatus.getStatusText(item.getStatus()));

            tableLayout.removeAllViews();

            button2.setVisibility("1".equals(item.getShifoupinglun()) ? View.GONE : View.VISIBLE);

            if (!CheckEmptyUtil.isEmpty(item.getDingdanfuwu())) {
                for (int i = 0; i < item.getDingdanfuwu().size(); i++) {
                    OrderServiceBean orderService = item.getDingdanfuwu().get(i);
                    TableRow tableRow = new TableRow(itemView.getContext());
                    tableRow.setPadding(0, 0, 0, UIUtil.getDimensionPixelSize(R.dimen.base_size3));
                    if (i < 3) {
                        tableRow.addView(TableRowUtil.getServiceTitleText(orderService.getFuwumingcheng(), itemView.getContext()));
                        if (orderService.getName()==null){
                        tableRow.addView(TableRowUtil.getServiceCenterText("(暂无服务人员)", itemView.getContext()));

                        }else{
                        tableRow.addView(TableRowUtil.getServiceCenterText("("+orderService.getName()+")", itemView.getContext()));
                        }

                        tableRow.addView(TableRowUtil.getServiceEndText("￥" + orderService.getDanjia(), itemView.getContext()));
                    } else if (i == 3) {
                        tableRow.addView(TableRowUtil.getCountTitleText("...", itemView.getContext()));
                    } else {
                        break;
                    }
                    tableLayout.addView(tableRow);
                }
            }


            if (!CheckEmptyUtil.isEmpty(item.getDingdanshangpin())) {
                for (int i = 0; i < item.getDingdanshangpin().size(); i++) {
                    OrderGoodBean orderGood = item.getDingdanshangpin().get(i);
                    TableRow tableRow = new TableRow(itemView.getContext());
                    tableRow.setPadding(0, 0, 0, UIUtil.getDimensionPixelSize(R.dimen.base_size3));
                    if (tableLayout.getChildCount() == 3) {
                        tableRow.addView(TableRowUtil.getCountTitleText("...", itemView.getContext()));

                    } else if (tableLayout.getChildCount() < 3) {
                        tableRow.addView(TableRowUtil.getServiceTitleText(orderGood.getMingcheng(), itemView.getContext()));
//                        tableRow.addView(TableRowUtil.getServiceCenterText(orderGood.getName(), itemView.getContext()));

                        tableRow.addView(TableRowUtil.getServiceEndText("￥" + orderGood.getDanjia(), itemView.getContext()));
                    } else {
                        break;
                    }
                    tableLayout.addView(tableRow);
                }
            }

            if (!CheckEmptyUtil.isEmpty(item.getDingdanfuwu())) {
                TableRow countRow = new TableRow(itemView.getContext());
                countRow.setPadding(0, 0, 0, UIUtil.getDimensionPixelSize(R.dimen.base_size3));
                countRow.addView(TableRowUtil.getCountTitleText(item.getDingdanfuwu().size() + "个项目", itemView.getContext()));
                countRow.addView(TableRowUtil.getCountEndText("合计：￥" + item.getYingfujine(), itemView.getContext()));
                tableLayout.addView(countRow);
            }


            TableRow finalPay = new TableRow(itemView.getContext());
            finalPay.setPadding(0, 0, 0, UIUtil.getDimensionPixelSize(R.dimen.base_size3));
            finalPay.addView(TableRowUtil.getVipTitleText(item.getDengjimingcheng(), itemView.getContext()));
            finalPay.addView(TableRowUtil.getVipEndText("应付金额：￥" + item.getShifujine(), itemView.getContext()));
            tableLayout.addView(finalPay);

            setButtonText();
        }


        void setButtonText() {
            switch (orderStatus) {
                case OrderStatus.NO_CONFIRM:
                case OrderStatus.CONFIRM:
                case OrderStatus.RUNNING:
                    button1.setText("取消预约");
                    button2.setText("联系商家");
                    break;
                case OrderStatus.COMPLETE:
                    button1.setText("再来一单");
                    button2.setText("评价服务");
                    break;
                case OrderStatus.CANCEL:
                    button1.setText("再来一单");
                    button2.setText("联系商家");
                    break;
            }
        }

        @OnClick(R.id.button1)
        void clickButton1(View v) {
            switch (orderStatus) {
                case OrderStatus.NO_CONFIRM:
                case OrderStatus.CONFIRM:
                case OrderStatus.RUNNING:
                    presenter.clickCancel(item, getAdapterPosition());
                    break;
                case OrderStatus.COMPLETE:
                case OrderStatus.CANCEL:
                    presenter.clickReSub(item, getAdapterPosition());
                    break;
            }
        }

        @OnClick(R.id.button2)
        void clickButton2(View v) {
            switch (orderStatus) {
                case OrderStatus.NO_CONFIRM:
                case OrderStatus.CONFIRM:
                case OrderStatus.RUNNING:
                case OrderStatus.CANCEL:
                    presenter.clickCall(item, getAdapterPosition());
                    break;
                case OrderStatus.COMPLETE:
                    presenter.clickEvaluate(item, getAdapterPosition());
                    break;
            }
        }

        @OnClick(R.id.holder)
        void clickHolder(View v) {
            presenter.clickHolder(item, getAdapterPosition());
            Log.e("guaju", "clickHolder: "+item.getDingdanfuwu().get(0).getName() );
        }

        @Override
        public void destroy() {

        }

    }

    public static class HeaderVH extends BaseViewHolder {
        public static HeaderVH newInstance(ViewGroup parent) {
            return new HeaderVH(UIUtil.inflate(R.layout.item_order_header, parent));
        }

        private HeaderVH(View itemView) {
            super(itemView);
        }

        @Override
        public void destroy() {

        }
    }
}
