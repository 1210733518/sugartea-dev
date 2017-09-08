package com.dianjiake.android.api;

import android.support.annotation.NonNull;

import com.dianjiake.android.constant.Constant;
import com.dianjiake.android.data.bean.ADBean;
import com.dianjiake.android.data.bean.AddOrderBean;
import com.dianjiake.android.data.bean.BaseBean;
import com.dianjiake.android.data.bean.BaseListBean;
import com.dianjiake.android.data.bean.BaseUnrealBean;
import com.dianjiake.android.data.bean.CouponBean;
import com.dianjiake.android.data.bean.HomeShopBean;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.data.bean.LoginBean;
import com.dianjiake.android.data.model.MsgModel;
import com.dianjiake.android.data.bean.MyCouponBean;
import com.dianjiake.android.data.bean.OccupationBean;
import com.dianjiake.android.data.bean.OrderBean;
import com.dianjiake.android.data.bean.ServiceBean;
import com.dianjiake.android.data.bean.ServiceTypeBean;
import com.dianjiake.android.data.bean.ShopCommentBean;
import com.dianjiake.android.data.bean.ShopDetailBean;
import com.dianjiake.android.data.bean.UserInfoBean;
import com.dianjiake.android.data.bean.VipBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;


/**
 * Created by lfs on 2017/6/6.
 */

public interface Api {

    /**
     * 首页店铺列表
     *
     * @param bs         {@link BSConstant#SHOP_LIST}
     * @param openid
     * @param order      （0默认 2距离 1评分）
     * @param coordinate
     * @param page
     * @return
     */
    @GET(Constant.SHOP)
    Observable<BaseListBean<HomeShopBean>> homeShop(
            @Query("bs") String bs,
            @Query("openid") String openid,
            @Query("paixu") int order,
            @Query("zuobiao") String coordinate,
            @Query("page") int page
    );

    /**
     * @param bs         {@link BSConstant#SEARCH_SHOP}
     * @param openid
     * @param keyword
     * @param order
     * @param coordinate
     * @param page
     * @return
     */
    @GET(Constant.SHOP)
    Observable<BaseListBean<HomeShopBean>> searchShop(
            @Query("bs") String bs,
            @Query("openid") String openid,
            @Query("guanjianci") String keyword,
            @Query("paixu") int order,
            @Query("zuobiao") String coordinate,
            @Query("page") int page
    );

    /**
     * @param bs         {@link BSConstant#SEARCH_SERVICE}
     * @param openid
     * @param keyword
     * @param order
     * @param coordinate
     * @param page
     * @return
     */
    @GET(Constant.SHOP)
    Observable<BaseListBean<ServiceBean>> searchService(
            @Query("bs") String bs,
            @Query("openid") String openid,
            @Query("guanjianci") String keyword,
            @Query("paixu") int order,
            @Query("zuobiao") String coordinate,
            @Query("page") int page
    );

    /**
     * 检索商户
     *
     * @param bs         {@link BSConstant#RETRIEVE_SHOP}
     * @param openid
     * @param typeId
     * @param order
     * @param coordinate
     * @param page
     * @return
     */
    @GET(Constant.SHOP)
    Observable<BaseListBean<HomeShopBean>> retrieveShop(
            @Query("bs") String bs,
            @Query("openid") String openid,
            @Query("erjileibie") String typeId,
            @Query("paixu") int order,
            @Query("zuobiao") String coordinate,
            @Query("page") int page
    );

    /**
     * 检索服务
     *
     * @param bs         {@link BSConstant#RETRIEVE_SERVICE}
     * @param openid
     * @param typeId
     * @param order
     * @param coordinate
     * @param page
     * @return
     */
    @GET(Constant.SHOP)
    Observable<BaseListBean<ServiceBean>> retrieveService(
            @Query("bs") String bs,
            @Query("openid") String openid,
            @Query("erjileibie") String typeId,
            @Query("paixu") int order,
            @Query("zuobiao") String coordinate,
            @Query("page") int page
    );


    @GET(Constant.SHOP)
    Observable<BaseBean<ShopDetailBean>> shopDetail(
            @Query("bs") String bs,
            @Query("openid") String openid,
            @Query("shanghuid") String shopid,
            @Query("zuobiao") String coordinate
    );

    /**
     * 服务列表
     *
     * @param bs     {@link BSConstant#SERVICE_LIST}
     * @param shopid
     * @param page
     * @return
     */
    @GET(Constant.SHOP)
    Observable<BaseListBean<ServiceBean>> serviceList(
            @Query("bs") String bs,
            @Query("shanghuid") String shopid,
            @Query("page") int page
    );

    /**
     * 商户员工列表
     *
     * @param bs     {@link BSConstant#SHOP_STAFF}
     * @param shopid
     * @param page
     * @return
     */
    @GET(Constant.SHOP)
    Observable<BaseListBean<UserInfoBean>> shopStaff(
            @Query("bs") String bs,
            @Query("shanghuid") String shopid,
            @Query("page") int page
    );

    /**
     * 商户评论列表
     *
     * @param bs     {@link BSConstant#SHOP_STAFF}
     * @param shopid
     * @param page
     * @return
     */
    @GET(Constant.SHOP)
    Observable<BaseListBean<ShopCommentBean>> shopComment(
            @Query("bs") String bs,
            @Query("shanghuid") String shopid,
            @Query("page") int page
    );

    /**
     * 服务员工列表
     *
     * @param bs     {@link BSConstant#SHOP_STAFF}
     * @param shopid
     * @return
     */
    @GET(Constant.SHOP)
    Observable<BaseListBean<UserInfoBean>> serviceStaff(
            @Query("bs") String bs,
            @Query("shanghuid") String shopid,
            @Query("fuwuid") String serviceId,
            @Query("page") int page
    );
    @GET(Constant.SHOP)
    Observable<BaseListBean<UserInfoBean>> serviceStaff(
            @Query("bs") String bs,
            @Query("shanghuid") String shopid
    );


    /**
     * 员工可提供的服务列表
     *
     * @param bs     {@link BSConstant#STAFF_SERVICE}
     * @param shopid
     * @param page
     * @return
     */
    @GET(Constant.SHOP)
    Observable<BaseListBean<ServiceBean>> staffService(
            @Query("bs") String bs,
            @Query("shanghuid") String shopid,
            @Query("openid") String openId,
            @Query("page") int page
    );


    /**
     * 下单
     *
     * @param bs           {@link BSConstant#ADD_SERVICE}
     * @param openid
     * @param vipName
     * @param vipPhone
     * @param timestamp
     * @param servicesSize
     * @param serviceType
     * @param from
     * @param gender
     * @param location
     * @param shopid
     * @param services
     * @return
     */
    @FormUrlEncoded
    @POST(Constant.ORDERS)
    Observable<BaseBean<AddOrderBean>> addService(
            @Field("bs") String bs,
            @Field("openid") String openid,
            @Field("name") String vipName,
            @Field("phone") String vipPhone,
            @Field("fuwushijian") long timestamp,
            @Field("shuliang") int servicesSize,
            @Field("fuwumoshi") int serviceType,
            @Field("laiyuan") int from,
            @Field("sex") int gender,
            @Field("dizhi") String location,
            @Field("shanghuid") String shopid,
            @Field("fuwuxiangmu") String services
    );


    /**
     * 登录
     *
     * @param bs
     * @param phone
     * @param sms
     * @param wxOpenId
     * @param unionId
     * @param cid
     * @return
     */
    @FormUrlEncoded
    @POST(Constant.USER)
    Observable<BaseBean<LoginBean>> login(
            @Field("bs") String bs,
            @Field("phone") String phone,
            @Field("sms") String sms,
            @Field("wxopenid") String wxOpenId,
            @Field("unionid") String unionId,
            @Field("cid") String cid,
            @Field("wxname") String wxName,
            @Field("wxtouxiang") String wxAvatar
    );

    /**
     * 获得验证码
     *
     * @param bs    {@link BSConstant#VERIFY_CODE}
     * @param phone
     * @param ip
     * @param mac
     * @param time
     * @param sign
     * @param type
     * @return
     */
    @GET(Constant.USER)
    Observable<BaseBean> getVerifycode(
            @NonNull @Query("bs") String bs,
            @NonNull @Query("phone") String phone,
            @NonNull @Query("ip") String ip,
            @NonNull @Query("mac") String mac,
            @NonNull @Query("time") long time,
            @NonNull @Query("sign") String sign,
            @NonNull @Query("leixing") String type

    );

    /**
     * 获取用户信息
     *
     * @param bs
     * @param openId
     * @param phone
     * @return
     */
    @GET(Constant.USER)
    Observable<BaseBean<LoginBean>> getUserInfo(
            @NonNull @Query("bs") String bs,
            @NonNull @Query("openid") String openId,
            @NonNull @Query("phone") String phone

    );

    /**
     * 职业列表
     *
     * @param bs
     * @return
     */
    @GET(Constant.USER)
    Observable<BaseListBean<OccupationBean>> occupationList(
            @NonNull @Query("bs") String bs
    );

    /**
     * 添加用户
     *
     * @param parts
     * @return
     */
    @Multipart
    @POST(Constant.USER)
    Observable<BaseBean> editUserInfo(@PartMap Map<String, RequestBody> parts);

    /**
     * 首页广告
     *
     * @param bs
     * @param lonlat
     * @return
     */
    @GET(Constant.USER)
    Observable<BaseBean<ADBean>> ad(
            @NonNull @Query("bs") String bs,
            @NonNull @Query("zuobiao") String lonlat
    );

    /**
     * {@link BSConstant#ORDER_LIST}
     *
     * @param bs
     * @return
     */
    @GET(Constant.ORDERS)
    Observable<BaseListBean<OrderBean>> orders(
            @NonNull @Query("bs") String bs,
            @NonNull @Query("openid") String openid,
            @NonNull @Query("page") int page
    );

    /**
     * 退出登录
     *
     * @param bs
     * @param openid
     * @return
     */
    @GET(Constant.USER)
    Observable<BaseBean> logout(
            @NonNull @Query("bs") String bs,
            @NonNull @Query("openid") String openid
    );

    /**
     * 取消订单
     *
     * @param bs       {@link BSConstant#ORDER_CANCEL}
     * @param openid
     * @param ordernum
     * @return
     */
    @GET(Constant.ORDERS)
    Observable<BaseBean> orderCancel(
            @NonNull @Query("bs") String bs,
            @NonNull @Query("openid") String openid,
            @NonNull @Query("ordernum") String ordernum

    );


    @Multipart
    @POST(Constant.ORDERS)
    Observable<BaseBean> evaluateOrder(
            @PartMap Map<String, RequestBody> parts);

    /**
     * @param bs     {@link BSConstant#ORDER_LIST}
     * @param openid
     * @param shopId
     * @return
     */
    @GET(Constant.SHOP)
    Observable<BaseListBean<CouponBean>> shopCoupon(
            @NonNull @Query("bs") String bs,
            @NonNull @Query("openid") String openid,
            @NonNull @Query("shanghuid") String shopId);

    /**
     * 会员卡列表
     *
     * @param bs     {@link BSConstant#VIP_LIST}
     * @param openid
     * @return
     */
    @GET(Constant.USER)
    Observable<BaseListBean<VipBean>> vipList(
            @NonNull @Query("bs") String bs,
            @NonNull @Query("openid") String openid);

    /**
     * 我的收藏列表
     *
     * @param bs     {@link BSConstant#MY_COLLECTION}
     * @param openid
     * @return
     */
    @GET(Constant.SHOP)
    Observable<BaseListBean<HomeShopBean>> myCollection(
            @NonNull @Query("bs") String bs,
            @NonNull @Query("openid") String openid,
            @Query("zuobiao") String coordinate);


    /**
     * 取消收藏
     *
     * @param bs     {@link BSConstant#DELETE_COLLECTION}
     * @param openid
     * @return
     */
    @GET(Constant.SHOP)
    Observable<BaseBean> deleteCollection(
            @NonNull @Query("bs") String bs,
            @NonNull @Query("openid") String openid,
            @NonNull @Query("shanghuid") String shopId);


    /**
     * 收藏
     *
     * @param bs     {@link BSConstant#MY_COLLECTION}
     * @param openid
     * @return
     */
    @GET(Constant.SHOP)
    Observable<BaseBean> collect(
            @NonNull @Query("bs") String bs,
            @NonNull @Query("openid") String openid,
            @NonNull @Query("shanghuid") String shopId);

    /**
     * 我的优惠券
     *
     * @param bs     {@link BSConstant#MY_COUPON}
     * @param openid
     * @return
     */
    @GET(Constant.USER)
    Observable<BaseListBean<MyCouponBean>> myCoupon(
            @NonNull @Query("bs") String bs,
            @NonNull @Query("openid") String openid,
            @NonNull @Query("page") int page);

    /**
     * 服务分类列表
     *
     * @param bs {@link BSConstant#SERVICE_TYPE}
     * @return
     */
    @GET(Constant.SHOP)
    Observable<BaseBean<ServiceTypeBean>> serviceType(
            @NonNull @Query("bs") String bs);

    /**
     * 首页收藏推荐
     *
     * @param bs {@link BSConstant#HOME_COLLECTION}
     * @return
     */
    @GET(Constant.SHOP)
    Observable<BaseListBean<HomeShopBean>> homeCollection(
            @NonNull @Query("bs") String bs,
            @NonNull @Query("openid") String openId
    );

    /**
     * 领取优惠券
     *
     * @param bs           {@link BSConstant#GET_COUPON}
     * @param openId
     * @param couponId
     * @param shareType
     * @param sharedOpenId
     * @return
     */
    @GET(Constant.USER)
    Observable<BaseBean> getCoupon(
            @NonNull @Query("bs") String bs,
            @NonNull @Query("openid") String openId,
            @NonNull @Query("kaquanid") String couponId,
            @NonNull @Query("fenxiangleixing") String shareType,
            @NonNull @Query("fenxiangopenid") String sharedOpenId);


    /**
     * 订单详情
     *
     * @param bs      {@link BSConstant#ORDER_DETAIL}
     * @param shopId
     * @param orderId
     * @return
     */
    @GET(Constant.ORDERS)
    Observable<BaseUnrealBean<OrderBean>> orderDetail(
            @NonNull @Query("bs") String bs,
            @NonNull @Query("shanghuid") String shopId,
            @NonNull @Query("id") String orderId
    );

    /**
     * 消息列表
     *
     * @param bs     {@link BSConstant#MSG_LIST}
     * @param openId
     * @param page
     * @return
     */
    @GET(Constant.USER)
    Observable<BaseListBean<MsgModel>> msgList(
            @NonNull @Query("bs") String bs,
            @NonNull @Query("openid") String openId,
            @NonNull @Query("page") int page
    );

    /**
     * 标记信息为已读
     *
     * @param bs     {@link BSConstant#MARK_MSG}
     * @param openId
     * @param msgId
     * @return
     */
    @GET(Constant.USER)
    Observable<BaseBean> readMsg(
            @NonNull @Query("bs") String bs,
            @NonNull @Query("openid") String openId,
            @NonNull @Query("id") String msgId
    );

    /**
     * 修改手机号
     *
     * @param bs     {@link BSConstant#CHANGE_PHONE}
     * @param openId
     * @param phone
     * @param sms
     * @return
     */
    @GET(Constant.USER)
    Observable<BaseBean> changePhone(
            @NonNull @Query("bs") String bs,
            @NonNull @Query("openid") String openId,
            @NonNull @Query("phone") String phone,
            @NonNull @Query("sms") String sms
    );
}
