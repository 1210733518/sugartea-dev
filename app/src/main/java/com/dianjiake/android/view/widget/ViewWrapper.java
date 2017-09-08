package com.dianjiake.android.view.widget;

import android.view.View;

/**
 * Created by lfs on 2017/7/7.
 */

public class ViewWrapper {
    private View mTarget;

    public ViewWrapper(View target) {
        mTarget = target;
    }

    public int getWidth() {
        return mTarget.getLayoutParams().width;
    }

    public void setWidth(int width) {
        mTarget.getLayoutParams().width = width;
        mTarget.requestLayout();
    }
}
