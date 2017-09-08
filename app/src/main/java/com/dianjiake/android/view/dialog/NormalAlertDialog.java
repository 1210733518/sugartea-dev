package com.dianjiake.android.view.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import com.dianjiake.android.util.ResourceUtil;


/**
 * Created by lfs on 16/10/27.
 */

public class NormalAlertDialog extends BaseAlertDialog implements DialogInterface.OnClickListener {

    private String mMessage;
    private boolean mCancelable;
    private OnButtonClick mOnButtonClick;
    private boolean mIsTwoButton;

    public static NormalAlertDialog newInstance(String message) {
        return newInstance(message, true);
    }

    public static NormalAlertDialog newInstance(String message, boolean cancelable) {
        return newInstance(message, cancelable, false);
    }

    public static NormalAlertDialog newInstance(String message, boolean cancelable, boolean isTwoButton) {
        NormalAlertDialog dialog = new NormalAlertDialog();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        bundle.putBoolean("cancelable", cancelable);
        bundle.putBoolean("two", isTwoButton);
        dialog.setArguments(bundle);
        return dialog;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMessage = getArguments().getString("message");
        mCancelable = getArguments().getBoolean("cancelable", true);
        mIsTwoButton = getArguments().getBoolean("two", false);
    }

    @Override
    public Dialog createDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActivity);
        alertDialog.setMessage(mMessage).setPositiveButton("确定", this);
        if (mIsTwoButton) {
            alertDialog.setNegativeButton("取消", this);
        }
        alertDialog.setCancelable(mCancelable);
        setCancelable(mCancelable);
        return alertDialog.create();
    }

    public void setMessage(String message) {
        getArguments().putString("message", message);
    }

    public void setMessage(@StringRes int resId) {
        getArguments().putString("message", ResourceUtil.getString(resId));
    }

    public void setOnButtonClick(OnButtonClick onButtonClick) {
        mOnButtonClick = onButtonClick;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dismiss();
        if (mOnButtonClick != null) {
            mOnButtonClick.click(which);
        }
    }


    public interface OnButtonClick {
        void click(int which);
    }


}
