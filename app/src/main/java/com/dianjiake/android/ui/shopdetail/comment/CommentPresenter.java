package com.dianjiake.android.ui.shopdetail.comment;

import com.dianjiake.android.api.Network;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.data.bean.BaseListBean;
import com.dianjiake.android.data.bean.ShopCommentBean;
import com.dianjiake.android.ui.shopdetail.BaseShopContentPresenter;

import io.reactivex.Observable;

/**
 * Created by lfs on 2017/7/17.
 */

public class CommentPresenter extends BaseShopContentPresenter<ShopCommentBean, CommentContract.View> {


    public CommentPresenter(CommentContract.View view, String shopId) {
        super(view, shopId);
    }

    @Override
    protected Observable<BaseListBean<ShopCommentBean>> provideObservable() {
        return Network.getInstance().shopComment(
                BSConstant.SHOP_COMMENT,
                shopId,
                page);
    }
}
