package com.dianjiake.android.ui.searchlocation;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.dianjiake.android.R;
import com.dianjiake.android.util.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lfs on 2017/7/12.
 */

public class SearchResultAdapter extends BaseAdapter {
    List<PoiItem> list = new ArrayList<>();
    SearchLocationContract.Presenter presenter;

    public SearchResultAdapter(SearchLocationContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void setItems(List<PoiItem> items) {
        list.clear();
        list.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public PoiItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder(parent, presenter);
            convertView = vh.getItemView();
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.setItem(getItem(position), position);
        return convertView;
    }

    public static class ViewHolder {
        SearchLocationContract.Presenter presenter;
        View itemView;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.current)
        TextView current;
        @BindView(R.id.holder)
        LinearLayout holder;
        @BindView(R.id.relocation_text)
        TextView relocationText;
        @BindView(R.id.desc)
        TextView desc;

        PoiItem item;

        public ViewHolder(ViewGroup parent, SearchLocationContract.Presenter presenter) {
            this.presenter = presenter;
            itemView = UIUtil.inflate(R.layout.item_search_location_result, parent);
            ButterKnife.bind(this, itemView);
            itemView.setTag(this);
        }

        public View getItemView() {
            return itemView;
        }

        public void setItem(PoiItem item, int position) {
            this.item =item;
            name.setText(item.getTitle());
            desc.setText(item.getSnippet());
            setFunctionTextVisible(position);
        }


        private void setFunctionTextVisible(int position) {
            current.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
            relocationText.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
            name.setMaxWidth(position == 0 ? UIUtil.getScreenWidth() * 1 / 2 : UIUtil.getScreenWidth() * 3 / 4);
        }

        @OnClick(R.id.relocation_text)
        void clickReloaction(View v) {
            presenter.reLocation();
        }

        @OnClick(R.id.item_holder)
        void clickHolder(View v) {
            presenter.chooseLocation(item);
        }
    }

}
