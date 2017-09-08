package com.dianjiake.android.ui.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.api.Network;
import com.dianjiake.android.base.App;
import com.dianjiake.android.base.BasePresenter;
import com.dianjiake.android.base.BaseTranslateActivity;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.constant.Constant;
import com.dianjiake.android.data.bean.BaseBean;
import com.dianjiake.android.data.bean.LoginBean;
import com.dianjiake.android.data.bean.UserInfoBean;
import com.dianjiake.android.data.db.LoginInfoDBHelper;
import com.dianjiake.android.util.AppUtil;
import com.dianjiake.android.util.ToastUtil;
import com.dianjiake.android.util.UIUtil;
import com.dianjiake.android.view.dialog.NormalProgressDialog;
import com.dianjiake.android.view.widget.ToolbarSpaceView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * 选择登录方式页
 * Created by lfs on 17/2/15.
 */

public class LoginChooseActivity extends BaseTranslateActivity {
    public static LoginChooseActivity sInstance;

    public static final String PLATFORM_QQ = "QQ";
    public static final String PLATFORM_WX = "Wechat";

    @BindView(R.id.toolbar_space)
    ToolbarSpaceView toolbarSpace;
    @BindView(R.id.toolbar_icon_left)
    ImageView toolbarIconLeft;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_icon_right)
    ImageView toolbarIconRight;
    @BindView(R.id.toolbar_holder)
    ConstraintLayout toolbarHolder;
    @BindView(R.id.login_choose_wx)
    Button loginChooseWx;
    @BindView(R.id.login_choose_qq)
    Button loginChooseQq;
    @BindView(R.id.login_choose_phone)
    Button loginChoosePhone;
    @BindView(R.id.toolbar_divider)
    View toolbarDivider;

    NormalProgressDialog mAuthorizePD;
    LoginInfoDBHelper loginInfoDBHelper;


    @Override
    public int provideContentView() {
        return R.layout.activity_login_choose;
    }

    @Override
    public void create(@Nullable Bundle savedInstanceState) {
        sInstance = this;
        overridePendingTransition(R.anim.le_slide_in_from_bottom, R.anim.anim_none);

        toolbarIconLeft.setImageResource(R.drawable.ic_toolbar_close);
        toolbarTitle.setText("登录糖茶");
        toolbarDivider.setVisibility(View.GONE);
        mAuthorizePD = NormalProgressDialog.newInstance("");
        mAuthorizePD.setCancelable(true);
        UIUtil.setVisibility(loginChooseWx, AppUtil.isAppInstalled(Constant.WX_PACKAGE_NAME));
        loginChooseQq.setVisibility(View.GONE);
        loginInfoDBHelper = LoginInfoDBHelper.newInstance();
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @OnClick(R.id.toolbar_icon_left)
    void pressClose(View v) {
        onBackPressed();
    }

    @OnClick(R.id.login_choose_wx)
    void pressWx(View v) {
        showAuthorizePD("正在获取微信授权...");
        loginByWx();
    }

    @OnClick(R.id.login_choose_qq)
    void pressQQ(View v) {
        showAuthorizePD("正在获取QQ授权...");

    }

    @OnClick(R.id.login_choose_phone)
    void pressPhone(View v) {
        startActivity(LoginPhoneActivity.getSignIntent());
    }


    void showAuthorizePD(String str) {
        mAuthorizePD.setMessage(str);
        if (!mAuthorizePD.isAdded()) {
            mAuthorizePD.show(getFragmentManager(), "str");
        }
    }

    void dismissAuthorizePD() {
        if (mAuthorizePD != null) {
            mAuthorizePD.dismissAllowingStateLoss();
        }
    }

    void loginByWx() {
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI api = UMShareAPI.get(App.getInstance());
        api.setShareConfig(config);
        api.getPlatformInfo(this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((ProgressDialog) mAuthorizePD.getDialog()).setMessage("正在登录，请稍候...");
                    }
                });
                login(map.get("openid"), map.get("unionid"), map.get("nickname"), map.get("headimgurl"));
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                ToastUtil.showShortToast("获取授权失败，请稍候再试");
                dismissAuthorizePD();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                ToastUtil.showShortToast("取消授权");
                dismissAuthorizePD();
            }
        });
    }


    void login(final String wxOpenId, final String unionId, final String wxName, final String wxAvatar) {
        showAuthorizePD("正在登录");
        Network.getInstance().login(BSConstant.LOGIN,
                null,
                null,
                wxOpenId,
                unionId,
                null,
                wxName,
                wxAvatar).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new Observer<BaseBean<LoginBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseBean<LoginBean> userInfo) {
                        dismissAuthorizePD();
                        if (userInfo == null) {
                            ToastUtil.showShortToast("登录失败");
                            return;
                        }

                        switch (userInfo.getCode()) {
                            case 200:
                            case 203:
                            case 204:
                                ToastUtil.showShortToast("登录成功");
                                saveLoginUserInfo(userInfo.getObj().getUser());
                                LoginActivitiesManger.newInstance().phoneLoginSuccess();
                                break;
                            case 202:
                                startActivity(LoginPhoneActivity.getBindIntent(wxOpenId, unionId,wxAvatar,wxName));
                                break;
                            case 201://绑定失败，这用不上
                                ToastUtil.showShortToast("登录失败");
                                break;
                            case 4003://验证码或手机号错误
                                ToastUtil.showShortToast("登录失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dismissAuthorizePD();
                        ToastUtil.showShortToast("登录失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void saveLoginUserInfo(UserInfoBean userInfoBean) {
        if (loginInfoDBHelper != null) {
            loginInfoDBHelper.saveLoginInfo(userInfoBean);
        }
    }

    @Override
    public void finish() {
        super.finish();
        sInstance = null;
        overridePendingTransition(R.anim.anim_none, R.anim.le_slide_out_to_bottom);
    }


}
