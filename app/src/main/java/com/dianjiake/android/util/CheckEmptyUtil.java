package com.dianjiake.android.util;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by lfs on 2017/5/15.
 */

public class CheckEmptyUtil {

    public static boolean isEmpty(List list) {
        return list == null ? true : list.isEmpty();
    }

    public static boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

}
