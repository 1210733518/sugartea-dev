package com.dianjiake.android.ui.common;

import com.dianjiake.android.constant.Constant;

/**
 * Created by lfs on 2017/7/20.
 */

public class OrderStatus {
    public static final int NO_CONFIRM = 0;
    public static final int CONFIRM = 1;
    public static final int COMPLETE = 2;
    public static final int CANCEL = 3;
    public static final int RUNNING = 4;

    public static int getStatus(String status) {
        switch (status) {
            case "0":
            case "1":
                return NO_CONFIRM;
            case "2":
                return CONFIRM;
            case "3":
                return COMPLETE;
            case "4":
            case "5":
                return CANCEL;
            default:
                return RUNNING;
        }
    }

    public static String getStatusText(String status) {
        switch (status) {
            case "0":
            case "1":
                return "等待商家确认";
            case "2":
                return "商家已确认";
            case "3":
                return "已完成";
            case "4":
            case "5":
                return "已取消";
            default:
                return "商家已确认";
        }
    }

    public static String getStatusDescText(String status) {
        switch (status) {
            case "0":
            case "1":
                return "商家若长时间未联系你，你可以与商家联系";
            case "2":
                return "商家已确认预约，请按照约定时间准时到店";
            case "3":
                return "感谢你对商家的信任，期待再次光临";
            case "4":
            case "5":
                return "如果对订单有疑惑，可以拨打客服热线或联系商家";
            default:
                return "商家已确认，商家已确认预约，请按照约定时间准时到店";
        }
    }
}
