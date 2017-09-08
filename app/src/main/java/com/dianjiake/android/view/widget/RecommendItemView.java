package com.dianjiake.android.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.dianjiake.android.R;
import com.dianjiake.android.data.bean.HomeShopBean;
import com.dianjiake.android.ui.shopdetail.ShopDetailActivity;
import com.dianjiake.android.util.CheckEmptyUtil;
import com.dianjiake.android.util.FrescoUtil;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.util.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Sacowiw on 2017/3/29.
 */

public class RecommendItemView extends LinearLayout {
    @BindViews({R.id.recommend_image0, R.id.recommend_image1, R.id.recommend_image2, R.id.recommend_image3,
            R.id.recommend_image4, R.id.recommend_image5, R.id.recommend_image6, R.id.recommend_image7})
    List<SimpleDraweeView> mImages;

    @BindViews({R.id.recommend_text0, R.id.recommend_text1, R.id.recommend_text2, R.id.recommend_text3,
            R.id.recommend_text4, R.id.recommend_text5, R.id.recommend_text6, R.id.recommend_text7})
    List<TextView> mTexts;

    List<HomeShopBean> mItems = new ArrayList<>();

    public RecommendItemView(Context context) {
        super(context);
        init();
    }

    public RecommendItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RecommendItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.recommend_item_view, this);
        setOrientation(VERTICAL);
        ButterKnife.bind(this, view);
    }

    public void setItems(List<HomeShopBean> items) {
        mItems.clear();
        mItems.addAll(items);
        for (int i = 0; i < mItems.size() && i < 8; i++) {
            mImages.get(i).setImageURI(FrescoUtil.getShopLogoUri(mItems.get(i).getTubiao(), mItems.get(i).getCover()));
            mTexts.get(i).setText(mItems.get(i).getMingcheng());
        }
    }

    @OnClick({R.id.recommend_image0, R.id.recommend_image1, R.id.recommend_image2, R.id.recommend_image3,
            R.id.recommend_image4, R.id.recommend_image5, R.id.recommend_image6, R.id.recommend_image7})
    void onClick(View v) {
        for (int i = 0; i < mImages.size(); i++) {
            if (v.getId() == mImages.get(i).getId()) {
                if (!CheckEmptyUtil.isEmpty(mItems) && mItems.size() > i) {
                    IntentUtil.startActivity(v, ShopDetailActivity.getStartIntent(mItems.get(i).getId()));
                } else {
                    ToastUtil.showShortToast("店铺不存在");
                }
                break;
            }
        }
    }

}
