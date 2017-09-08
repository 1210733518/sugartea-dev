package com.dianjiake.android.ui.nocomment;

import com.dianjiake.android.api.Network;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.data.bean.BaseListBean;
import com.dianjiake.android.data.bean.OrderBean;
import com.dianjiake.android.ui.common.BaseOrderPresenter;
import com.dianjiake.android.ui.common.OrderView;
import com.dianjiake.android.ui.evaluate.EvaluateActivity;
import com.dianjiake.android.ui.evaluate.EvaluateContract;
import com.dianjiake.android.util.IntentUtil;

import io.reactivex.Observable;

/**
 * Created by lfs on 2017/7/20.
 */

public class NoCommentOrderPresenter extends BaseOrderPresenter {

    public NoCommentOrderPresenter(OrderView view) {
        super(view);
    }

    @Override
    public void clickHolder(OrderBean orderBean, int position) {
        clickEvaluate(orderBean, position);
    }

    @Override
    public void clickCall(OrderBean orderBean, int position) {

    }

    @Override
    public void clickReSub(OrderBean orderBean, int position) {

    }

    @Override
    public void clickCancel(OrderBean orderBean, int position) {

    }

    @Override
    public void clickEvaluate(OrderBean orderBean, int position) {
        IntentUtil.startActivity(view.getViewContext(), EvaluateActivity.getStartIntent(orderBean));
    }

    @Override
    public Observable<BaseListBean<OrderBean>> provideApi() {
        return Network.getInstance().orders(BSConstant.NO_COMMENT_ORDER_LIST, loginInfo.getOpenId(), page);
    }
}
