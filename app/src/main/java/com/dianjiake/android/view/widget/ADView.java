package com.dianjiake.android.view.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.dianjiake.android.R;
import com.dianjiake.android.data.bean.ADItemBean;
import com.dianjiake.android.util.UIUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮播广告
 * Created by lfs on 17/3/8.
 */

public class ADView extends ProportionLayout {
    AutoScrollViewPager mViewPager;
    ADAdapter mADAdapter;
    LinearLayout mLinearLayout;
    List<ImageView> mIcons;

    public ADView(Context context) {
        super(context);
        init(context);
    }

    public ADView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ADView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //将inflate的布局至于自己当中
        View view = inflate(context, R.layout.adview, this);
        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mViewPager = (AutoScrollViewPager) view.findViewById(R.id.adview_viewpager);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.adview_ll);
        mIcons = new ArrayList<>();

    }

    public void setItems(List<ADItemBean> items) {
        //设置viewpager不加载左右页面
        mViewPager.setOffscreenPageLimit(0);

        initIndicator(items.size());
        /*************新的广告条**************/
        if (mADAdapter == null) {
            /*************新的广告条**************/
            mADAdapter = new ADAdapter();

            mViewPager.setAdapter(mADAdapter);
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

            mViewPager.setInterval(5000);  //间隔时间
            mViewPager.setDirection(AutoScrollViewPager.RIGHT);
            mViewPager.setAutoScrollDurationFactor(2.0); //控制滑动的速度
            mViewPager.setCycle(true);//是否循环滑动
            mViewPager.setStopScrollWhenTouch(true); //设置手指摸得时候停止滑动
            mViewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_NONE);//设置不能从边上滑动
            mViewPager.setBorderAnimation(true); //设置边界效果
//            mViewPager.setPageTransformer(false, new ParallaxPagerTransformer(R.id.fragment_timeline_hot_header_ad_image));
        }
        setIndicatorPosition(0);
        mADAdapter.setItems(items);
        mADAdapter.notifyDataSetChanged();
//        mAutoScrollViewPager.setItems(mItems);
        mViewPager.setOffscreenPageLimit(items.size());
        mViewPager.setCurrentItem(0);
        mViewPager.startAutoScroll();
    }
    //往linearlayout中动态添加图片
    private void initIndicator(int size) {
        mLinearLayout.removeAllViews();
        mIcons.clear();
        for (int i = 0; i < size; i++) {
            if (size > 1) {
                ImageView imageView = new ImageView(getContext());
                int iconSize = UIUtil.getDimensionPixelSize(R.dimen.ad_icon_size);
                int marginSize = UIUtil.getDimensionPixelSize(R.dimen.ad_icon_size);
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


    public static class ADAdapter extends PagerAdapter {

        private List<ADItemBean> mItems;

        public ADAdapter() {
            mItems = new ArrayList<>();
        }

        public void setItems(List items) {
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
            ADItemView adView = (ADItemView) container.findViewWithTag(position);
            if (adView == null) {
                adView = ADItemView.newInstance(container.getContext());
            }
            // TODO: 2017/7/10 录播图接口还没有
            adView.load(mItems.get(position));
            adView.setTag(position);
            container.addView(adView);
            return adView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (mItems.size() > position) {
                container.removeView((View) object);
            }
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
