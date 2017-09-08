package com.dianjiake.android.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dianjiake.android.R;


/**
 * 自定义标签
 * Created by lfs on 2017/4/14.
 */

public class ChooseTimeTabView extends LinearLayout {
    TextView mWeek;
    TextView mDay;
    String mText;

    public ChooseTimeTabView(Context context) {
        super(context);
        init();
    }

    public ChooseTimeTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChooseTimeTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        View view = inflate(getContext(), R.layout.choose_time_tab, this);
        mWeek = (TextView) view.findViewById(R.id.choose_time_week);
        mDay = (TextView) view.findViewById(R.id.choose_time_day);
    }


    public void setWeekAndDay(String weekAndDay) {
        mText = weekAndDay;
        mWeek.setText(weekAndDay.substring(0, weekAndDay.indexOf('-')));
        mDay.setText(weekAndDay.substring(weekAndDay.indexOf('-') + 1));
    }

    public String getText() {
        return mText;
    }

}
