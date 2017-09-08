package com.dianjiake.android.ui.msg;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.base.BaseListActivity;
import com.dianjiake.android.data.model.MsgModel;
import com.dianjiake.android.ui.coupon.CouponActivity;
import com.dianjiake.android.ui.orderdetail.OrderDetailActivity;
import com.dianjiake.android.ui.vip.VipActivity;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.view.widget.BaseLoadMoreAdapter;
import com.dianjiake.android.view.widget.ToolbarSpaceView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lfs on 2017/7/26.
 */

public class MsgActivity extends BaseListActivity<MsgContract.Presenter> implements MsgContract.View {
    public static Intent getStartIntent() {
        Intent intent = IntentUtil.getIntent(MsgActivity.class);
        return intent;
    }

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


    @Override
    public int provideContentView() {
        return R.layout.activity_list;
    }

    @Override
    public void create(@Nullable Bundle savedInstanceState) {
        toolbarTitle.setText("消息中心");
    }


    @Override
    public MsgContract.Presenter getPresenter() {
        return new MsgPresenter(this);
    }

    @Override
    protected BaseLoadMoreAdapter provideAdapter() {
        return new MsgAdapter(presenter.getItems(), presenter);
    }

    @Override
    public void onLoadMore() {
        presenter.load(false);
    }

    @Override
    public void onReload() {
        presenter.reload();
    }

    @OnClick(R.id.toolbar_icon_left)
    void clickBack(View v) {
        finish();
    }

    @Override
    public void open(MsgModel msgModel) {
        switch (msgModel.getLeixing()) {
            case MsgType.COUPON:
                startActivity(IntentUtil.getIntent(CouponActivity.class));
                break;
            case MsgType.MONEY:
            case MsgType.NEW_CARD:
                startActivity(IntentUtil.getIntent(VipActivity.class));
                break;
            case MsgType.ORDER_CANCEL:
            case MsgType.ORDER_COMPLETE:
            case MsgType.ORDER_CONFIRM:
                startActivity(OrderDetailActivity.getStartIntent(msgModel.getShanghuid(), msgModel.getXinxiid()));
                break;
        }
    }
}
