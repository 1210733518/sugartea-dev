package com.dianjiake.android.api;


import com.dianjiake.android.constant.Constant;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

/**
 * Created by lfs on 2017/5/15.
 */

public interface OtherApi {
    @GET(Constant.NETWORK_INFO + "?ie=utf-8")
    Observable<ResponseBody> networkInfo();
}
