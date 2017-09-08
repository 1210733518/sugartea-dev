package com.dianjiake.android.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.data.bean.ServiceSecondBean;
import com.dianjiake.android.ui.servicetype.ServiceTypeActivity;
import com.dianjiake.android.util.CheckEmptyUtil;
import com.dianjiake.android.util.FrescoUtil;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.util.UIUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lfs on 2017/7/25.
 */

public class ServiceSecondAdapter extends RecyclerView.Adapter {
    List<ServiceSecondBean> items;


    public ServiceSecondAdapter() {
        items = new ArrayList<>();
    }

    public void setItems(List<ServiceSecondBean> items) {
        this.items.clear();
        if (!CheckEmptyUtil.isEmpty(items)) {
            this.items.addAll(items);
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ServiceType.TITLE) {
            return new TitleVH(UIUtil.inflate(R.layout.item_service_second_title, parent));
        } else {
            return new NormalVH(UIUtil.inflate(R.layout.item_service_second, parent), this);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ServiceType.NORMAL) {
            NormalVH vh = (NormalVH) holder;
            vh.setItem(items.get(position));
        } else {
            TitleVH titleVH = (TitleVH) holder;
            titleVH.setItem(items.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class TitleVH extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;

        public TitleVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void setItem(ServiceSecondBean item) {
            title.setText(item.getViewTitle());
        }

    }

    public static class NormalVH extends RecyclerView.ViewHolder {
        @BindView(R.id.icon)
        SimpleDraweeView icon;
        @BindView(R.id.title)
        TextView title;
        ServiceSecondBean item;
        ServiceSecondAdapter adapter;

        public NormalVH(View itemView, ServiceSecondAdapter adapter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.adapter = adapter;
        }

        public void setItem(ServiceSecondBean item) {
            this.item = item;
            icon.setImageURI(FrescoUtil.getServiceTyoeIcon(item.getLogo()));
            title.setText(item.getMingcheng());
        }

        @OnClick(R.id.holder)
        void click(View v) {
            IntentUtil.startActivity(v, ServiceTypeActivity.getStartIntent(item,"64".equals(item.getSid())));
        }

    }
}
