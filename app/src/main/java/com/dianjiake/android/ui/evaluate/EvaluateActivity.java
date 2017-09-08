package com.dianjiake.android.ui.evaluate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.base.BaseListActivity;
import com.dianjiake.android.common.ActiivtyDataHelper;
import com.dianjiake.android.common.AndroidBug5497Workaround;
import com.dianjiake.android.data.bean.OrderBean;
import com.dianjiake.android.util.EventUtil;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.util.ToastUtil;
import com.dianjiake.android.view.dialog.NormalProgressDialog;
import com.dianjiake.android.view.widget.BaseLoadMoreAdapter;
import com.dianjiake.android.view.widget.PtrListLayout;
import com.dianjiake.android.view.widget.ToolbarSpaceView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lfs on 2017/7/20.
 */

public class EvaluateActivity extends BaseListActivity<EvaluateContract.Presenter> implements EvaluateContract.View {
    public static Intent getStartIntent(OrderBean order) {
        Intent intent = IntentUtil.getIntent(EvaluateActivity.class);
        intent.putExtra("order", order);
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
    @BindView(R.id.ptr_list)
    PtrListLayout ptrList;

    NormalProgressDialog pd;

    @Override
    public int provideContentView() {
        return R.layout.activity_no_comment;
    }

    @Override
    public void create(@Nullable Bundle savedInstanceState) {
        AndroidBug5497Workaround.assistActivity(this);
        ptrListLayout.setNeedPtr(false);
        ptrListLayout.setNeedLoadMore(false);
        presenter.setServices((OrderBean) getIntent().getParcelableExtra("order"));
        toolbarTitle.setText("评价服务");
    }

    @Override
    public EvaluateContract.Presenter getPresenter() {
        return new EvaluatePresenter(this);
    }

    @Override
    protected BaseLoadMoreAdapter provideAdapter() {
        return new EvaluateAdapter(presenter.getItems(), presenter);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onReload() {

    }

    @Override
    public void showPD() {
        if (pd == null) {
            pd = NormalProgressDialog.newInstance("正在提交，请稍候...");
        }
        pd.showDialog(getFragmentManager(), "tag");
    }

    @Override
    public void dismissPD() {
        if (pd != null && pd.isAdded()) {
            pd.dismissAllowingStateLoss();
        }
    }

    @Override
    public void evaluateSuccess() {
        ToastUtil.showShortToast("评价成功");
        EventUtil.postRefreshOrderList();
        setResult(RESULT_OK, ActiivtyDataHelper.getOrderData(presenter.getOrderBean()));
        finish();
    }

    @Override
    public void evaluateFail() {
        ToastUtil.showShortToast("评价失败");

    }

    @OnClick(R.id.toolbar_icon_left)
    void clickBack(View v) {
        finish();
    }
}
