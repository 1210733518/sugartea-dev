package com.dianjiake.android.ui.msg;

/**
 * 消息类型
 * Created by lfs on 2017/7/26.
 */

public interface MsgType {
    /**
     * 订单被确认
     */
    String ORDER_CONFIRM = "1";
    /**
     * 订单被取消
     */
    String ORDER_CANCEL = "2";
    /**
     * 订单完成
     */
    String ORDER_COMPLETE = "3";
    /**
     * 商家发放优惠券
     */
    String COUPON = "4";
    /**
     * 储值卡信息
     */
    String MONEY = "5";
    /**
     * 开卡信息
     */
    String NEW_CARD = "6";
}
