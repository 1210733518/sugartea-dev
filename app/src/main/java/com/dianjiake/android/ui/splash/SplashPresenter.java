package com.dianjiake.android.ui.splash;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;

import com.dianjiake.android.api.Network;
import com.dianjiake.android.base.App;
import com.dianjiake.android.data.db.LoginInfoDBHelper;
import com.dianjiake.android.data.db.PhoneInfoDBHelper;
import com.dianjiake.android.data.model.PhoneInfoModel;
import com.dianjiake.android.util.ToastUtil;
import com.dianjiake.android.util.UIUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import timber.log.Timber;

/**
 * Created by lfs on 2017/5/15.
 */

public class SplashPresenter implements SplashContract.Presenter {
    CompositeDisposable compositeDisposable;
    SplashContract.SplashView view;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            jump();
        }
    };

    public SplashPresenter(SplashContract.SplashView view) {
        this.view = view;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void start() {
        handler.postDelayed(runnable, 2000);
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(runnable);
        handler = null;
        compositeDisposable.clear();
    }


    /*
    guaju ：拿到手机网络及屏幕状态
     */
    @Override
    public void getPhoneInfo() {
        Network.newOtherApi().networkInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ResponseBody s) {
                        try {
                            String responseStr = s.string();
                            String result = responseStr.substring(responseStr.indexOf("\"cip\": \"") + 8, responseStr.indexOf("\", \"cid\""));
                            PhoneInfoModel phoneInfoModel = new PhoneInfoModel();
                            phoneInfoModel.setIp(result);
                            phoneInfoModel.setMac(getMAC());
                            phoneInfoModel.setScreenHeight(UIUtil.getScreenHeight());
                            phoneInfoModel.setScreenWidth(UIUtil.getScreenWidth());
                            PhoneInfoDBHelper.savePhoneInfo(phoneInfoModel);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.d(e);
                        ToastUtil.showNetworkErrorToast();
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("complete");
                    }
                });
    }

    @Override
    public String getMAC() {
        try {
            WifiManager manager = (WifiManager) App.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = manager.getConnectionInfo();
            String address = info.getMacAddress();
            return address;
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public void jump() {
        if (LoginInfoDBHelper.newInstance().isLogin()) {
            view.startMainActivity();
        } else {
            view.startLoginActivity();
        }
    }


}
