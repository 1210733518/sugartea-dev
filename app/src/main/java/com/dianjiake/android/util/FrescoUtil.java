package com.dianjiake.android.util;

import android.net.Uri;
import android.text.TextUtils;

import com.dianjiake.android.base.App;
import com.dianjiake.android.constant.Constant;


/**
 * Created by lfs on 2017/6/2.
 */

public class FrescoUtil {
    public static Uri getResImage(int imgRes) {
        return Uri.parse("res://" + App.getInstance().getPackageName() + "/" + imgRes);
    }

    public static Uri getAvatarUri(String avatar) {
        return Uri.parse(Constant.IMAGE_AVATAR + avatar);
    }

    public static Uri getShopLogoUri(String logo, String cover) {
        if (!TextUtils.isEmpty(logo)) {
            return Uri.parse(Constant.IMAGE_SHOP + logo);
        } else {
            return Uri.parse(Constant.IMAGE_SHOP + cover);
        }
    }

    public static Uri getShopCoverUri(String logo, String cover) {
        if (!TextUtils.isEmpty(cover)) {
            return Uri.parse(Constant.IMAGE_SHOP + cover);
        } else {
            return Uri.parse(Constant.IMAGE_SHOP + logo);
        }
    }

    public static Uri getOccupationAvatar(String oa) {
        return Uri.parse(Constant.IMAGE_PRO_PHOTO + oa);
    }

    public static Uri getServiceUri(String image) {
        return Uri.parse(Constant.IMAGE_SERVICE + image);
    }

    public static Uri getADUri(String adImage) {
        return Uri.parse(Constant.IMAGE_AD + adImage);
    }

    public static Uri getServiceTyoeIcon(String icon) {
        return Uri.parse(Constant.IMAGE_SERVICE_ICON + icon);
    }

    public static Uri getFileUri(String path) {
        return Uri.parse("file://" + path);
    }
}
