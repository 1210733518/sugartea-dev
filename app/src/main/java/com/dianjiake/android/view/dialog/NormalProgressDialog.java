package com.dianjiake.android.view.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.dianjiake.android.R;
import com.dianjiake.android.util.ResourceUtil;

/**
 * Created by Sacowiw on 2017/5/17.
 */

public class NormalProgressDialog extends DialogFragment {
    private String mMessage;
    private long mLastTimestamp;
    private String mTag = System.currentTimeMillis() + "";

    public static NormalProgressDialog newInstance(String message) {
        NormalProgressDialog baseProgressDialog = new NormalProgressDialog();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        baseProgressDialog.setArguments(bundle);
        return baseProgressDialog;
    }

    public static NormalProgressDialog newInstance(@StringRes int resId) {
        String message = ResourceUtil.getString(resId);
        return newInstance(message);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMessage = getArguments().getString("message");
        setStyle(STYLE_NORMAL, R.style.Theme_AppCompat_Light_Dialog_Alert);
        setCancelable(false);
    }


    public void setMessage(String message) {
        getArguments().putString("message", message);
    }

    public void setMessage(@StringRes int resId) {
        getArguments().putString("message", ResourceUtil.getString(resId));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage(mMessage);
        return dialog;
    }

    public void showDialog(FragmentManager fm, @Nullable String tag) {
        if (!TextUtils.isEmpty(tag)) {
            mTag = tag;
        }

        if (System.currentTimeMillis() - mLastTimestamp > 500 && !isAdded()) {
            show(fm, tag);
            mLastTimestamp = System.currentTimeMillis();
        }
    }

}
