package com.dianjiake.android.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.api.Network;
import com.dianjiake.android.base.BasePresenter;
import com.dianjiake.android.base.BaseTranslateActivity;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.custom.MyTextWatcher;
import com.dianjiake.android.data.bean.BaseBean;
import com.dianjiake.android.data.bean.LoginBean;
import com.dianjiake.android.data.bean.UserInfoBean;
import com.dianjiake.android.data.db.AppInfoDBHelper;
import com.dianjiake.android.data.db.LoginInfoDBHelper;
import com.dianjiake.android.data.db.PhoneInfoDBHelper;
import com.dianjiake.android.data.model.AppInfoModel;
import com.dianjiake.android.ui.main.MainActivity;
import com.dianjiake.android.util.AppUtil;
import com.dianjiake.android.util.CheckStringUtil;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.util.ToastUtil;
import com.dianjiake.android.view.dialog.NormalProgressDialog;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by lfs on 17/2/22.
 */

public class LoginPhoneActivity extends BaseTranslateActivity {
    public static LoginPhoneActivity sInstance;

    public static final String TYPE_SIGN = "sign";
    public static final String TYPE_BIND = "bind";
    public static final String TYPE_EDIT = "edit";
    public static final String KEY_OPEN_ID = "open_id";
    public static final String KEY_MODULE = "module";
    public static final String KEY_WXOPNEOD = "wxopen";
    public static final String KEY_WXUNION = "wxunion";
    public static final String KEY_WXNAME = "wxname";
    public static final String KEY_WXAVATAR = "wxavatar";

    @BindView(R.id.toolbar_icon_left)
    ImageView mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_divider)
    View mToolbarDivider;
    @BindView(R.id.phone_ic)
    ImageView mPhoneIc;
    @BindView(R.id.password_ic)
    ImageView mPasswordIc;
    @BindView(R.id.password_button)
    TextView mPasswordButton;
    @BindView(R.id.sign_button)
    Button mSignButton;
    @BindView(R.id.get_audio_ic_button)
    TextView mGetAudioIcButton;
    @BindView(R.id.get_ic_ll)
    LinearLayout mGetIcLl;
    @BindView(R.id.phone_input)
    EditText mPhoneInput;
    @BindView(R.id.password_input)
    EditText mPasswordInput;

    String mOpenId;
    LoginPhoneType mLoginPhoneType;
    InputMethodManager mIMM;
    boolean mIsCountingDown;//是否正在倒计时
    NormalProgressDialog mLoginDialog;
    CompositeDisposable cd;
    String wxOpenId, wxUnionId, wxName, wxAvatar;
    AppInfoModel appInfo;
    LoginInfoDBHelper loginInfoDBHelper;

    public static Intent getSignIntent() {
        Intent intent = IntentUtil.getIntent(LoginPhoneActivity.class);
        intent.setType(TYPE_SIGN);
        return intent;
    }

    public static Intent getBindIntent(String wxOpenId, String wxUnionId, String wxAvatar, String wxName) {
        Intent intent = IntentUtil.getIntent(LoginPhoneActivity.class);
        intent.setType(TYPE_BIND);
        intent.putExtra(KEY_WXOPNEOD, wxOpenId);
        intent.putExtra(KEY_WXUNION, wxUnionId);
        intent.putExtra(KEY_WXAVATAR, wxAvatar);
        intent.putExtra(KEY_WXNAME, wxName);
        return intent;
    }

    public static Intent getEditIntent(String openId) {
        Intent intent = IntentUtil.getIntent(LoginPhoneActivity.class);
        intent.setType(TYPE_EDIT);
        intent.putExtra(KEY_OPEN_ID, openId);
        return intent;
    }

    @Override
    public int provideContentView() {
        return R.layout.activity_login_phone;
    }

    @Override
    public void create(@Nullable Bundle savedInstanceState) {
        cd = new CompositeDisposable();
        appInfo = AppInfoDBHelper.newInstance().getAppInfo();
        loginInfoDBHelper = LoginInfoDBHelper.newInstance();
        sInstance = this;
        mIMM = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mOpenId = getIntent().getStringExtra(KEY_OPEN_ID);
        wxOpenId = getIntent().getStringExtra(KEY_WXOPNEOD);
        wxUnionId = getIntent().getStringExtra(KEY_WXUNION);
        wxName = getIntent().getStringExtra(KEY_WXNAME);
        wxAvatar = getIntent().getStringExtra(KEY_WXAVATAR);

        mLoginPhoneType = getLoginPhoneType();
        mToolbarTitle.setText(mLoginPhoneType.getToolbarTitle());
        mSignButton.setText(mLoginPhoneType.getButtonTitle());
        mPhoneInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        mPasswordInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        mPhoneInput.addTextChangedListener(mPhoneWatcher);
        mPasswordInput.addTextChangedListener(mPasswordWatcher);
        mGetIcLl.setVisibility(View.GONE);
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    private LoginPhoneType getLoginPhoneType() {
        if (TYPE_SIGN.equals(getIntent().getType())) {
            return new LoginPhoneSign();
        } else if (TYPE_BIND.equals(getIntent().getType())) {
            return new LoginPhoneBind();
        } else {
            return new LoginPhoneEdit();
        }
    }

    //倒计时
    CountDownTimer mCountDownTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mPasswordButton.setText(String.format("已发送(%d秒)", millisUntilFinished / 1000));
            mPasswordButton.setEnabled(false);
            mGetAudioIcButton.setEnabled(false);
        }

        @Override
        public void onFinish() {
            mGetIcLl.setVisibility(View.VISIBLE);
            mGetAudioIcButton.setEnabled(true);
            mPasswordButton.setEnabled(true);
            mPasswordButton.setText("发送验证码");
        }
    };

    MyTextWatcher mPhoneWatcher = new MyTextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean isPhoneNum = CheckStringUtil.isPhoneNumber(s.toString());
            mPasswordButton.setEnabled(isPhoneNum && !mIsCountingDown);
            mGetAudioIcButton.setEnabled(isPhoneNum && !mIsCountingDown);
            setPhoneIcon(isPhoneNum);
            setButtonBG();
        }
    };

    MyTextWatcher mPasswordWatcher = new MyTextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            setPasswordIcon(s.length() == 4);
            setButtonBG();
        }
    };

    private void setPhoneIcon(boolean isFit) {
        mPhoneIc.setImageResource(isFit ? R.drawable.ic_input_phone_positive : R.drawable.ic_input_phone_negative);
    }

    private void setPasswordIcon(boolean isFit) {
        mPasswordIc.setImageResource(isFit ? R.drawable.ic_input_pwd_positive : R.drawable.ic_input_pwd_negative);
    }

    private void setButtonBG() {
        mSignButton.setBackgroundResource(mPhoneInput.length() == 11 && mPasswordInput.length() == 4 ?
                R.drawable.button_main : R.drawable.button_main_unable);
    }

    void showLoginDialog() {
        if (mLoginDialog == null) {
            mLoginDialog = NormalProgressDialog.newInstance("正在提交，请稍候...");
        }
        mLoginDialog.showDialog(getFragmentManager(), "login");
    }

    void dissmissLoginDialog() {
        if (mLoginDialog != null) {
        }
        mLoginDialog.dismissAllowingStateLoss();
    }

    @OnClick(R.id.toolbar_icon_left)
    void pressBack(View v) {
        onBackPressed();
    }

    /**
     * 获得验证码
     */
    @OnClick(R.id.password_button)
    void getIC(View v) {
        requestVC("1");
    }

    /**
     * 获得语音验证码
     *
     * @param v
     */
    @OnClick(R.id.get_audio_ic_button)
    void getAudioVC(View v) {
        ToastUtil.showShortToast("全民乐帮将会给你拨打电话，通过语音播报验证码，请注意接听来电");
        requestVC("2");
    }

    @OnClick(R.id.sign_button)
    void presseLogin(View v) {
        if (!CheckStringUtil.isPhoneNumber(mPhoneInput.getText().toString())) {
            ToastUtil.showShortToast("请输入正确的手机号");
        } else if (mPasswordInput.length() == 0) {
            ToastUtil.showShortToast("请输入验证码");
        } else if (mPasswordInput.length() != 4) {
            ToastUtil.showShortToast("验证码长度不正确");
        } else {
            if (TYPE_SIGN.equals(getIntent().getType())) {
                requestLogin();
            } else if (TYPE_BIND.equals(getIntent().getType())) {
                requestBindPhone(wxOpenId, wxUnionId);
            } else if (TYPE_EDIT.equals(getIntent().getType())) {
                requestChangePhone();
            }
        }
    }

    /**
     * 联网请求验证码
     *
     * @param type
     */
    void requestVC(String type) {
        cd.clear();
        mCountDownTimer.start();
        mIMM.hideSoftInputFromWindow(mPasswordInput.getWindowToken(), 0);

        final long currentTime = System.currentTimeMillis() / 1000;
        Network.getInstance().getVerifycode(
                BSConstant.VERIFY_CODE,
                mPhoneInput.getText().toString(),
                PhoneInfoDBHelper.getIPAddress(),
                PhoneInfoDBHelper.getMac(),
                currentTime,
                AppUtil.encryptSign((currentTime - 444) + "", "", mPhoneInput.getText().toString(), PhoneInfoDBHelper.getIPAddress()),
                type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onNext(@NonNull BaseBean bean) {
                        if (bean.getCode() == 4003) {
                            mCountDownTimer.cancel();
                            mCountDownTimer.onFinish();
                            ToastUtil.showShortToast("当前手机获取验证码次数超出每日限制");
                        } else if (bean.getCode() == 4444) {
                            mCountDownTimer.cancel();
                            mCountDownTimer.onFinish();
                            ToastUtil.showServiceErrorToast("验证码发送错误，请联系客服");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mCountDownTimer.cancel();
                        mCountDownTimer.onFinish();
                        ToastUtil.showServiceErrorToast();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 登录
     */
    void requestLogin() {
        requestBindPhone(null, null);
    }

    /**
     * 绑定
     */
    void requestBindPhone(String wxoi, String wxun) {
        showLoginDialog();
        cd.clear();
        Network.getInstance().login(BSConstant.LOGIN,
                mPhoneInput.getText().toString(),
                mPasswordInput.getText().toString(),
                wxoi,
                wxun,
                appInfo.getCid(),
                wxName,
                wxAvatar)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new Observer<BaseBean<LoginBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onNext(@NonNull BaseBean<LoginBean> baseBean) {
                        dissmissLoginDialog();
                        if (baseBean != null && baseBean.getCode() == 200) {
                            saveLoginUserInfo(baseBean.getObj().getUser());
                            if (getIntent().getType().equals(TYPE_BIND)) {
                                LoginActivitiesManger.newInstance().phoneRegisterSuccess();
                            } else if (getIntent().getType().equals(TYPE_SIGN)) {
                                LoginActivitiesManger.newInstance().phoneLoginSuccess();
                            }
                            startActivity(IntentUtil.getIntent(MainActivity.class));
                            finish();
                        } else if (baseBean != null && baseBean.getCode() == 4003) {
                            ToastUtil.showShortToast("验证码有误，请重新输入");
                        } else if (baseBean != null && baseBean.getCode() == 4005) {
                            ToastUtil.showShortToast("手机号已存在");
                        } else if (baseBean != null && baseBean.getCode() == 202) {
                            ToastUtil.showShortToast("绑定错误，该手机号已绑定其他QQ号");
                        } else if (baseBean != null && baseBean.getCode() == 201) {
                            ToastUtil.showShortToast("绑定错误，该手机号已绑定其他微信号");
                        } else {
                            ToastUtil.showServiceErrorToast();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dissmissLoginDialog();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 修改手机号
     */
    void requestChangePhone() {
        showLoginDialog();
        Network.getInstance().changePhone(BSConstant.CHANGE_PHONE, loginInfoDBHelper.getLoginInfo().getOpenId(),
                mPhoneInput.getText().toString(), mPasswordInput.getText().toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onNext(@NonNull BaseBean baseBean) {
                        dissmissLoginDialog();
                        if (baseBean != null && baseBean.getCode() == 200) {
                            LoginActivitiesManger.newInstance().phoneRegisterSuccess();
                        } else if (baseBean != null && baseBean.getCode() == 4003) {
                            ToastUtil.showShortToast("验证码有误，请重新输入");
                        } else if (baseBean != null && baseBean.getCode() == 4005) {
                            ToastUtil.showShortToast("手机号已存在");
                        } else {
                            ToastUtil.showServiceErrorToast();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dissmissLoginDialog();

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
    protected void onDestroy() {
        mCountDownTimer.cancel();
        mPhoneInput.removeTextChangedListener(mPhoneWatcher);
        mPasswordInput.removeTextChangedListener(mPasswordWatcher);
        mIMM = null;
        cd.clear();
        super.onDestroy();
    }

    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
        sInstance = null;
    }
}
