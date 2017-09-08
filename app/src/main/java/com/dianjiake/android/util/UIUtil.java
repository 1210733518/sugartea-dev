package com.dianjiake.android.util;

import android.content.Context;
import android.os.Build;
import android.support.annotation.DimenRes;
import android.support.annotation.LayoutRes;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;


import com.dianjiake.android.base.App;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by lfs on 2017/5/12.
 */

public class UIUtil {
    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return 0;
        }
        try {
            Class c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getDimensionPixelSize(x);
        } catch (Exception e) {

        }
        return 0;
    }
    public static int mGetStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height","dimen","android");
        if(resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * 设置状态栏图标为深色和魅族特定的文字风格，Flyme4.0以上
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean flymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean miuiSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    public static int getDimensionPixelSize(@DimenRes int dp) {
        return App.getInstance().getResources().getDimensionPixelSize(dp);
    }

    public static float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, App.getInstance().getResources().getDisplayMetrics());

    }

    public static View inflate(@LayoutRes int layout, Context context) {
        return LayoutInflater.from(context).inflate(layout, null);
    }

    public static View inflate(@LayoutRes int layout, ViewGroup viewGroup) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
    }

    public static int getScreenWidth() {
        return App.getInstance().getResources().getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return App.getInstance().getResources().getSystem().getDisplayMetrics().heightPixels;
    }

    public static int getColor(int colorRes) {
        return App.getInstance().getResources().getColor(colorRes);
    }


    /**
     * 设置view是否可见
     *
     * @param view    {@link View}
     * @param visible
     */
    public static void setVisibility(View view, boolean visible) {
        if (view != null) {
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    public static void setUpIndicatorWidth(TabLayout tabLayout) {
        float marginLeft = 24;
        Class<?> tabLayoutClass = tabLayout.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayoutClass.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        LinearLayout layout = null;
        try {
            if (tabStrip != null) {
                layout = (LinearLayout) tabStrip.get(tabLayout);
            }
            for (int i = 0; i < layout.getChildCount(); i++) {
                View child = layout.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params.setMarginStart((int) dp2px(marginLeft));
                    params.setMarginEnd((int) dp2px(marginLeft));
                }
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
