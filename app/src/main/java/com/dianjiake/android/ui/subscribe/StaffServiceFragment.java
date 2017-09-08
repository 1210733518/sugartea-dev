package com.dianjiake.android.ui.subscribe;

import android.app.Activity;

import com.dianjiake.android.common.ActiivtyDataHelper;
import com.dianjiake.android.data.bean.ServiceBean;
import com.dianjiake.android.data.bean.UserInfoBean;
import com.dianjiake.android.ui.common.ServiceListAdapter;
import com.dianjiake.android.ui.common.ShopStaffAdapter;
import com.dianjiake.android.view.widget.BaseLoadMoreAdapter;

/**
 * Created by lfs on 2017/7/18.
 */

public class StaffServiceFragment extends BaseServiceStaffFragment implements BaseLoadMoreAdapter.OnItemClickListener {
    @Override
    protected BaseServiceStaffPresenter getPresenter() {
        return new StaffServicePresenter(this, shopId, openId);
    }

    @Override
    protected BaseLoadMoreAdapter provideAdapter() {
        BaseLoadMoreAdapter adapter = new ServiceListAdapter(presenter.getItems(), false, false);
        adapter.setOnItemClickListener(this);
        return adapter;
    }

    @Override
    public void onClick(Object t, int position) {
        activity.setResult(Activity.RESULT_OK, ActiivtyDataHelper.getStaffServiceData((ServiceBean) t));
        activity.finish();
    }
}
