package com.dianjiake.android.ui.shopdetail.comment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dianjiake.android.ui.common.ShopCommentAdapter;
import com.dianjiake.android.ui.shopdetail.BaseShopContentFragment;
import com.dianjiake.android.view.widget.BaseLoadMoreAdapter;

/**
 * Created by lfs on 2017/7/17.
 */

public class CommentFragment extends BaseShopContentFragment implements CommentContract.View {

    @Override
    protected CommentPresenter getPresenter() {
        return new CommentPresenter(this, shopId);
    }

    @Override
    protected BaseLoadMoreAdapter provideAdapter() {
        return new ShopCommentAdapter(presenter.getItems());
    }

    @Override
    protected void viewCreated(View view, @Nullable Bundle savedInstanceState) {

    }


}
