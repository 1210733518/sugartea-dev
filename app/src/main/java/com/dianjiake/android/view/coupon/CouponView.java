package com.dianjiake.android.view.coupon;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.data.bean.CouponBean;
import com.dianjiake.android.data.bean.HomeShopBean;
import com.dianjiake.android.util.DateUtil;
import com.dianjiake.android.util.FrescoUtil;
import com.dianjiake.android.util.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

/**
 * Created by lfs on 2017/7/24.
 */

public class CouponView extends ConstraintLayout {

    private final int TYPE_GET = 2;
    private final int TYPE_LIST = 0;

    @BindView(R.id.gradient_bg)
    FrameLayout gradientBg;
    @BindView(R.id.wave_bg)
    FrameLayout waveBg;
    @BindView(R.id.logo)
    SimpleDraweeView logo;
    @BindView(R.id.corner_tip)
    SimpleDraweeView cornerTip;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rule)
    TextView rule;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.get)
    Button get;

    CompositeDisposable cd;
    OnGetListener onGetListener;
    CouponBean couponBean;
    int position = -1;
    int type = TYPE_LIST;

    public CouponView(Context context) {
        super(context);
        init();
    }

    public CouponView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CouponView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        View view = inflate(getContext(), R.layout.coupon_view, this);
        ButterKnife.bind(this, view);
        cd = new CompositeDisposable();
    }

    public void setCoupon(CouponBean coupon, HomeShopBean shopBean, int position) {

        type = TYPE_GET;
        this.position = position;
        setCoupon(coupon, shopBean);
    }

    public void setCoupon(CouponBean coupon, HomeShopBean shopBean) {
        if (shopBean != null) {
            logo.setImageURI(FrescoUtil.getShopLogoUri(shopBean.getLogo(), shopBean.getCover()));
        }
        this.couponBean = coupon;
        title.setText(coupon.getKaquanmingcheng());
        String ruleString = "使用规则：";
        switch (coupon.getLeixing()) {
            case CouponType.CARD_TYPE_NORMAL:
                gradientBg.setBackgroundResource(R.drawable.item_coupon_bg_orange);
                cornerTip.setImageURI(FrescoUtil.getResImage(R.drawable.ic_coupon_normal));
                ruleString += CouponType.CARD_RULE_NORMAL;
                break;
            case CouponType.CARD_TYPE_LESS:
                ruleString += CouponType.CARD_RULE_LESS;
                gradientBg.setBackgroundResource(R.drawable.item_coupon_bg_purple);
                cornerTip.setImageURI(FrescoUtil.getResImage(R.drawable.ic_coupon_less));
                break;
            case CouponType.CARD_TYPE_EXPERIENCE:
                ruleString += CouponType.CARD_RULE_EXPERIENCE;
                gradientBg.setBackgroundResource(R.drawable.item_coupon_bg_green);
                cornerTip.setImageURI(FrescoUtil.getResImage(R.drawable.ic_coupon_experience));
                break;
            case CouponType.CARD_TYPE_GIFT:
                ruleString += CouponType.CARD_RULE_GIFT;
                gradientBg.setBackgroundResource(R.drawable.item_coupon_bg_yellow);
                cornerTip.setImageURI(FrescoUtil.getResImage(R.drawable.ic_coupon_gift));
                break;
        }
        rule.setText(ruleString);
        if (type == TYPE_LIST || "1".equals(coupon.getYouxiaoqileixing())) {
            time.setText("使用期限："
                    + DateUtil.formatYMD(coupon.getKaishishijian())
                    + " ~ "
                    + DateUtil.formatYMD(coupon.getJieshushijian())
            );

        } else {

            time.setText("使用期限："
                    + ("0".equals(coupon.getKaishishijian()) ? "当天生效" : "领取后" + coupon.getKaishishijian() + "天后生效"));
        }
        if(type == TYPE_LIST){
            get.setText("立即使用");
            get.setClickable(false);
        }else{
            get.setText("领取");
            get.setClickable(true);
        }
    }

    public void setOnGetListener(OnGetListener onGetListener) {
        this.onGetListener = onGetListener;
    }

    @OnClick(R.id.get)
    void getCoupon(View v) {
        if (onGetListener != null && couponBean != null) {
            onGetListener.onGet(couponBean, position);
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public static interface OnGetListener {
        void onGet(CouponBean coupon, int position);
    }
}
