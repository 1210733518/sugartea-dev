package com.dianjiake.android.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.dianjiake.android.R;
import com.dianjiake.android.util.UIUtil;

/**
 * Created by lfs on 2017/7/26.
 */

public class MsgIconView extends AppCompatImageView {
    boolean show;
    int width;

    public MsgIconView(Context context) {
        super(context);
    }

    public MsgIconView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MsgIconView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
    }

    public void setShowRedIcon(boolean show) {
        this.show = show;
        setWillNotDraw(false);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (show) {
            Paint paint = new Paint();
            paint.setColor(UIUtil.getColor(R.color.main));
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            int size = UIUtil.getDimensionPixelSize(R.dimen.base_size1);
            canvas.drawCircle(width, size/2, size, paint);
        }
    }
}
