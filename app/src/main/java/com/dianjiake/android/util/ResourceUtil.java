package com.dianjiake.android.util;

import android.support.annotation.StringRes;

import com.dianjiake.android.R;
import com.dianjiake.android.base.App;


/**
 * Created by Sacowiw on 2017/5/17.
 */

public class ResourceUtil {
    public static String getString(@StringRes int stringRes) {
        return App.getInstance().getResources().getString(stringRes);
    }

//    public static int getGenderIcon(String gender) {
//        if ("1".equals(gender)) {
//            return R.drawable.ic_gender_male;
//        } else {
//            return R.drawable.ic_gender_female;
//        }
//    }
}
