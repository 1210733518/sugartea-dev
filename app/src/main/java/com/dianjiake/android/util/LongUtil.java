package com.dianjiake.android.util;

/**
 * Created by Sacowiw on 2017/5/31.
 */

public class LongUtil {
    public static long parseLong(String s) {
        try {
            double d = Double.parseDouble(s);
            return (long) d;
        } catch (Exception e) {
            return 0;
        }
    }
}
