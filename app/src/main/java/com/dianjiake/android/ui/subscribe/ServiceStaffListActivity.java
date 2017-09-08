package com.dianjiake.android.ui.subscribe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.base.BasePresenter;
import com.dianjiake.android.base.BaseTranslateActivity;
import com.dianjiake.android.base.BaseView;
import com.dianjiake.android.common.FragmentFactory;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.view.widget.ToolbarSpaceView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lfs on 2017/7/18.
 */

public class ServiceStaffListActivity extends BaseTranslateActivity implements BaseView {
    private static final String TYPE_SERVICE = "service";
    private static final String TYPE_STAFF = "staff";
    private static final String KEY_SHOP_ID = "shop_id";
    private static final String KEY_SERVICE_ID = "service_id";
    private static final String KEY_OPEN_ID = "open_id";
    @BindView(R.id.toolbar_space)
    ToolbarSpaceView toolbarSpace;
    @BindView(R.id.toolbar_icon_left)
    ImageView toolbarIconLeft;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_icon_right)
    ImageView toolbarIconRight;
    @BindView(R.id.toolbar_holder)
    ConstraintLayout toolbarHolder;
    @BindView(R.id.content)
    FrameLayout content;


    public static Intent getServiceIntent(String shopId, String openId) {
        return getStartIntent(shopId, null, openId, TYPE_SERVICE);
    }

    public static Intent getStaffIntent(String shopId, String serviceId) {
        return getStartIntent(shopId, serviceId, null, TYPE_STAFF);
    }

    private static Intent getStartIntent(String shopId, String serviceId, String openId, String type) {
        Intent intent = IntentUtil.getIntent(ServiceStaffListActivity.class);
        intent.setType(type);
        intent.putExtra(KEY_SHOP_ID, shopId);
        intent.putExtra(KEY_SERVICE_ID, serviceId);
        intent.putExtra(KEY_OPEN_ID, openId);
        return intent;
    }

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public int provideContentView() {
        return R.layout.activity_service_staff;
    }

    @Override
    public void create(@Nullable Bundle savedInstanceState) {
        String shopid = getIntent().getStringExtra(KEY_SHOP_ID);
        String openid = getIntent().getStringExtra(KEY_OPEN_ID);
        String serviceid = getIntent().getStringExtra(KEY_SERVICE_ID);

        Bundle b = BaseServiceStaffFragment.getBundle(shopid, serviceid, openid);
        if (TYPE_SERVICE.equals(getIntent().getType())) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.content, FragmentFactory.createFragment(StaffServiceFragment.class, b)).commit();
            toolbarTitle.setText("服务列表");

        } else if (TYPE_STAFF.equals(getIntent().getType())) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.content, FragmentFactory.createFragment(ServiceStaffFragment.class, b)).commit();
            toolbarTitle.setText("员工列表");
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.toolbar_icon_left)
    void clickBack(View v) {
        finish();
    }
}
