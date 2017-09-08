package com.dianjiake.android.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dianjiake.android.R;
import com.dianjiake.android.base.BaseListFragment;
import com.dianjiake.android.event.CheckMsgUnreadEvent;
import com.dianjiake.android.ui.common.ShopListAdapter;
import com.dianjiake.android.ui.msg.MsgActivity;
import com.dianjiake.android.ui.searchlocation.SearchLocationActivity;
import com.dianjiake.android.ui.searchshop.SearchShopActivity;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.util.UIUtil;
import com.dianjiake.android.view.dialog.NormalProgressDialog;
import com.dianjiake.android.view.widget.BaseLoadMoreAdapter;
import com.dianjiake.android.view.widget.HomeFilterView;
import com.dianjiake.android.view.widget.MsgIconView;
import com.dianjiake.android.view.widget.ToolbarSpaceView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by lfs on 2017/7/7.
 */

public class HomeFragment extends BaseListFragment<HomeContract.Presenter> implements HomeContract.View {

    int totalScrollY;
    int toolbarBottomDistance;
    int adViewTopDistance;
    int collectionBottomDistance;

    @BindView(R.id.toolbar_space)
    ToolbarSpaceView toolbarSpace;
    @BindView(R.id.toolbar_location_text)
    TextView toolbarLocationText;
    @BindView(R.id.toolbar_location_holder)
    LinearLayout toolbarLocationHolder;
    @BindView(R.id.toolbar_search_input)
    TextView toolbarSearchInput;
    @BindView(R.id.toolbar_search_holder)
    LinearLayout toolbarSearchHolder;
    @BindView(R.id.toolbar_msg_holder)
    FrameLayout toolbarMsgHolder;
    @BindView(R.id.toolbar)
    LinearLayout toolbarHolder;
    @BindView(R.id.filter)
    HomeFilterView filter;
    @BindView(R.id.msg_icon)
    MsgIconView msgIconView;

    NormalProgressDialog pd;

    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //算了一下recyclerview的总共便宜量
            totalScrollY += dy;
            judgeScrollY(totalScrollY);
        }
    };

    //提供了布局
    @Override
    protected int provideLayout() {
        return R.layout.fragment_home;
    }

    //设置了presenter
    @Override
    protected HomeContract.Presenter getPresenter() {
        return new HomePresenter(this);
    }

    //提供了一个适配器
    @Override
    protected BaseLoadMoreAdapter provideAdapter() {
        return new ShopListAdapter(presenter.getItems());
    }
    //初始化viewcreate方法
    @Override
    protected void viewCreated(View view, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        ptrListLayout.getRecyclerView().addOnScrollListener(scrollListener);
        ptrListLayout.getPtr().disableWhenHorizontalMove(true);
        toolbarLocationText.setMaxWidth(UIUtil.getScreenWidth() * 1 / 4);

        toolbarBottomDistance = UIUtil.getStatusBarHeight() + UIUtil.getDimensionPixelSize(R.dimen.toolbar_size);
        adViewTopDistance = UIUtil.getScreenWidth() / 2;
        collectionBottomDistance = UIUtil.getDimensionPixelSize(R.dimen.home_collection);
        toolbarHolder.getBackground().mutate().setAlpha(0);
        toolbarSpace.getBackground().mutate().setAlpha(0);
        presenter.addRG(filter.getFilterGroup());
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

    @Override
    public void onDestroyView() {
        ptrListLayout.getRecyclerView().removeOnScrollListener(scrollListener);
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    //设置定位的名称
    @Override
    public void setLocationName(String locationName) {
        toolbarLocationText.setText(locationName);
    }

    @Override
    public void judgeScrollY(int totalY) {
        int toolbarThreshold = adViewTopDistance - toolbarBottomDistance;
        int filterThreshold = toolbarThreshold + collectionBottomDistance;
        if (totalY >= toolbarThreshold) {
            toolbarLocationHolder.setVisibility(View.GONE);
            toolbarMsgHolder.setVisibility(View.GONE);
            toolbarHolder.getBackground().mutate().setAlpha(255);
            toolbarSpace.getBackground().mutate().setAlpha(255);
            toolbarSearchHolder.setBackgroundResource(R.drawable.bg_home_search);
        } else {
            Timber.d("alpha " + (255 * totalY * 1.0f / toolbarThreshold));
            toolbarHolder.getBackground().mutate().setAlpha((int) (255 * totalY * 1.0f / toolbarThreshold));
            toolbarSpace.getBackground().mutate().setAlpha((int) (255 * totalY * 1.0f / toolbarThreshold));
            toolbarLocationHolder.setVisibility(View.VISIBLE);
            toolbarMsgHolder.setVisibility(View.VISIBLE);
            toolbarSearchHolder.setBackgroundResource(R.drawable.bg_home_search_white);
        }

        filter.setVisibility(totalY >= filterThreshold ? View.VISIBLE : View.INVISIBLE);
    }

    //展示加载中进度
    @Override
    public void showPD() {
        if (pd == null) {
            pd = NormalProgressDialog.newInstance("正在加载，请稍候...");
        }
        pd.showDialog(getFragmentManager(), "pdd");
    }
    //隐藏
    @Override
    public void dismissPD() {
        if (pd != null) {
            pd.dismissAllowingStateLoss();
        }
    }

    //移动recycleview
    @Override
    public void moveRecyclerView() {
        ptrListLayout.getRecyclerView().stopScroll();
        totalScrollY = adViewTopDistance - toolbarBottomDistance + collectionBottomDistance;
        Log.e("totalScrollY"+totalScrollY, "moveRecyclerView: "+adViewTopDistance+"---"+toolbarBottomDistance+"---"+collectionBottomDistance);
        ((LinearLayoutManager) ptrListLayout.getRecyclerView().getLayoutManager())
                .scrollToPositionWithOffset(2, UIUtil.getStatusBarHeight() + UIUtil.getDimensionPixelSize(R.dimen.button_size_normal));
    //.scrollToPositionWithOffset(2, UIUtil.getStatusBarHeight() + UIUtil.getDimensionPixelSize(R.dimen.button_size_normal)
    }

    @OnClick(R.id.toolbar_location_holder)
    void clickLocation(View v) {
        startActivity(IntentUtil.getIntent(SearchLocationActivity.class));
    }

    @OnClick(R.id.toolbar_search_holder)
    void clickSearch(View v) {
        startActivity(IntentUtil.getIntent(SearchShopActivity.class));
    }

    @OnClick(R.id.toolbar_msg_holder)
    void clickMsg(View v) {
        startActivity(MsgActivity.getStartIntent());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void checkUnread(CheckMsgUnreadEvent event) {
        msgIconView.setShowRedIcon(presenter.haveUnreadMsg());
    }
}
