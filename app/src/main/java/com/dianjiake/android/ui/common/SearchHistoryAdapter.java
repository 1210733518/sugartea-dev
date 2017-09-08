package com.dianjiake.android.ui.common;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.data.model.SearchHistoryModel;
import com.dianjiake.android.util.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lfs on 2017/7/12.
 */

public class SearchHistoryAdapter extends BaseAdapter {
    List<SearchHistoryModel> items = new ArrayList<>();

    public void setItems(List<SearchHistoryModel> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public SearchHistoryModel getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder(parent);
            convertView = vh.getItemView();
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.setItem(getItem(position).getSearch());
        return convertView;
    }

    public static class ViewHolder {
        View itemView;
        @BindView(R.id.title)
        TextView title;

        public ViewHolder(ViewGroup parent) {
            itemView = UIUtil.inflate(R.layout.item_search_history, parent);
            ButterKnife.bind(this, itemView);
            itemView.setTag(this);
        }

        public View getItemView() {
            return itemView;
        }

        public void setItem(String search) {
            title.setText(search);
        }
    }

}
