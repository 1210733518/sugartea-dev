package com.dianjiake.android.util;

import android.support.annotation.StringRes;
import android.widget.Toast;

import com.dianjiake.android.base.App;


/**
 * Created by lfs on 2017/5/15.
 */

public class ToastUtil {
    private static Toast sToast;

    private ToastUtil() {
    }

    private static Toast getInstance() {
        if (null == sToast) {
            synchronized (ToastUtil.class) {
                if (null == sToast) {
                    sToast = Toast.makeText(App.getInstance(), "", Toast.LENGTH_SHORT);
                }
            }
        }
        return sToast;
    }

    private static void setDurationAndShow(int duration) {
        sToast.setDuration(duration);
        sToast.show();
    }

    public static void showLongToast(String str) {
        getInstance().setText(str);
        setDurationAndShow(Toast.LENGTH_LONG);
    }

    public static void showLongToast(@StringRes int strRes) {
        getInstance().setText(strRes);
        setDurationAndShow(Toast.LENGTH_LONG);
    }

    public static void showShortToast(String str) {
        getInstance().setText(str);
        setDurationAndShow(Toast.LENGTH_SHORT);
    }

    public static void showShortToast(@StringRes int strRes) {
        getInstance().setText(strRes);
        setDurationAndShow(Toast.LENGTH_SHORT);
    }


    public static void showServiceErrorToast() {
        showServiceErrorToast("");
    }

    public static void showServiceErrorToast(String message) {
        StringBuilder sb = new StringBuilder("出现问题了╮(╯_╰)╭，请重试");
//        if (!TextUtils.isEmpty(message)) {
//            sb.append("\n");
//            sb.append(message);
//        }
        getInstance().setText(sb.toString());
        setDurationAndShow(Toast.LENGTH_SHORT);
    }

    public static void showNetworkErrorToast() {
        getInstance().setText("网络异常，请检查网络设置");
        setDurationAndShow(Toast.LENGTH_SHORT);
    }
}

