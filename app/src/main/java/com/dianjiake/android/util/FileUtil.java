package com.dianjiake.android.util;

import android.os.Environment;


import com.dianjiake.android.base.App;

import java.io.File;

/**
 * Created by lfs on 2017/6/20.
 */

public class FileUtil {

    private static File getExternalFilesDir(String type) {
        return App.getInstance().getExternalFilesDir(type);
    }

    public static File getExternalPicturesDir() {
        return getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }
}
