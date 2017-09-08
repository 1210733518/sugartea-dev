package com.dianjiake.android.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.dianjiake.android.R;
import com.dianjiake.android.base.App;
import com.dianjiake.android.util.AppUtil;
import com.dianjiake.android.util.UIUtil;


/**
 * Created by lfs on 2017/5/19.
 */

public class ToolbarSpaceView extends FrameLayout {

    public ToolbarSpaceView(@NonNull Context context) {
        super(context);
        init();
    }

    public ToolbarSpaceView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ToolbarSpaceView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (!AppUtil.isMIUIV6() && !AppUtil.isFlyme() && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M)) {
            setBackgroundResource(R.color.text_grey_tab);
        }else{
            setBackgroundResource(R.color.translate);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(UIUtil.getStatusBarHeight(), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
