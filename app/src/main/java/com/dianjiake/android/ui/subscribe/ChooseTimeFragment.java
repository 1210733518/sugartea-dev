package com.dianjiake.android.ui.subscribe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;


import com.dianjiake.android.R;
import com.dianjiake.android.base.BaseFragment;
import com.dianjiake.android.base.BasePresenter;
import com.dianjiake.android.custom.GridSpacingItemDecoration;
import com.dianjiake.android.data.bean.ChooseTimeBean;
import com.dianjiake.android.util.DateUtil;
import com.dianjiake.android.util.IntegerUtil;
import com.dianjiake.android.util.UIUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lfs on 2017/4/11.
 */

public class ChooseTimeFragment extends BaseFragment {

    private static final String KEY_DAY = "day";
    private static final String KEY_START_TIME = "starttime";
    private static final String KEY_END_TIME = "endtime";
    private static final long HALF_OF_HOUR = 30 * 60 * 1000;


    public static ChooseTimeFragment newInstance(int day, String startTime, String endTime) {
        Bundle args = new Bundle();
        args.putInt(KEY_DAY, day);
        args.putString(KEY_START_TIME, startTime);
        args.putString(KEY_END_TIME, endTime);
        ChooseTimeFragment fragment = new ChooseTimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.choose_time_rv)
    RecyclerView mChooseTimeRv;

    int mDay;
    String mStartTime;
    String mEndTime;
    ChooseTimeAdapter mAdapter;

    OnTimeCheckListener mOnTimeCheckListener;

    @Override
    protected int provideLayout() {
        return R.layout.fragment_choose_time;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mDay = arguments.getInt(KEY_DAY);
        mStartTime = arguments.getString(KEY_START_TIME);
        mEndTime = arguments.getString(KEY_END_TIME);
    }


    public void setOnTimeCheckListener(OnTimeCheckListener listener) {
        mOnTimeCheckListener = listener;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridLayoutManager lm = new GridLayoutManager(null, 5);
        mChooseTimeRv.setLayoutManager(new GridLayoutManager(null, 5));
        mChooseTimeRv.addItemDecoration(new GridSpacingItemDecoration(5, UIUtil.getDimensionPixelSize(R.dimen.base_size2), false));
        mAdapter = new ChooseTimeAdapter(mListener);
        mChooseTimeRv.setAdapter(mAdapter);

        int startTimeHour = IntegerUtil.parseInt(mStartTime.substring(0, mStartTime.indexOf(':')));
        int startTimeMin = IntegerUtil.parseInt(mStartTime.substring(mStartTime.indexOf(":") + 1));
        int endTimeHour = IntegerUtil.parseInt(mEndTime.substring(0, mEndTime.indexOf(':')));
        int endTimeMin = IntegerUtil.parseInt(mEndTime.substring(mEndTime.indexOf(":") + 1));

        Calendar nowCalendar = Calendar.getInstance(Locale.getDefault());
        int currentDay = nowCalendar.get(Calendar.DAY_OF_MONTH);
        if (nowCalendar.get(Calendar.MINUTE) <= 30 && nowCalendar.get(Calendar.MINUTE) > 0) {
            nowCalendar.set(Calendar.HOUR_OF_DAY, (nowCalendar.get(Calendar.HOUR_OF_DAY) + 1));
            nowCalendar.set(Calendar.MINUTE, 0);
        } else if (nowCalendar.get(Calendar.MINUTE) == 0) {
            nowCalendar.set(Calendar.MINUTE, 30);
        } else {
            nowCalendar.set(Calendar.HOUR_OF_DAY, (nowCalendar.get(Calendar.HOUR_OF_DAY) + 1));
            nowCalendar.set(Calendar.MINUTE, 30);
        }

        List<ChooseTimeBean> chooseTimes = new ArrayList<>();
        //判断是否为当天
        if (mDay != 0) {
            nowCalendar.add(Calendar.DAY_OF_YEAR, mDay);
            nowCalendar.set(Calendar.HOUR_OF_DAY, 0);
            nowCalendar.set(Calendar.MINUTE, 0);
            currentDay = nowCalendar.get(Calendar.DAY_OF_MONTH);
        }
        if (startTimeHour < endTimeHour) {
            while (currentDay == nowCalendar.get(Calendar.DAY_OF_MONTH)) {
                if (startTimeHour * 100 + startTimeMin > nowCalendar.get(Calendar.HOUR_OF_DAY) * 100 + nowCalendar.get(Calendar.MINUTE)) {
                    nowCalendar.add(Calendar.MINUTE, 30);
                    continue;
                }
                ChooseTimeBean bean = new ChooseTimeBean();
                bean.setTimestamp(nowCalendar.getTimeInMillis());
                chooseTimes.add(bean);
                nowCalendar.add(Calendar.MINUTE, 30);


                if (nowCalendar.get(Calendar.HOUR_OF_DAY) * 100 + nowCalendar.get(Calendar.MINUTE) > endTimeHour * 100 + endTimeMin - 100) {
                    break;
                }
            }
        } else {
            while (currentDay == nowCalendar.get(Calendar.DAY_OF_MONTH)) {
                if (endTimeHour * 100 + endTimeMin - 100 < nowCalendar.get(Calendar.HOUR_OF_DAY) * 100 + nowCalendar.get(Calendar.MINUTE)
                        && nowCalendar.get(Calendar.HOUR_OF_DAY) * 100 + nowCalendar.get(Calendar.MINUTE) < startTimeHour * 100 + startTimeMin
                        ) {
                    nowCalendar.add(Calendar.MINUTE, 30);
                    continue;
                }

                ChooseTimeBean bean = new ChooseTimeBean();
                bean.setTimestamp(nowCalendar.getTimeInMillis());
                chooseTimes.add(bean);
                nowCalendar.add(Calendar.MINUTE, 30);
            }
        }

        setChooseTimes(chooseTimes);

    }

    void setChooseTimes(List<ChooseTimeBean> times) {
        mAdapter.setItems(times);
    }

    OnItemCheckListener mListener = new OnItemCheckListener() {
        @Override
        public void onChecked(int position, long timestamp) {
            mAdapter.onChecked(position);
            if (mOnTimeCheckListener != null) {
                mOnTimeCheckListener.onChecked(getTag(), timestamp);
            }
        }
    };

    public void resetStates() {
        mAdapter.resetState();
    }

    public static class ChooseTimeAdapter extends RecyclerView.Adapter<ChooseViewHolder> {

        private List<ChooseTimeBean> mItems = new ArrayList<>();
        OnItemCheckListener mListener;

        public ChooseTimeAdapter(OnItemCheckListener listener) {
            mListener = listener;
        }

        @Override
        public ChooseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ChooseViewHolder vh = new ChooseViewHolder(UIUtil.inflate(viewType, parent));
            vh.setListener(mListener);
            return vh;
        }

        public void setItems(List<ChooseTimeBean> items) {
            mItems.clear();
            mItems.addAll(items);
            notifyDataSetChanged();
        }

        public void onChecked(int position) {
            mItems.get(position).setChecked(true);
            for (int i = 0; i < mItems.size(); i++) {
                if (mItems.get(i).isChecked() && i != position) {
                    mItems.get(i).setChecked(false);
                }
            }
            notifyDataSetChanged();
        }

        public void resetState() {
            for (int i = 0; i < mItems.size(); i++) {
                mItems.get(i).setChecked(false);
            }
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(ChooseViewHolder holder, int position) {
            holder.setItem(mItems.get(position));
        }

        @Override
        public int getItemViewType(int position) {
            return R.layout.item_choose_time;
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

    public static class ChooseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.choose_time_cb)
        CheckBox mChooseTimeCb;
        OnItemCheckListener mListener;
        ChooseTimeBean mBean;

        public ChooseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setListener(OnItemCheckListener listener) {
            mListener = listener;
        }

        public void setItem(ChooseTimeBean bean) {
            mBean = bean;
            mChooseTimeCb.setText(DateUtil.formatOnlyHM(bean.getTimestamp()));
            mChooseTimeCb.setChecked(bean.isChecked());
        }

        @OnClick(R.id.choose_time_cb)
        void onChecked(View view) {
            mListener.onChecked(getAdapterPosition(), mBean.getTimestamp());
        }
    }

    public interface OnItemCheckListener {
        void onChecked(int position, long timestamp);
    }

    public interface OnTimeCheckListener {
        void onChecked(String tag, long timestamp);
    }
}
