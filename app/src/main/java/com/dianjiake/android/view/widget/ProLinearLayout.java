package com.dianjiake.android.view.widget;


import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dianjiake.android.R;

import java.sql.Time;
import java.util.ArrayList;

import timber.log.Timber;

/**
 * Created by lfs on 2017/7/13.
 */

public class ProLinearLayout extends LinearLayout {
    public ProLinearLayout(Context context) {
        super(context);
    }

    public ProLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ProLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        final int givenWidth = MeasureSpec.getSize(widthMeasureSpec);
//        final int givenHeight = MeasureSpec.getSize(heightMeasureSpec);
//        int wrapContentWidthSpec = MeasureSpec.makeMeasureSpec(givenWidth,
//                MeasureSpec.AT_MOST);
//        int wrapContentHeightSpec = MeasureSpec.makeMeasureSpec(givenHeight,
//                MeasureSpec.AT_MOST);
//        int totalWidth = getPaddingLeft() + getPaddingRight();
//        for (int i = 0; i < getChildCount(); i++) {
//            final View child = getChildAt(i);
//            if (child.getVisibility() == GONE) {
//                // We'll give it the rest of the space in the end
//                continue;
//            }
//            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
//            int childWidthSpec = getChildMeasureSpec(wrapContentWidthSpec,
//                    lp.leftMargin + lp.rightMargin, lp.width);
//            int childHeightSpec = getChildMeasureSpec(wrapContentHeightSpec,
//                    lp.topMargin + lp.bottomMargin, lp.height);
//            child.measure(childWidthSpec, childHeightSpec);
//            totalWidth += lp.leftMargin + lp.rightMargin + child.getMeasuredWidth();
//        }
//        Timber.d("measure " + totalWidth);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Timber.d("总长度" + (r - l));

        final int givenWidth = r - l;
        int totalWidth = getPaddingLeft() + getPaddingRight();
        int childrenWidthWithoutFirstChild = 0;
        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                // We'll give it the rest of the space in the end
                continue;
            }
            if (i != 0) {
                childrenWidthWithoutFirstChild += child.getMeasuredWidth();
            }
            totalWidth += child.getMeasuredWidth();
        }

        if (totalWidth > givenWidth) {
            if (getChildAt(0) instanceof TextView) {
                TextView child0 = (TextView) getChildAt(0);
                child0.setMaxWidth(givenWidth - childrenWidthWithoutFirstChild - 50);
            }
        }
        Timber.d("measure " + totalWidth);
        super.onLayout(changed, l, t, r, b);

    }
}
