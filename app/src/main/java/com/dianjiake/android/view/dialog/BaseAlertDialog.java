package com.dianjiake.android.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.dianjiake.android.R;
import com.dianjiake.android.util.UIUtil;


/**
 * AlertDialog基类，实现material design样式向前兼容
 * Created by lfs on 16/10/27.
 */

public abstract class BaseAlertDialog extends DialogFragment implements DialogInterface.OnShowListener {

    protected AppCompatActivity mActivity;
    private long mLastShowTime;
    private String mTag = System.currentTimeMillis() + "";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (AppCompatActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.BaseAlertDialog);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Deprecated
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog dialog = (AlertDialog) createDialog(savedInstanceState);

        dialog.setOnShowListener(this);
        return dialog;
    }

    public abstract Dialog createDialog(Bundle savedInstanceState);


    @Override
    public void onShow(DialogInterface dialog) {
        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(UIUtil.getColor(R.color.main));
        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(UIUtil.getColor(R.color.text_content_text));
    }

    /**
     * 显示dialog，防止多重显示
     */
    public void showDialog(@NonNull FragmentManager fm, @Nullable String tag) {
        if (!TextUtils.isEmpty(tag)) {
            mTag = tag;
        }

        if (!isAdded() && System.currentTimeMillis() - mLastShowTime > 500) {
            mLastShowTime = System.currentTimeMillis();
            show(fm, mTag);
        }
    }
}
