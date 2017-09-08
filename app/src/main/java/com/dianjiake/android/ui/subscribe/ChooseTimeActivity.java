package com.dianjiake.android.ui.subscribe;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.dianjiake.android.R;
import com.dianjiake.android.base.BasePresenter;
import com.dianjiake.android.base.BaseTranslateActivity;
import com.dianjiake.android.common.ActiivtyDataHelper;
import com.dianjiake.android.util.IntegerUtil;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.util.ToastUtil;
import com.dianjiake.android.view.widget.ChooseTimeTabView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lfs on 2017/6/16.
 */

public class ChooseTimeActivity extends BaseTranslateActivity {
    private static final String KEY_START_TIME = "starttime";
    private static final String KEY_END_TIME = "endtime";
    private static final long HALF_OF_HOUR = 30 * 60 * 1000;
    private static final long HOUR = 60 * 60 * 1000;
    private static final String DAY_NAMES[] = new String[]{"今天", "明天"};
    private static final String WEEK_NAMES[] = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    @BindView(R.id.toolbar_icon_left)
    ImageView mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_icon_right)
    ImageView mToolbarRight;
    @BindView(R.id.choose_time_tab)
    TabLayout mTabLayout;
    @BindView(R.id.choose_time_content)
    FrameLayout mContent;
    @BindView(R.id.choose_time_button)
    Button mButton;

    int mStartDay = 0;
    long mCheckTimestamp;

    Map<String, ChooseTimeFragment> mFragmentMap = new ArrayMap<>();

    public static Intent getStartIntent(String startTime, String endTime) {
        Intent intent = IntentUtil.getIntent(ChooseTimeActivity.class);
        intent.putExtra(KEY_START_TIME, startTime);
        intent.putExtra(KEY_END_TIME, endTime);
        return intent;
    }

    @Override
    public int provideContentView() {
        return R.layout.activity_choose_time;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void create(@Nullable Bundle savedInstanceState) {
        mToolbarTitle.setText("选择服务时间");
        String startTime = getIntent().getStringExtra(KEY_START_TIME);
        String endTime = getIntent().getStringExtra(KEY_END_TIME);
        List<String> tabTitles = getTabTitles(startTime, endTime);

        FragmentManager fm = getFragmentManager();
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < tabTitles.size(); i++) {
            ChooseTimeTabView tabView = new ChooseTimeTabView(this);
            tabView.setWeekAndDay(tabTitles.get(i));
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(tabView));
            ChooseTimeFragment f = (ChooseTimeFragment) fm.findFragmentByTag(tabTitles.get(i));
            if (f == null) {
                f = ChooseTimeFragment.newInstance(i + mStartDay, startTime, endTime);
            }
            f.setOnTimeCheckListener(mOnTimeCheckListener);
            fm.beginTransaction().add(R.id.choose_time_content, f, tabTitles.get(i))
                    .hide(f)
                    .commit();
            mFragmentMap.put(tabTitles.get(i), f);
        }
        fm.beginTransaction().show(mFragmentMap.get(tabTitles.get(0))).commit();


        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ChooseTimeTabView tabView = (ChooseTimeTabView) tab.getCustomView();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                for (String key : mFragmentMap.keySet()) {
                    if (key.equals(tabView.getText())) {
                        ft.show(mFragmentMap.get(key));
                    } else {
                        ft.hide(mFragmentMap.get(key));
                        mFragmentMap.get(key).resetStates();
                    }
                }
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    ChooseTimeFragment.OnTimeCheckListener mOnTimeCheckListener = new ChooseTimeFragment.OnTimeCheckListener() {
        @Override
        public void onChecked(String tag, long timestamp) {
            mCheckTimestamp = timestamp;
            setButtonStates();
            for (String key : mFragmentMap.keySet()) {
                if (!key.equals(tag)) {
                    mFragmentMap.get(key).resetStates();
                }
            }
        }
    };

    String getButtonState() {
        if (mCheckTimestamp == 0) {
            return "请选择服务时间";
        } else {
            return null;
        }
    }

    void setButtonStates() {
        if (!TextUtils.isEmpty(getButtonState())) {
            mButton.setBackgroundResource(R.drawable.click_main_unable);
        } else {
            mButton.setBackgroundResource(R.drawable.click_main);
        }
    }

    @OnClick(R.id.choose_time_button)
    void click(View v) {
        if (TextUtils.isEmpty(getButtonState())) {
            Intent data = ActiivtyDataHelper.getChooseTimeData(mCheckTimestamp);
            setResult(RESULT_OK, data);

            finish();
        } else {
            ToastUtil.showShortToast(getButtonState());
        }
    }

    @OnClick(R.id.toolbar_icon_left)
    void clickBack(View v) {
        finish();
    }

    List<String> getTabTitles(String startTime, String endTime) {
        List<String> titles = new ArrayList<>();
        Calendar nowCalendar = Calendar.getInstance(Locale.getDefault());
        mStartDay = 0;//开始日期，从今天开算

        int startTimeHour = IntegerUtil.parseInt(startTime.substring(0, startTime.indexOf(':')));
        int startTimeMin = IntegerUtil.parseInt(startTime.substring(startTime.indexOf(":") + 1));
        int endTimeHour = IntegerUtil.parseInt(endTime.substring(0, endTime.indexOf(':')));
        int endTimeMin = IntegerUtil.parseInt(endTime.substring(endTime.indexOf(":") + 1));

        //开始时间小于结束时间，不夸天计算
        if (startTimeHour < endTimeHour) {
            //直接计算下一天的条件
            Calendar temp = (Calendar) nowCalendar.clone();
            temp.set(Calendar.HOUR_OF_DAY, endTimeHour);
            temp.set(Calendar.MINUTE, endTimeMin);
            if (nowCalendar.get(Calendar.HOUR_OF_DAY) >= endTimeHour
                    || nowCalendar.getTimeInMillis() + HALF_OF_HOUR > temp.getTimeInMillis() - HOUR) {
                mStartDay = 1;
            }

        } else {
            if (nowCalendar.get(Calendar.HOUR_OF_DAY) >= 23) {
                mStartDay = 1;
            }
        }

        Calendar titleCalendar = Calendar.getInstance(Locale.getDefault());
        if (mStartDay == 1) {
            titleCalendar.set(Calendar.DAY_OF_YEAR, nowCalendar.get(Calendar.DAY_OF_YEAR) + 1);
        }
        for (int i = 0; i < 5; i++) {
            String weekDisplay = "";
            String dayDisplay = "";
            if (mStartDay == 0 && i < 2) {
                weekDisplay = DAY_NAMES[i];
            } else if (i < 1) {
                weekDisplay = DAY_NAMES[i + 1];
            } else {
                weekDisplay = WEEK_NAMES[titleCalendar.get(Calendar.DAY_OF_WEEK) - 1];
            }

            dayDisplay = "-"
                    + (titleCalendar.get(Calendar.MONTH) + 1)
                    + "月"
                    + titleCalendar.get(Calendar.DAY_OF_MONTH)
                    + "日";
            String title = weekDisplay + dayDisplay;
            titles.add(title);

            titleCalendar.set(Calendar.DAY_OF_YEAR, (titleCalendar.get(Calendar.DAY_OF_YEAR) + 1));
        }

        return titles;
    }


}
