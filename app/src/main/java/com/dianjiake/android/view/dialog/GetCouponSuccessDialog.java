package com.dianjiake.android.view.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.constant.Constant;
import com.dianjiake.android.data.bean.CouponBean;
import com.dianjiake.android.data.bean.HomeShopBean;
import com.dianjiake.android.data.db.LoginInfoDBHelper;
import com.dianjiake.android.data.model.LoginInfoModel;
import com.dianjiake.android.util.FrescoUtil;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.util.ToastUtil;
import com.dianjiake.android.util.UIUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lfs on 2017/7/26.
 */

public class GetCouponSuccessDialog extends AppCompatActivity implements UMShareListener {

    @BindView(R.id.bg)
    SimpleDraweeView bg;
    @BindView(R.id.close)
    ImageView close;
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.share_bg)
    View shareBg;
    @BindView(R.id.share_wx)
    LinearLayout shareWx;
    @BindView(R.id.share_qq)
    LinearLayout shareQq;

    CouponBean couponBean;
    HomeShopBean shopBean;
    LoginInfoModel loginInfo;

    String url, title, desc, imgUrl;

    public static Intent getStartIntent(CouponBean couponBean, HomeShopBean shopBean) {
        Intent intent = IntentUtil.getIntent(GetCouponSuccessDialog.class);
        intent.putExtra("coupon", couponBean);
        intent.putExtra("shop", shopBean);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        setContentView(R.layout.dialog_get_coupon_success);
        ButterKnife.bind(this);
        loginInfo = LoginInfoDBHelper.newInstance().getLoginInfo();
        couponBean = getIntent().getParcelableExtra("coupon");
        shopBean = getIntent().getParcelableExtra("shop");
        if (couponBean != null && shopBean != null) {
            title = loginInfo.getNickname() + "送您一张优惠券";
            desc = shopBean.getMingcheng()+" · " + couponBean.getKaquanmingcheng();
            imgUrl = FrescoUtil.getShopLogoUri(shopBean.getLogo(), shopBean.getCover()).toString();
            url = String.format(Constant.SHARE_COUPON, couponBean.getId(), shopBean.getId());
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        int width = (int) (UIUtil.getScreenWidth() * 0.7);
        getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.close)
    void clickClose(View v) {
        finish();
    }

    @OnClick(R.id.share_wx)
    void clickWx(View v) {
        startShare(SHARE_MEDIA.WEIXIN);
    }

    @OnClick(R.id.share_qq)
    void clickQQ(View v) {
        startShare(SHARE_MEDIA.QQ);
    }

    private void startShare(SHARE_MEDIA shareMedia) {
        UMWeb umWeb = new UMWeb(url);
        umWeb.setTitle(title);
        umWeb.setDescription(desc);
        umWeb.setThumb(new UMImage(this, imgUrl));

        new ShareAction(this)
                .setPlatform(shareMedia)
                .withText(title)
                .withMedia(umWeb)
                .setCallback(this)
                .share();
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        ToastUtil.showShortToast("分享成功");

    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        ToastUtil.showShortToast("分享失败");

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        ToastUtil.showShortToast("取消分享");
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }
}
