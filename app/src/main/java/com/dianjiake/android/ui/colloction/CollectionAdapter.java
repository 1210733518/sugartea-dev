package com.dianjiake.android.ui.colloction;

import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.data.bean.HomeShopBean;
import com.dianjiake.android.data.bean.ShopDetailBean;
import com.dianjiake.android.ui.shopdetail.ShopDetailActivity;
import com.dianjiake.android.ui.shopdetail.comment.CommentContract;
import com.dianjiake.android.util.AMapUtil;
import com.dianjiake.android.util.FloatUtil;
import com.dianjiake.android.util.FrescoUtil;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.util.LongUtil;
import com.dianjiake.android.util.StringUtil;
import com.dianjiake.android.util.UIUtil;
import com.dianjiake.android.view.dialog.NormalAlertDialog;
import com.dianjiake.android.view.widget.BaseLoadMoreAdapter;
import com.dianjiake.android.view.widget.BaseViewHolder;
import com.dianjiake.android.view.widget.StarView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.security.DigestInputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by lfs on 2017/7/25.
 */

public class CollectionAdapter extends BaseLoadMoreAdapter<HomeShopBean> {

    CollectionContract.Presenter p;

    public CollectionAdapter(List<HomeShopBean> items, CollectionContract.Presenter p) {
        super(items);
        this.p = p;
    }

    @Override
    public void myOnBindViewHolder(BaseViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        vh.setItem(getItem(position));
    }

    @Override
    public BaseViewHolder myOnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(UIUtil.inflate(R.layout.item_collection, parent), p);
    }

    @Override
    public int myGetItemViewType(int position) {
        return 0;
    }

    public static class ViewHolder extends BaseViewHolder implements NormalAlertDialog.OnButtonClick {
        @BindView(R.id.logo)
        SimpleDraweeView logo;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.distance)
        TextView distance;
        @BindView(R.id.desc)
        TextView desc;
        @BindView(R.id.star)
        StarView star;
        CollectionContract.Presenter p;
        HomeShopBean item;

        NormalAlertDialog dialog;

        public ViewHolder(View itemView, CollectionContract.Presenter p) {
            super(itemView);
            this.p = p;
            dialog = NormalAlertDialog.newInstance("删除后，该店铺将取消收藏", true, true);
            dialog.setOnButtonClick(this);
        }

        public void setItem(HomeShopBean shopBean) {
            this.item = shopBean;
            logo.setImageURI(FrescoUtil.getShopLogoUri(shopBean.getLogo(), shopBean.getCover()));
            name.setText(shopBean.getMingcheng());
            desc.setText(shopBean.getJianjie());
            star.setScore(FloatUtil.parseFloat(shopBean.getPingfen()));
            distance.setText(AMapUtil.formatDistance(LongUtil.parseLong(shopBean.getJuli())));
        }

        @OnClick(R.id.holder)
        void click(View v) {
            IntentUtil.startActivity(v, ShopDetailActivity.getStartIntent(item.getId()));
        }

        @OnLongClick(R.id.holder)
        boolean longClick(View v) {
            showDialog();
            return true;
        }

        void showDialog() {
            if (dialog != null) {
                dialog.showDialog(p.getFM(), "delete");
            }
        }

        @Override
        public void destroy() {

        }

        @Override
        public void click(int which) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                p.deleteItem(item, getAdapterPosition());
            }
        }
    }
}
