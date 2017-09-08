package com.dianjiake.android.util;


import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import com.dianjiake.android.base.App;
import com.dianjiake.android.constant.Constant;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import timber.log.Timber;

/**
 * Created by Sacowiw on 2017/5/17.
 */

public class AppUtil {
    public static String encryptSign(String t, String s, String p, String i) {
        return MD5(t + p + Constant.SALT + i);
    }

    public static String MD5(String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean isMIUIV6() {
        String miuiVersionName = get("ro.miui.ui.version.name");
        Timber.e("MIUI: " + miuiVersionName);
        Timber.e("MIUI ROM : " + Build.MANUFACTURER);
        if ("Xiaomi".equals(Build.MANUFACTURER) && !CheckEmptyUtil.isEmpty(miuiVersionName) && miuiVersionName.length() > 1) {
            int version = IntegerUtil.parseInt(miuiVersionName.substring(1));
            if (version > 5) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isFlyme() {
        if ("Flyme".equals(Build.MANUFACTURER)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据给定Key获取值.
     *
     * @return 如果不存在该key则返回空字符串
     */
    public static String get(String key) {
        String ret = "";
        try {
            ClassLoader cl = App.getInstance().getClassLoader();
            @SuppressWarnings("rawtypes")
            Class SystemProperties = cl.loadClass("android.os.SystemProperties");
            @SuppressWarnings("rawtypes")
            Class[] paramTypes = new Class[1];
            paramTypes[0] = String.class;
            Method get = SystemProperties.getMethod("get", paramTypes);

            Object[] params = new Object[1];
            params[0] = new String(key);
            ret = (String) get.invoke(SystemProperties, params);
        } catch (IllegalArgumentException iAE) {
            ret = "";
        } catch (Exception e) {
            ret = "";

        }
        return ret;
    }

    /**
     * 判断app是否安装
     *
     * @return
     */
    public static boolean isAppInstalled(String packageName) {
        PackageManager pm = App.getInstance().getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (Exception x) {
            return false;
        }
    }

}
