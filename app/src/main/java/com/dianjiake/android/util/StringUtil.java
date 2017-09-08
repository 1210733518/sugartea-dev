package com.dianjiake.android.util;

/**
 * Created by Sacowiw on 2017/6/13.
 */

public class StringUtil {
    public static CharSequence trimTrailingWhitespace(CharSequence source) {

        if (source == null)
            return "";

        int i = source.length();

        // loop back to the first non-whitespace character
        while (--i >= 0 && Character.isWhitespace(source.charAt(i))) {
        }

        return source.subSequence(0, i + 1);
    }
}
