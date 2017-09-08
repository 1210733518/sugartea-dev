package com.dianjiake.android.ui.common;


import android.app.Activity;
import android.content.Intent;

import com.dianjiake.android.api.Network;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.data.bean.BaseListBean;
import com.dianjiake.android.data.bean.OrderBean;
import com.dianjiake.android.runnabl.CustomRunnable;
import com.dianjiake.android.ui.evaluate.EvaluateActivity;
import com.dianjiake.android.ui.main.MainActivity;
import com.dianjiake.android.ui.orderdetail.OrderDetailActivity;
import com.dianjiake.android.ui.subscribe.SubscribeActivity;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.util.ToastUtil;
import com.dianjiake.android.view.dialog.CancleOrderDialog;
import com.dianjiake.android.view.dialog.NormalAlertDialog;

import io.reactivex.Observable;

/**
 * Created by lfs on 2017/7/20.
 */

public class OrderPresenter extends BaseOrderPresenter {
    MainActivity activity;
    public OrderPresenter(OrderView view) {
        super(view);
    }
    public void attachActivity(MainActivity fa){
         activity=fa;
    }
    @Override
    public void clickHolder(OrderBean orderBean, int position) {
        Intent startIntent = OrderDetailActivity.getStartIntent(orderBean);
//       try{
//        OrderFragment view = (OrderFragment) this.view;
//
//        Intent intent = new Intent();
//        intent.putExtra("orderbean",orderBean);
//        view.ma.startActivity(intent); }
//       catch (ActivityNotFoundException e){
//           Log.e("guaju", "clickHolder:activity not found");
//       }
        IntentUtil.startActivity(view.getViewContext(),startIntent );

    }

    @Override
    public void clickCall(OrderBean orderBean, int position) {
        if (orderBean.getDianpu() != null) {
            IntentUtil.startCall(view.getViewContext(), orderBean.getDianpu().getDianhua());
        }
    }

    @Override
    public void clickReSub(OrderBean orderBean, int position) {
        if(orderBean!=null&&orderBean.getDianpu()!=null){
            IntentUtil.startActivity(view.getViewContext(), SubscribeActivity.getStartIntent(orderBean.getDianpu().getId()));
        }else{
            ToastUtil.showShortToast("暂未获得店铺信息");
        }
    }

    @Override
    public void clickCancel(final OrderBean orderBean, final int position) {
        NormalAlertDialog alertDialog = NormalAlertDialog.newInstance("确定要取消预约？", true, true);
//        alertDialog.setOnButtonClick(new NormalAlertDialog.OnButtonClick() {
//            @Override
//            public void click(int which) {
//                if (which == DialogInterface.BUTTON_POSITIVE) {
//                    cancelOrder(orderBean, position);
//                }
//            }
//        });
//        alertDialog.showDialog(view.provideFragmentManager(), "cancel");
        CancleOrderDialog.showCustomDialog((Activity) view.getViewContext(), new CustomRunnable() {
            @Override
            public void run() {
                String info = getInfo();
                //// TODO: 2017/8/4
            }
        });
    }

    @Override
    public void clickEvaluate(OrderBean orderBean, int position) {
        IntentUtil.startActivity(view.getViewContext(), EvaluateActivity.getStartIntent(orderBean));
    }



    @Override
    public Observable<BaseListBean<OrderBean>> provideApi() {
        return Network.getInstance().orders(BSConstant.ORDER_LIST, loginInfo.getOpenId(), page);
    }


}
