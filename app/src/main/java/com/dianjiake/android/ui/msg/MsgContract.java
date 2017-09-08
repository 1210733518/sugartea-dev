package com.dianjiake.android.ui.msg;

import com.dianjiake.android.base.BaseListPresenter;
import com.dianjiake.android.base.BaseListView;
import com.dianjiake.android.data.model.MsgModel;

import java.util.List;

/**
 * Created by lfs on 2017/7/26.
 */

public interface MsgContract {
    interface View extends BaseListView {
        void open(MsgModel msgModel);
    }

    interface Presenter extends BaseListPresenter {
        List<MsgModel> getItems();

        void clickItem(MsgModel msgModel, int position);
    }
}
