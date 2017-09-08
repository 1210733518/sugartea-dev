package com.dianjiake.android.ui.common;

import com.dianjiake.android.util.CheckEmptyUtil;

/**
 * Created by lfs on 2017/7/26.
 */

public class OrderPayType {
    public static String getPayType(String type){
        if(CheckEmptyUtil.isEmpty(type)) return "";
        switch (type){
            case "1":
                return "支付宝";
            case "2":
                return "微信";
            case "3":
                return "余额";
            case "4":
                return "银行卡";
            case "5":
                return "现金";
            case "6":
                return "计次卡";
            case "7":
                return "赊欠";
            default:
                return "默认";
        }
    }
}
