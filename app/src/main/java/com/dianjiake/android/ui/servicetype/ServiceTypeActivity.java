package com.dianjiake.android.ui.servicetype;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.base.BasePresenter;
import com.dianjiake.android.base.BaseTranslateActivity;
import com.dianjiake.android.common.FragmentFactory;
import com.dianjiake.android.data.bean.ServiceSecondBean;
import com.dianjiake.android.ui.searchshop.SearchShopActivity;
import com.dianjiake.android.ui.searchshop.ServiceResultFragment;
import com.dianjiake.android.ui.searchshop.ShopResultFragment;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.util.TabFragmentManager;
import com.dianjiake.android.view.widget.ToolbarSpaceView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lfs on 2017/7/25.
 */

public class ServiceTypeActivity extends BaseTranslateActivity {
    public static Intent getStartIntent(ServiceSecondBean bean, boolean isSingle) {
        Intent intent = IntentUtil.getIntent(ServiceTypeActivity.class);
        intent.putExtra("bean", bean);
        intent.putExtra("single", isSingle);
        return intent;
    }

    @BindView(R.id.toolbar_space)
    ToolbarSpaceView toolbarSpace;
    @BindView(R.id.toolbar_icon_left)
    ImageView toolbarIconLeft;
    @BindView(R.id.toolbar_radio_0)
    RadioButton toolbarRadio0;
    @BindView(R.id.toolbar_radio_1)
    RadioButton toolbarRadio1;
    @BindView(R.id.toolbar_rg)
    RadioGroup toolbarRg;
    @BindView(R.id.toolbar_icon_right)
    ImageView toolbarIconRight;
    @BindView(R.id.toolbar_divider)
    ImageView toolbarDivider;
    @BindView(R.id.toolbar_holder)
    ConstraintLayout toolbarHolder;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    ServiceSecondBean secondBean;
    TabFragmentManager tabManager;
    boolean isSingle;

    @Override
    public int provideContentView() {
        return R.layout.activity_service_type;
    }

    @Override
    public void create(@Nullable Bundle savedInstanceState) {
        toolbarIconRight.setImageResource(R.drawable.ic_toolbar_search);
        isSingle = getIntent().getBooleanExtra("single", false);
        secondBean = getIntent().getParcelableExtra("bean");
        toolbarRadio0.setText(secondBean.getMingcheng());
        toolbarRadio0.post(new Runnable() {
            @Override
            public void run() {
                if (toolbarRadio1 != null) {
                    toolbarRadio1.setWidth(toolbarRadio0.getWidth());
                }
            }
        });
        tabManager = new TabFragmentManager(getFragmentManager(), toolbarRg, false);
        Fragment fm2 = FragmentFactory.createFragmentByFM(ShopResultFragment.class, getFragmentManager(), ShopResultFragment.getBundle(secondBean.getId()));
        tabManager.putFragment(R.id.toolbar_radio_1, fm2);
        if (isSingle) {
            toolbarRg.setVisibility(View.GONE);
            toolbarTitle.setText(secondBean.getMingcheng());
            toolbarTitle.setVisibility(View.VISIBLE);
        } else {
            toolbarTitle.setVisibility(View.INVISIBLE);
            Fragment fm1 = FragmentFactory.createFragmentByFM(ServiceResultFragment.class, getFragmentManager(), ServiceResultFragment.getBundle(secondBean.getId()));
            tabManager.putFragment(R.id.toolbar_radio_0, fm1);

        }
        tabManager.start();
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @OnClick(R.id.toolbar_icon_left)
    void clickBack(View v) {
        finish();
    }


    @OnClick(R.id.toolbar_icon_right)
    void clickRight(View v) {
        startActivity(IntentUtil.getIntent(SearchShopActivity.class));
    }
}
