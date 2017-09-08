package com.dianjiake.android.ui.subscribe;

import android.app.Activity;

import com.dianjiake.android.common.ActiivtyDataHelper;
import com.dianjiake.android.data.bean.UserInfoBean;
import com.dianjiake.android.ui.common.ShopStaffAdapter;
import com.dianjiake.android.view.widget.BaseLoadMoreAdapter;

/**
 * Created by lfs on 2017/7/18.
 */

public class ServiceStaffFragment extends BaseServiceStaffFragment implements BaseLoadMoreAdapter.OnItemClickListener {
    @Override
    protected BaseServiceStaffPresenter<UserInfoBean, ServiceStaffFragment> getPresenter() {
        return new ServiceStaffPresenter(this, shopId, serviceId);
    }

    @Override
    protected BaseLoadMoreAdapter provideAdapter() {
        BaseLoadMoreAdapter adapter = new ShopStaffAdapter(presenter.getItems());
        adapter.setOnItemClickListener(this);
        return adapter;
    }

    @Override
    public void onClick(Object t, int position) {
        activity.setResult(Activity.RESULT_OK, ActiivtyDataHelper.getServiceStaffData((UserInfoBean) t));
        activity.finish();
    }
}
