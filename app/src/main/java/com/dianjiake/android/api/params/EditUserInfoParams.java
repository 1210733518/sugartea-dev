package com.dianjiake.android.api.params;


import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.util.CheckEmptyUtil;

import java.io.File;
import java.util.Map;

import okhttp3.RequestBody;

/**
 * Created by lfs on 2017/6/20.
 */

public class EditUserInfoParams extends BaseParams {

    public EditUserInfoParams() {
        super();
        requestParams.put("bs", getTextBody(BSConstant.EDIT_USER_INFO));
    }


    public void setOpenId(String huiyuandengji) {
        requestParams.put("openid", getTextBody(huiyuandengji));
    }

    public void setNickname(String nickname) {
        requestParams.put("nickname", getTextBody(nickname));
    }

    public void setSex(String sex) {
        requestParams.put("sex", getTextBody(sex));
    }

    public void setBirthday(String birthday) {
        requestParams.put("birthday", getTextBody(birthday));
    }

    public void setJieshushijian(String jieshushijian) {
        requestParams.put("jieshushijian", getTextBody(jieshushijian));
    }

    public void setProfession(String profession) {
        requestParams.put("profession", getTextBody(profession));
    }

    public void setLocation(String location) {
        requestParams.put("location", getTextBody(location));

    }

    public void setlongitude(String longitude) {
        if (!CheckEmptyUtil.isEmpty(longitude)) {
            requestParams.put("longitude", getTextBody(longitude));
        }
    }

    public void setlatitude(String latitude) {
        if (!CheckEmptyUtil.isEmpty(latitude)) {
            requestParams.put("latitude", getTextBody(latitude));
        }
    }

    public void setTouxiang(String touxiang) {
        if (!CheckEmptyUtil.isEmpty(touxiang)) {
            requestParams.put("touxiang", getTextBody(touxiang));
        }
    }

    public void setAvatar(File file) {
        requestParams.put("avatar\"; filename=\"" + file.getName(), getImageBody(file));

    }

    @Override
    public Map<String, RequestBody> getRequestParams() {
        return requestParams;
    }


}
