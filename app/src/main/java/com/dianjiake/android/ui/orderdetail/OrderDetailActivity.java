package com.dianjiake.android.ui.orderdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.base.BaseTranslateActivity;
import com.dianjiake.android.common.ActiivtyDataHelper;
import com.dianjiake.android.data.bean.DiscountBean;
import com.dianjiake.android.data.bean.OrderBean;
import com.dianjiake.android.data.bean.OrderGoodBean;
import com.dianjiake.android.data.bean.OrderServiceBean;
import com.dianjiake.android.runnabl.CustomRunnable;
import com.dianjiake.android.ui.common.OrderPayType;
import com.dianjiake.android.ui.common.OrderStatus;
import com.dianjiake.android.ui.evaluate.EvaluateActivity;
import com.dianjiake.android.ui.shopdetail.ShopDetailActivity;
import com.dianjiake.android.util.CheckEmptyUtil;
import com.dianjiake.android.util.DateUtil;
import com.dianjiake.android.util.FrescoUtil;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.util.TableRowUtil;
import com.dianjiake.android.util.UIUtil;
import com.dianjiake.android.view.coupon.GetCouponView;
import com.dianjiake.android.view.dialog.CancleOrderDialog;
import com.dianjiake.android.view.dialog.NormalProgressDialog;
import com.dianjiake.android.view.widget.ToolbarSpaceView;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by lfs on 2017/7/20.
 */

public class OrderDetailActivity extends BaseTranslateActivity<OrderDetailPresenter> implements OrderDetailContract.View {
    public final int REQUEST_CODE = 789;

    @BindView(R.id.toolbar_space)
    ToolbarSpaceView toolbarSpace;
    @BindView(R.id.toolbar_icon_left)
    ImageView toolbarIconLeft;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_icon_right)
    ImageView toolbarIconRight;
    @BindView(R.id.toolbar_divider)
    ImageView toolbarDivider;
    @BindView(R.id.toolbar_holder)
    ConstraintLayout toolbarHolder;
    @BindView(R.id.detail_logo)
    SimpleDraweeView detailLogo;
    @BindView(R.id.detail_status)
    TextView detailStatus;
    @BindView(R.id.detail_desc)
    TextView detailDesc;
    @BindView(R.id.detail_button1)
    Button detailButton1;
    @BindView(R.id.detail_button2)
    Button detailButton2;
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
    @BindView(R.id.table_layout2)
    TableLayout tableLayout2;

    @BindView(R.id.sub_title)
    TextView subTitle;
    @BindView(R.id.sub_divider)
    ImageView subDivider;
    @BindView(R.id.sub_table)
    TableLayout subTable;
    @BindView(R.id.get_coupon)
    GetCouponView couponView;
    @BindView(R.id.discount_table)
    TableLayout discountTable;

    int orderStatus;
    OrderBean orderBean;
    NormalProgressDialog cancelPD, getPD;

    public static Intent getStartIntent(OrderBean order) {
        Intent intent = IntentUtil.getIntent(OrderDetailActivity.class);
        intent.putExtra("order", order);
        return intent;
    }

    public static Intent getStartIntent(String shopId, String orderId) {
        Intent intent = IntentUtil.getIntent(OrderDetailActivity.class);
        intent.putExtra("shopid", shopId);
        intent.putExtra("orderid", orderId);
        return intent;
    }

    @Override
    public void setPresenter(OrderDetailContract.Presenter presenter) {

    }

    @Override
    public int provideContentView() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void create(@Nullable Bundle savedInstanceState) {
        orderBean = getIntent().getParcelableExtra("order");
        toolbarTitle.setText("订单详情");
        couponView.setFragmentManager(getFragmentManager());
        if (orderBean != null) {
            presenter.setOrderBean(orderBean);
            setView(orderBean);
        } else {
            presenter.getOrderDetail(getIntent().getStringExtra("shopid"), getIntent().getStringExtra("orderid"));
        }

    }

    @Override
    public OrderDetailPresenter getPresenter() {
        return new OrderDetailPresenter(this);
    }

    @OnClick(R.id.toolbar_icon_left)
    void clickBack(View v) {
        finish();
    }

    @Override
    public void setView(OrderBean item) {
        orderBean = item;
        if (item == null) return;
        if (item.getDianpu() != null) {
            couponView.setShop(item.getDianpu());
            logo.setImageURI(FrescoUtil.getShopLogoUri(item.getDianpu().getLogo(), item.getDianpu().getCover()));
            detailLogo.setImageURI(FrescoUtil.getShopLogoUri(item.getDianpu().getLogo(), item.getDianpu().getCover()));
            name.setText(item.getDianpu().getMingcheng());
        }
        orderStatus = OrderStatus.getStatus(item.getStatus());
        detailStatus.setText(OrderStatus.getStatusText(item.getStatus()));
        detailDesc.setText(OrderStatus.getStatusDescText(item.getStatus()));
        detailButton2.setVisibility("1".equals(item.getShifoupinglun()) ? View.GONE : View.VISIBLE);

        tableLayout.removeAllViews();
        tableLayout2.removeAllViews();
        discountTable.removeAllViews();

        if (!CheckEmptyUtil.isEmpty(item.getDingdanfuwu())) {
            for (int i = 0; i < item.getDingdanfuwu().size(); i++) {
                OrderServiceBean orderService = item.getDingdanfuwu().get(i);
                TableRow tableRow = new TableRow(this);
                tableRow.setPadding(0, 0, 0, UIUtil.getDimensionPixelSize(R.dimen.base_size3));
                if (i < 3) {
                    tableRow.addView(TableRowUtil.getServiceTitleText(orderService.getFuwumingcheng(), this));
                    tableRow.addView(TableRowUtil.getServiceEndText("￥" + orderService.getDanjia(), this));
                } else if (i == 3) {
                    tableRow.addView(TableRowUtil.getCountTitleText("...", this));
                } else {
                    break;
                }
                tableLayout.addView(tableRow);
            }
        }


        if (!CheckEmptyUtil.isEmpty(item.getDingdanshangpin())) {
            for (int i = 0; i < item.getDingdanshangpin().size(); i++) {
                OrderGoodBean orderGood = item.getDingdanshangpin().get(i);
                TableRow tableRow = new TableRow(this);
                tableRow.setPadding(0, 0, 0, UIUtil.getDimensionPixelSize(R.dimen.base_size3));
                if (tableLayout.getChildCount() == 3) {
                    tableRow.addView(TableRowUtil.getCountTitleText("...", this));

                } else if (tableLayout.getChildCount() < 3) {
                    tableRow.addView(TableRowUtil.getServiceTitleText(orderGood.getMingcheng(), this));
                    tableRow.addView(TableRowUtil.getServiceEndText("￥" + orderGood.getDanjia(), this));
                } else {
                    break;
                }
                tableLayout.addView(tableRow);
            }
        }

        //中间
        if (!CheckEmptyUtil.isEmpty(item.getDingdanfuwu())) {
            TableRow countRow = new TableRow(this);
            countRow.setPadding(0, 0, 0, UIUtil.getDimensionPixelSize(R.dimen.base_size3));
            countRow.addView(TableRowUtil.getCountTitleText(item.getDingdanfuwu().size() + "个项目", this));
            countRow.addView(TableRowUtil.getCountEndText("合计：￥" + item.getYingfujine(), this));
            discountTable.addView(countRow);
        }

        if (!CheckEmptyUtil.isEmpty(item.getYouhui())) {
            for (DiscountBean b : item.getYouhui()) {
                TableRow countRow = new TableRow(this);
                countRow.setGravity(Gravity.CENTER_VERTICAL);
                countRow.setPadding(0, 0, 0, UIUtil.getDimensionPixelSize(R.dimen.base_size3));
                countRow.addView(TableRowUtil.getDiscountIcon(b, this));
                countRow.addView(TableRowUtil.getDiscountTitle(b, this));
                countRow.addView(TableRowUtil.getDiscountEnd(b, this));
                discountTable.addView(countRow);
            }
        }


        TableRow finalPay = new TableRow(this);
        finalPay.setPadding(0, 0, 0, UIUtil.getDimensionPixelSize(R.dimen.base_size3));
        finalPay.addView(TableRowUtil.getVipTitleText(item.getDengjimingcheng(), this));
        finalPay.addView(TableRowUtil.getVipEndText("应付金额：￥" + item.getHuiyuanjia(), this));
        tableLayout2.addView(finalPay);

        if (orderStatus == OrderStatus.COMPLETE) {
            TableRow payTool = new TableRow(this);
            payTool.setPadding(0, 0, 0, UIUtil.getDimensionPixelSize(R.dimen.base_size3));
            payTool.addView(TableRowUtil.getVipTitleText("支付方式：" + OrderPayType.getPayType(item.getPays()), this));
            payTool.addView(TableRowUtil.getVipEndText("实付金额：￥" + item.getShifujine(), this));
            tableLayout2.addView(payTool);

            TableRow payTime = new TableRow(this);
            payTime.setPadding(0, 0, 0, UIUtil.getDimensionPixelSize(R.dimen.base_size3));
            payTime.addView(TableRowUtil.getVipTitleText("结账时间：" + DateUtil.formatYMDHM(item.getWanchengshijian()), this));
            tableLayout2.addView(payTime);
        }

        setButtonText();

        subTable.removeAllViews();
        TableRow serviceTime = new TableRow(this);
        serviceTime.setPadding(0, 0, 0, UIUtil.getDimensionPixelSize(R.dimen.base_size3));
        serviceTime.addView(TableRowUtil.getSubText("服务时间：", this));
        serviceTime.addView(TableRowUtil.getSubText(DateUtil.formatYMDHM(item.getFuwushijian()), this));
        subTable.addView(serviceTime);

        TableRow info = new TableRow(this);
        info.setPadding(0, 0, 0, UIUtil.getDimensionPixelSize(R.dimen.base_size3));
        info.addView(TableRowUtil.getSubText("客户信息：", this));
        info.addView(TableRowUtil.getSubText(item.getName() + " " + item.getTel(), this));
        subTable.addView(info);

        TableRow addTime = new TableRow(this);
        addTime.setPadding(0, 0, 0, UIUtil.getDimensionPixelSize(R.dimen.base_size3));
        addTime.addView(TableRowUtil.getSubText("下单时间：", this));
        addTime.addView(TableRowUtil.getSubText(DateUtil.formatYMDHM(item.getAddtime()), this));
        subTable.addView(addTime);
    }

    @Override
    public void showCancelPD() {
        if (cancelPD == null) {
            cancelPD = NormalProgressDialog.newInstance("正在取消预约，请稍候...");
        }
        cancelPD.showDialog(getFragmentManager(), "cancel");
    }

    @Override
    public void dismissCancelPD() {
        if (cancelPD != null) {
            cancelPD.dismissAllowingStateLoss();
        }
    }

    @Override
    public void showGetPD() {
        if (getPD == null) {
            getPD = NormalProgressDialog.newInstance("正在获取订单详情，请稍候...");
        }
        getPD.showDialog(getFragmentManager(), "get");
    }

    @Override
    public void dismissGetPD() {
        if (getPD != null && getPD.isAdded()) {
            getPD.dismissAllowingStateLoss();
        }
    }

    @Override
    public Context provideContext() {
        return this;
    }


    void setButtonText() {
        switch (orderStatus) {
            case OrderStatus.NO_CONFIRM:
            case OrderStatus.CONFIRM:
            case OrderStatus.RUNNING:
                detailButton1.setText("取消预约");
                detailButton2.setText("联系商家");
                break;
            case OrderStatus.COMPLETE:
                detailButton1.setText("再来一单");
                detailButton2.setText("评价服务");
                break;
            case OrderStatus.CANCEL:
                detailButton1.setText("再来一单");
                detailButton2.setText("联系商家");
                break;
        }
    }

    void cancelOrder() {
//        NormalAlertDialog alertDialog = NormalAlertDialog.newInstance("确定要取消预约？", true, true);
//        alertDialog.setOnButtonClick(new NormalAlertDialog.OnButtonClick() {
//            @Override
//            public void click(int which) {
//                if (which == DialogInterface.BUTTON_POSITIVE) {
//                    presenter.cancelOrder();
//                }
//            }
//        });
//        alertDialog.showDialog(getFragmentManager(), "cancel");
        CancleOrderDialog.showCustomDialog(this, new CustomRunnable() {
            @Override
            public void run() {
                String info = getInfo();
                //// TODO: 2017/8/4  
            }
        });
    }

    @OnClick(R.id.detail_logo)
    void clickLogo(View v) {
        if (orderBean == null || orderBean.getDianpu() == null) return;
        startActivity(ShopDetailActivity.getStartIntent(orderBean.getDianpu().getId()));
    }

    @OnClick(R.id.detail_button1)
    void clickButton1(View v) {
        if (orderBean == null) return;
        switch (orderStatus) {
            case OrderStatus.NO_CONFIRM:
            case OrderStatus.CONFIRM:
            case OrderStatus.RUNNING:
                cancelOrder();
                break;
            case OrderStatus.COMPLETE:
            case OrderStatus.CANCEL:
                presenter.reSub();
                break;
        }
    }

    @OnClick(R.id.detail_button2)
    void clickButton2(View v) {
        if (orderBean == null) return;
        switch (orderStatus) {
            case OrderStatus.NO_CONFIRM:
            case OrderStatus.CONFIRM:
            case OrderStatus.RUNNING:
            case OrderStatus.CANCEL:
                presenter.call();
                break;
            case OrderStatus.COMPLETE:
                startActivityForResult(EvaluateActivity.getStartIntent(orderBean), REQUEST_CODE);
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            setView(ActiivtyDataHelper.getOrderBean(data));
        }
    }
}
