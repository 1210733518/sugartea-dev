package com.dianjiake.android.api.params;

import java.util.Map;

import okhttp3.RequestBody;

/**
 * Created by lfs on 2017/7/21.
 */

public class EvaluateParams extends BaseParams {

    public EvaluateParams() {
        super();
        requestParams.put("bs", getTextBody("fuwudingdanpinglun"));
    }

    public void setOrderNum(String orderNum) {
        requestParams.put("ordernum", getTextBody(orderNum));
    }

    public void setOpenId(String openid) {
        requestParams.put("openid", getTextBody(openid));
    }

    public void setShopId(String shopId) {
        requestParams.put("shanghuid", getTextBody(shopId));
    }

    public void setPinglunlist(String json) {
        requestParams.put("pinglunlist", getJSONBody(json));
    }

    @Override
    public Map<String, RequestBody> getRequestParams() {
        return requestParams;
    }
}
