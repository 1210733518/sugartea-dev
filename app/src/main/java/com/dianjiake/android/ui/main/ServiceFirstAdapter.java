package com.dianjiake.android.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.data.bean.ServiceFirstBean;
import com.dianjiake.android.util.CheckEmptyUtil;
import com.dianjiake.android.util.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lfs on 2017/7/25.
 */

public class ServiceFirstAdapter extends RecyclerView.Adapter<ServiceFirstAdapter.ViewHolder> {
    List<ServiceFirstBean> items;
    int checkedPosition;
    OnFirstClickListener listener;

    public ServiceFirstAdapter() {
        items = new ArrayList<>();
    }

    public void setItems(List<ServiceFirstBean> items) {
        this.items.clear();
        if (!CheckEmptyUtil.isEmpty(items)) {
            this.items.addAll(items);
        }
        notifyDataSetChanged();
    }

    public void setListener(OnFirstClickListener listener) {
        this.listener = listener;
    }

    public int getCheckedPosition() {
        return checkedPosition;
    }

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
        if (listener != null) {
            listener.onClick(items.get(checkedPosition), checkedPosition);
        }
        notifyDataSetChanged();
    }

    public void scrollToPosition(int position) {
        this.checkedPosition = position;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(UIUtil.inflate(R.layout.item_service_first, parent), this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static interface OnFirstClickListener {
        void onClick(ServiceFirstBean firstBean, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        ServiceFirstAdapter adapter;

        public ViewHolder(View itemView, ServiceFirstAdapter adapter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.adapter = adapter;
        }

        public void setItem(ServiceFirstBean item) {
            title.setText(item.getMingcheng());
            title.setTextColor(getAdapterPosition() == adapter.getCheckedPosition() ?
                    UIUtil.getColor(R.color.main) : UIUtil.getColor(R.color.text_content_secondary));
            title.setBackgroundResource(getAdapterPosition() == adapter.getCheckedPosition() ?
                    R.color.background : R.drawable.click_white);
        }

        @OnClick(R.id.title)
        void click(View v) {
            adapter.setCheckedPosition(getAdapterPosition());
        }
    }
}
