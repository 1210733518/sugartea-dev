package com.dianjiake.android.api;


import com.dianjiake.android.constant.Constant;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lfs on 2017/5/15.
 */

public class Network {
    private static Api instance;

    private Network() {
    }

    public static Api getInstance() {
        if (instance == null) {
            synchronized (Network.class) {
                if (instance == null) {
//                    LogInterceptor logging = new LogInterceptor();
//                    logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
                    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                    if (Constant.IS_DEBUG) {
                        httpClient.addNetworkInterceptor(new StethoInterceptor());
                    }

                    instance = new Retrofit.Builder()
                            .baseUrl(Constant.APP_URL)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(httpClient.build())
                            .build().create(Api.class);
                }
            }
        }
        return instance;
    }

    public static OtherApi newOtherApi() {
        return new Retrofit.Builder()
                .baseUrl(Constant.NETWORK_INFO)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(OtherApi.class);
    }
}
