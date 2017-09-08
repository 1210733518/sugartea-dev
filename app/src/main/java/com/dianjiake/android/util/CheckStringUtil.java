package com.dianjiake.android.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sacowiw on 2017/5/16.
 */

public class CheckStringUtil {
    public static boolean isPhoneNumber(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        Pattern p = Pattern
                .compile("^((13)|(14)|(15)|(17)|(18))\\d{9}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }
}
