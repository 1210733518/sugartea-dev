package com.dianjiake.android.ui.shopweb;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.dianjiake.android.R;
import com.dianjiake.android.base.BaseWebViewActivity;
import com.dianjiake.android.data.bean.HomeShopBean;
import com.dianjiake.android.data.bean.ServiceBean;
import com.dianjiake.android.data.bean.UserInfoBean;
import com.dianjiake.android.data.db.LoginInfoDBHelper;
import com.dianjiake.android.ui.login.LoginChooseActivity;
import com.dianjiake.android.ui.shopdetail.ShopDetailActivity;
import com.dianjiake.android.ui.subscribe.SubscribeActivity;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.view.dialog.ShareDialog;
import com.google.gson.Gson;

import butterknife.OnClick;

/**
 * Created by lfs on 2017/7/17.
 */

public class ShopWebActivity extends BaseWebViewActivity {

    public static final String TYPE_SERVICE = "service";
    public static final String TYPE_STAFF = "staff";
    public static final String KEY_PROVIDE = "KEY_PROVIDE";

    BaseProvider provider;


    public static Intent getServiceDetail(ServiceBean serviceBean) {
        Intent intent = IntentUtil.getIntent(ShopWebActivity.class);
        intent.setType(TYPE_SERVICE);
        intent.putExtra(KEY_PROVIDE, new ServiceProvider(serviceBean));
        return intent;
    }

    public static Intent getStaffDetaill(UserInfoBean userInfoBean) {
        Intent intent = IntentUtil.getIntent(ShopWebActivity.class);
        intent.setType(TYPE_STAFF);
        intent.putExtra(KEY_PROVIDE, new StaffProvider(userInfoBean));
        return intent;
    }

    @Override
    protected void provideIntent(Intent intent) {
        provider = intent.getParcelableExtra(KEY_PROVIDE);
    }

    @Override
    protected String getToolbarTitle() {
        return provider.getTitle();
    }

    @Override
    protected String getUrl() {
        return provider.getUrl();
    }

    @Override
    protected void addView(FrameLayout view) {

    }

    @Override
    @SuppressLint("AddJavascriptInterface")
    protected void initWebView(WebView webView) {
        mToolbarRight.setVisibility(View.VISIBLE);
        mToolbarRight.setImageResource(R.drawable.ic_toolbar_share);
        webView.addJavascriptInterface(new JS(), "storehome");
    }


    class JS {
        @JavascriptInterface
        public void getService(String s) {
            if (LoginInfoDBHelper.newInstance().isLogin()) {
                ServiceBean serviceBean;
                try {
                    serviceBean = new Gson().fromJson(s, ServiceBean.class);
                    startActivity(SubscribeActivity.getStartIntent(serviceBean, null));
                } catch (Exception e) {

                }
            } else {
                startActivity(IntentUtil.getIntent(LoginChooseActivity.class));
            }


        }

        @JavascriptInterface
        public void shop(String s) {
            if (LoginInfoDBHelper.newInstance().isLogin()) {
                HomeShopBean shopEntity;
                try {
                    shopEntity = new Gson().fromJson(s, HomeShopBean.class);
                    startActivity(ShopDetailActivity.getStartIntent(shopEntity.getId()));
                } catch (Exception e) {

                }
            } else {
                startActivity(IntentUtil.getIntent(LoginChooseActivity.class));
            }
        }

        @JavascriptInterface
        public void getStaffService(String staff, String service) {
            if (LoginInfoDBHelper.newInstance().isLogin()) {
                ServiceBean serviceBean;
                UserInfoBean userInfoBean;
                try {
                    userInfoBean = new Gson().fromJson(staff, UserInfoBean.class);
                    serviceBean = new Gson().fromJson(service, ServiceBean.class);
                    startActivity(SubscribeActivity.getStartIntent(serviceBean, userInfoBean));
                } catch (Exception e) {

                }
            } else {
                startActivity(IntentUtil.getIntent(LoginChooseActivity.class));
            }
        }
    }

    @OnClick(R.id.toolbar_icon_right)
    void clickShare(View v) {
        String desc = provider.getShopBean() != null ? provider.getShopBean().getMingcheng() + " · " + provider.getShopBean().getJianjie() : "糖茶,让生活更有滋味！";
        ShareDialog.Show(this, shareTitle, desc, shareUrl, shareImage);
    }

}
