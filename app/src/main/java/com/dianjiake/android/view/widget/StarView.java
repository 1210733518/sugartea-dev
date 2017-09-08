package com.dianjiake.android.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dianjiake.android.R;
import com.dianjiake.android.util.UIUtil;

import timber.log.Timber;

/**
 * Created by lfs on 2017/7/10.
 */

public class StarView extends LinearLayout {
    int totalStars;
    int positiveStarRes;
    int negativeStarRes;
    int size;
    float score;

    public StarView(Context context) {
        super(context);
        init();
    }

    public StarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public StarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StarView, defStyleAttr, 0);
        totalStars = typedArray.getInteger(R.styleable.StarView_total_stars, 5);
        positiveStarRes = typedArray.getResourceId(R.styleable.StarView_positive_star, R.drawable.ic_star_positive);
        negativeStarRes = typedArray.getResourceId(R.styleable.StarView_negative_star, R.drawable.ic_star_negative);
        size = (int) UIUtil.dp2px(typedArray.getDimension(R.styleable.StarView_size, 12f));
        score = typedArray.getFloat(R.styleable.StarView_score, 2f);
        typedArray.recycle();
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        if (positiveStarRes == 0) {
            positiveStarRes = R.drawable.ic_star_positive;
        }

        if (negativeStarRes == 0) {
            negativeStarRes = R.drawable.ic_star_negative;
        }

        removeAllViews();

        for (int i = 0; i < totalStars; i++) {
            ImageView iv = new ImageView(getContext());
            LayoutParams lp = new LayoutParams(size, size);
            if (i != totalStars - 1) {
                lp.setMargins(0, 0, 4, 0);
            }
            iv.setLayoutParams(lp);
            iv.setImageResource(negativeStarRes);
            addView(iv);
        }

        setScore(score);
    }

    public void setScore(float score) {
        Timber.d("score = " + score);
        this.score = score;
        int fScore = Math.round(score);
        if (fScore > totalStars) {
            fScore = totalStars;
        }

        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i) instanceof ImageView) {
                ImageView iv = (ImageView) getChildAt(i);
                iv.setImageResource(i < fScore ? positiveStarRes : negativeStarRes);
            }
        }
    }


}
