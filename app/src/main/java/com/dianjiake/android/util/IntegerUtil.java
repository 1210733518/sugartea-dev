package com.dianjiake.android.util;

/**
 * Created by Sacowiw on 2017/5/25.
 */

public class IntegerUtil {
    public static int parseInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (Exception e) {
            return 0;
        }
    }
}
