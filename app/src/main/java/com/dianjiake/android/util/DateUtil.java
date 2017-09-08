package com.dianjiake.android.util;

import android.text.format.DateFormat;

import java.util.Calendar;

/**
 * Created by lfs on 16/11/29.
 */

public class DateUtil {

    public static int getAge(String timestamp) {
        return getAge(LongUtil.parseLong(timestamp));
    }

    /**
     * 计算年龄
     *
     * @param timestamp
     * @return
     */
    public static int getAge(long timestamp) {
        if (timestamp == 0) return 0;
        Calendar now = Calendar.getInstance();
        Calendar result = Calendar.getInstance();
        if (timestamp < 1e11) {
            timestamp *= 1e3;
        }
        result.setTimeInMillis(timestamp);
        int year1 = now.get(Calendar.YEAR);
        int year2 = result.get(Calendar.YEAR);
        int age = year1 - year2;
        int month1 = now.get(Calendar.MONTH);
        int month2 = result.get(Calendar.MONTH);
        if (month2 > month1) {
            age--;
        } else if (month1 == month2) {
            int day1 = now.get(Calendar.DAY_OF_MONTH);
            int day2 = result.get(Calendar.DAY_OF_MONTH);
            if (day2 > day1) {
                age--;
            }
        }
        return age;
    }

    /**
     * yyyy-MM-dd HH:mm
     *
     * @param timestamp
     * @return
     */
    public static String formatHM(long timestamp) {
        if (timestamp < 1e11) {
            timestamp *= 1e3;
        }
        return DateFormat.format("yyyy-MM-dd HH:mm", timestamp).toString();

    }

    public static String formatHM(String timestamp) {
        return formatHM(LongUtil.parseLong(timestamp));

    }

    /**
     * HH:mm
     *
     * @param timestamp
     * @return
     */
    public static String formatOnlyHM(long timestamp) {
        if (timestamp < 1e11) {
            timestamp *= 1000L;
        }
        return DateFormat.format("HH:mm", timestamp).toString();
    }

    public static String formatOnlyHM(String timestamp) {
        return formatOnlyHM(LongUtil.parseLong(timestamp));
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp
     * @return
     */
    public static String formatHMS(long timestamp) {
        if (timestamp < 1e11) {
            timestamp *= 1e3;
        }
        return DateFormat.format("yyyy-MM-dd HH:mm:ss", timestamp).toString();

    }

    /**
     * yyyy-MM-dd
     *
     * @param timestamp
     * @return
     */
    public static String formatYMD(long timestamp) {
        if (timestamp < 1e11) {
            timestamp *= 1e3;
        }
        return DateFormat.format("yyyy-MM-dd", timestamp).toString();

    }

    public static String formatYMD(String timestamp) {
        return formatYMD(LongUtil.parseLong(timestamp));
    }

    /**
     * @param timestamp
     * @return
     */
    public static String formatMDHM(long timestamp) {
        if (timestamp < 1e11) {
            timestamp *= 1e3;
        }
        return DateFormat.format("MM-dd HH:mm", timestamp).toString();

    }

    public static String formatMDHM(String timestamp) {

        return formatMDHM(LongUtil.parseLong(timestamp));

    }

    /**
     * @param timestamp
     * @return
     */
    public static String formatMD(long timestamp) {
        if (timestamp < 1e11) {
            timestamp *= 1e3;
        }
        return DateFormat.format("MM-dd", timestamp).toString();

    }

    public static String formatMD(String timestamp) {
        return formatMD(LongUtil.parseLong(timestamp));
    }

    /**
     * 2016-12-23 12:23
     *
     * @param timestamp
     * @return
     */
    public static String formatYMDHM(long timestamp) {
        if (timestamp < 1e11) {
            timestamp *= 1e3;
        }
        return DateFormat.format("yyyy-MM-dd HH:mm", timestamp).toString();

    }

    public static String formatYMDHM(String timestamp) {

        return formatYMDHM(LongUtil.parseLong(timestamp));

    }
}
