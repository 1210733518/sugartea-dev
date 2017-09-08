package com.dianjiake.android.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dianjiake.android.R;
import com.dianjiake.android.util.EventUtil;
import com.dianjiake.android.util.UIUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lfs on 2017/7/10.
 */

public class HomeFilterView extends FrameLayout {

    @BindView(R.id.filter_group)
    RadioGroup filter_group;
    @BindView(R.id.filter_base)
    RadioButton filterBase;
    @BindView(R.id.filter_score)
    RadioButton filterScore;
    @BindView(R.id.filter_distance)
    RadioButton filterDistance;

    public HomeFilterView(Context context) {
        super(context);
        init();
    }

    public HomeFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HomeFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.home_filter_view, this);
        ButterKnife.bind(this, view);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtil.getDimensionPixelSize(R.dimen.button_size_normal));
        setLayoutParams(lp);
        setClickable(true);
    }

    public RadioGroup getFilterGroup() {
        return filter_group;
    }
}
