package com.dianjiake.android.view.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.dianjiake.android.R;
import com.dianjiake.android.data.bean.HomeShopBean;
import com.dianjiake.android.util.UIUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sacowiw on 2017/3/29.
 */

public class RecommendView extends FrameLayout {
    ViewPager mViewPager;
    LinearLayout mLinearLayout;
    RecommendAdapter mAdapter;

    List<ImageView> mIcons = new ArrayList<>();

    public RecommendView(Context context) {
        super(context);
        init();
    }

    public RecommendView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RecommendView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.recommend_view, this);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.recommend_ll);
    }

    public void setItems(List<HomeShopBean> items) {
        int size = (int) Math.ceil(items.size() / (double) 8);
        initIndicator(size);
        if (mAdapter == null) {
            mAdapter = new RecommendAdapter();
            mViewPager.setAdapter(mAdapter);
            mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    setIndicatorPosition(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

        List<List<HomeShopBean>> contents = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int end = 8 * (i + 1);
            if ((i + 1) * 8 >= size) {
                end = items.size();
            }
            contents.add(items.subList(i * 8, end));
        }
        mAdapter.setItems(contents);
        mAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(0);
        setIndicatorPosition(0);
    }

    private void initIndicator(int size) {
        mLinearLayout.removeAllViews();
        mIcons.clear();
        for (int i = 0; i < size; i++) {
            if (size > 1) {
                ImageView imageView = new ImageView(mLinearLayout.getContext());
                int iconSize = UIUtil.getDimensionPixelSize(R.dimen.size6);
                int marginSize = UIUtil.getDimensionPixelSize(R.dimen.size6);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(iconSize, iconSize);
                lp.setMargins(0, 0, marginSize, marginSize * 2);
                imageView.setLayoutParams(lp);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageResource(R.drawable.indicator_negative);
                mIcons.add(imageView);
                mLinearLayout.addView(imageView);
            }
        }
    }

    private void setIndicatorPosition(int position) {
        if (position >= mIcons.size()) return;
        for (int i = 0; i < mIcons.size(); i++) {
            if (i == position) {
                mIcons.get(i).setImageResource(R.drawable.indicator_positive);
            } else {
                mIcons.get(i).setImageResource(R.drawable.indicator_negative);
            }
        }
    }


    public static class RecommendAdapter extends PagerAdapter {

        private List<List<HomeShopBean>> mItems;

        public RecommendAdapter() {
            mItems = new ArrayList<>();
        }

        public void setItems(List<List<HomeShopBean>> items) {
            mItems.clear();
            mItems.addAll(items);
        }

        @Override
        public int getCount() {
            return mItems == null ? 0 : mItems.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            RecommendItemView v = (RecommendItemView) container.findViewWithTag(position);
            if (v == null) {
                v = new RecommendItemView(container.getContext());
            }
            v.setItems(mItems.get(position));
            v.setTag(position);

            container.addView(v);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }


}
