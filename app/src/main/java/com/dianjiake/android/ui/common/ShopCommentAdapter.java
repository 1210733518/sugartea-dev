package com.dianjiake.android.ui.common;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.data.bean.ShopCommentBean;
import com.dianjiake.android.util.CheckEmptyUtil;
import com.dianjiake.android.util.DateUtil;
import com.dianjiake.android.util.FloatUtil;
import com.dianjiake.android.util.FrescoUtil;
import com.dianjiake.android.util.UIUtil;
import com.dianjiake.android.view.widget.BaseLoadMoreAdapter;
import com.dianjiake.android.view.widget.BaseViewHolder;
import com.dianjiake.android.view.widget.StarView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by lfs on 2017/7/17.
 */

public class ShopCommentAdapter extends BaseLoadMoreAdapter<ShopCommentBean> {

    public ShopCommentAdapter(List<ShopCommentBean> items) {
        super(items);
    }

    @Override
    public void myOnBindViewHolder(BaseViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        vh.setItem(getItem(position));
    }

    @Override
    public BaseViewHolder myOnCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.newInstance(parent);
    }

    @Override
    public int myGetItemViewType(int position) {
        return 0;
    }

    public static class ViewHolder extends BaseViewHolder {


        @BindView(R.id.avatar)
        SimpleDraweeView avatar;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.star_view)
        StarView starView;
        @BindView(R.id.comment)
        TextView comment;

        public static ViewHolder newInstance(ViewGroup parent) {
            return new ViewHolder(UIUtil.inflate(R.layout.item_shop_comment, parent));
        }

        private ViewHolder(View itemView) {
            super(itemView);
        }

        public void setItem(ShopCommentBean bean) {
            if (bean.getUser() != null) {
                avatar.setImageURI(FrescoUtil.getAvatarUri(bean.getUser().getAvatar()));
                name.setText(bean.getUser().getNickname());
            }
            starView.setScore(FloatUtil.parseFloat(bean.getPingfen()));
            time.setText(DateUtil.formatMDHM(bean.getShijian()));
            comment.setText(bean.getNeirong());
            comment.setVisibility(CheckEmptyUtil.isEmpty(bean.getNeirong()) ? View.GONE : View.VISIBLE);
        }

        @Override
        public void destroy() {

        }
    }
}
