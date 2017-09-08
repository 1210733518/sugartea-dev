package com.dianjiake.android.view.coupon;

/**
 * Created by lfs on 2017/7/24.
 */

public interface CouponType {
    /**
     * 卡券通用
     */
    String CARD_TYPE_NORMAL = "1";
    String CARD_RULE_NORMAL = "无限制，任何服务均可使用";
    /**
     * 卡券满减
     */
    String CARD_TYPE_LESS = "2";
    String CARD_RULE_LESS = "消费满既定额度，即可享受一定的优惠";
    /**
     * 卡券体验
     */
    String CARD_TYPE_EXPERIENCE = "3";
    String CARD_RULE_EXPERIENCE = "指定服务项目体验券";

    /**
     * 卡券礼品
     */
    String CARD_TYPE_GIFT = "4";
    String CARD_RULE_GIFT = "到点消费即有礼品赠送";
}
