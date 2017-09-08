package com.dianjiake.android.ui.vip;

import com.dianjiake.android.base.BaseListPresenter;
import com.dianjiake.android.base.BaseListView;
import com.dianjiake.android.data.bean.VipBean;

import java.util.List;

/**
 * Created by lfs on 2017/7/25.
 */

public interface VipContract {
    interface View extends BaseListView {
    }

    interface Presenter extends BaseListPresenter {
        List<VipBean> getItems();
    }
}
