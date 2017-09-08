package com.dianjiake.android.util;

/**
 * Created by lfs on 16/10/31.
 */

public class FloatUtil {
    public static Float parseFloat(String floatValue) {
        Float value = 0f;
        try {
            value = Float.parseFloat(floatValue.replace(",",""));
        } catch (Exception e) {
        }
        return value;
    }
}
