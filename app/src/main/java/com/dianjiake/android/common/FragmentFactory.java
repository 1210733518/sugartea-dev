package com.dianjiake.android.common;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by lfs on 2017/5/19.
 */

public class FragmentFactory {

    public static <T extends Fragment> T createFragment(Class<T> clz, @Nullable Bundle b) {
        T f = null;
        try {
            f = (T) Class.forName(clz.getName()).newInstance();
            if (b != null) {
                f.setArguments(b);
            }
        } catch (Exception e) {

        }
        return f;
    }

    public static <T extends Fragment> T createFragment(Class<T> clz) {
        return createFragment(clz, null);
    }

    public static <T extends Fragment> T createFragmentByFM(Class<T> clz, FragmentManager fm) {
        T t = (T) fm.findFragmentByTag(clz.getName());
        if (t == null) {
            return createFragment(clz, null);
        } else {
            return t;
        }

    }


    public static <T extends Fragment> T createFragmentByFM(Class<T> clz, FragmentManager fm, Bundle bundle) {
        T t = (T) fm.findFragmentByTag(clz.getName());
        if (t == null) {
            return createFragment(clz, bundle);
        } else {
            return t;
        }

    }

}
