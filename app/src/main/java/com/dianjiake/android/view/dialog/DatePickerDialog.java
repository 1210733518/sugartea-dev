package com.dianjiake.android.view.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;

import com.dianjiake.android.R;
import com.dianjiake.android.util.UIUtil;
import com.dianjiake.android.view.calender.GregorianLunarCalendarView;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by lfs on 17/2/20.
 */

public class DatePickerDialog extends BaseAlertDialog implements DialogInterface.OnClickListener {

    String mTitle = "";
    GregorianLunarCalendarView mCalendarView;
    OnPickListener mListener;

    public static DatePickerDialog newInstance(String title) {

        Bundle args = new Bundle();
        args.putString("title", title);
        DatePickerDialog fragment = new DatePickerDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String str = getArguments().getString("title");
        mTitle = TextUtils.isEmpty(str) ? "请选择日期" : str;
    }

    @Override
    public Dialog createDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        View view = UIUtil.inflate(R.layout.dialog_date_picker, mActivity);
        mCalendarView = (GregorianLunarCalendarView) view.findViewById(R.id.calendar_view);
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(1990, 0, 1);
        mCalendarView.init(calendar);

        builder.setTitle(mTitle)
                .setView(view)
                .setCancelable(true)
                .setPositiveButton("确定", this)
                .setNegativeButton("取消", this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE && mCalendarView != null && mListener != null) {
            GregorianLunarCalendarView.CalendarData data = mCalendarView.getCalendarData();
            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            calendar.set(data.pickedYear, data.pickedMonthSway - 1, data.pickedDay);
            mListener.onPick(calendar.getTimeInMillis());
        }
    }

    public void setOnPickListener(OnPickListener li) {
        mListener = li;
    }

    public static interface OnPickListener {
        void onPick(long timestamp);
    }
}
