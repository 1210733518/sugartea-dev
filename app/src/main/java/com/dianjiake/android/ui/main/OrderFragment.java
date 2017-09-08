package com.dianjiake.android.ui.main;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.base.BaseListFragment;
import com.dianjiake.android.event.CheckMsgUnreadEvent;
import com.dianjiake.android.ui.common.OrderPresenter;
import com.dianjiake.android.ui.common.OrderView;
import com.dianjiake.android.ui.msg.MsgActivity;
import com.dianjiake.android.ui.nocomment.NoCommentOrderActivity;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.view.dialog.NormalProgressDialog;
import com.dianjiake.android.view.widget.BaseLoadMoreAdapter;
import com.dianjiake.android.view.widget.MsgIconView;
import com.dianjiake.android.view.widget.PtrListLayout;
import com.dianjiake.android.view.widget.ToolbarSpaceView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lfs on 2017/7/19.
 */

public class OrderFragment extends BaseListFragment<OrderPresenter> implements OrderView {
    boolean showNoComment = true;


    @BindView(R.id.toolbar_space)
    ToolbarSpaceView toolbarSpace;
    @BindView(R.id.toolbar_icon_left)
    ImageView toolbarIconLeft;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_icon_right)
    MsgIconView msgIconView;
    @BindView(R.id.toolbar_divider)
    ImageView toolbarDivider;
    @BindView(R.id.toolbar_holder)
    ConstraintLayout toolbarHolder;
    @BindView(R.id.ptr_list)
    public  PtrListLayout ptrList;
    @BindView(R.id.no_comment_text)
    TextView noCommentText;
    @BindView(R.id.no_comment_holder)
    View noCommentHolder;
    int i = 0;

    NormalProgressDialog cancelPD;
    public  MainActivity  ma;
    @Override
    protected int provideLayout() {
        return R.layout.fragment_order;
    }

    @Override
    protected OrderPresenter getPresenter() {

        return new OrderPresenter(this);
    }

    public  void attachMainActivity(MainActivity activity){
         ma=activity;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //不让走正规方式
        setHook(1);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void viewCreated(View view, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        toolbarIconLeft.setVisibility(View.INVISIBLE);
        toolbarTitle.setText("订单");
        checkUnread(null);
    }

    @Override
    public void onLoadMore() {
        presenter.load(false);
    }

    @Override
    public void onReload() {
        presenter.reload();
    }

    @OnClick(R.id.no_comment_close)
    void clickClose(View v) {
        setShowNoCommentHolder(false);
        presenter.removeHeaderItem();
    }

    @OnClick(R.id.no_comment_holder)
    void clickComment(View v) {
        startActivity(IntentUtil.getIntent(NoCommentOrderActivity.class));
    }


    @OnClick(R.id.toolbar_msg_holder)
    void clickMsg(View v) {
        startActivity(MsgActivity.getStartIntent());
    }


    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    protected BaseLoadMoreAdapter provideAdapter() {

        return null;
    }

    @Override
    protected void provideAdapterHook() {
        presenter.view=this;
        super.provideAdapterHook();
    }

    @Override
    public void setShowNoCommentHolder(boolean show) {
        showNoComment = show;
        noCommentHolder.setVisibility(showNoComment ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public boolean getShowNoCommentHolder() {
        return showNoComment;
    }

    @Override
    public void setNoCommentCount(int count) {
        noCommentHolder.setVisibility(showNoComment ? View.VISIBLE : View.INVISIBLE);
        noCommentText.setText(count + "个订单未评价，点击评价");
    }

    @Override
    public Context getViewContext() {
        return activity;
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
        if (cancelPD != null && cancelPD.isAdded()) {
            cancelPD.dismissAllowingStateLoss();
        }
    }

    @Override
    public FragmentManager provideFragmentManager() {
        return getFragmentManager();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void checkUnread(CheckMsgUnreadEvent event) {
        msgIconView.setShowRedIcon(presenter.haveUnreadMsg());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden){
        ptrList.scrollTo(0,0);
            if (baseLoadMoreAdapter!=null){
        baseLoadMoreAdapter.notifyDataSetChanged();

            }
            Log.e("guaju", "onHiddenChanged: "+"hehehehehehehe" );
        }
    }
}

