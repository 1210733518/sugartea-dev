package com.dianjiake.android.view.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;


import com.dianjiake.android.R;
import com.dianjiake.android.util.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lfs on 2017/6/14.
 */

public class ShareDialog extends AppCompatActivity implements UMShareListener {
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESC = "desc";
    private static final String KEY_URL = "url";
    private static final String KEY_IMG = "img";
    private static final String KEY_IMG_RESOURCE = "imgresource";


    String tag;
    long lastTimestamp;

    String title, desc, url, imgUrl;
    int imgResource;

    public static void Show(Context context, String title, String desc, String url, String imgUrl) {
        Intent intent = new Intent(context, ShareDialog.class);
        intent.putExtra(KEY_TITLE, title);
        intent.putExtra(KEY_DESC, desc);
        intent.putExtra(KEY_IMG, imgUrl);
        intent.putExtra(KEY_URL, url);
        context.startActivity(intent);
    }

    public static void Show(Context context, String title, String desc, String url, int imageResource) {
        Intent intent = new Intent(context, ShareDialog.class);
        intent.putExtra(KEY_TITLE, title);
        intent.putExtra(KEY_DESC, desc);
        intent.putExtra(KEY_IMG_RESOURCE, imageResource);
        intent.putExtra(KEY_URL, url);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.dialog_share_enter,R.anim.dialog_share_exit);
        setContentView(R.layout.dialog_share);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        ButterKnife.bind(this);
        title = getIntent().getStringExtra(KEY_TITLE);
        desc = getIntent().getStringExtra(KEY_DESC);
        imgUrl = getIntent().getStringExtra(KEY_IMG);
        url = getIntent().getStringExtra(KEY_URL);
        imgResource = getIntent().getIntExtra(KEY_IMG_RESOURCE, R.mipmap.ic_launcher);
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @OnClick(R.id.share_wx)
    void wx(View v) {
        startShare(SHARE_MEDIA.WEIXIN);
    }

    @OnClick(R.id.share_moments)
    void moments(View v) {
        startShare(SHARE_MEDIA.WEIXIN_CIRCLE);
    }

    @OnClick(R.id.share_qq)
    void qq(View v){
        startShare(SHARE_MEDIA.QQ);
    }

    @OnClick(R.id.share_qzone)
    void qzone(View v){
        startShare(SHARE_MEDIA.QZONE);
    }

    @OnClick(R.id.share_close)
    void close(View v) {
        finish();
    }

    private void startShare(SHARE_MEDIA shareMedia) {
        UMWeb umWeb = new UMWeb(url);
        umWeb.setTitle(title);
        umWeb.setDescription(desc);
        if (TextUtils.isEmpty(imgUrl)) {
            umWeb.setThumb(new UMImage(this, imgResource));
        } else {
            umWeb.setThumb(new UMImage(this, imgUrl));
        }

        new ShareAction(this)
                .setPlatform(shareMedia)
                .withText(title)
                .withMedia(umWeb)
                .setCallback(this)
                .share();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        UMShareAPI.get(this).release();
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.dialog_share_enter,R.anim.dialog_share_exit);
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        ToastUtil.showShortToast("分享成功");
        finish();
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        ToastUtil.showShortToast("分享失败");
        finish();
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        ToastUtil.showShortToast("取消分享");
        finish();
    }
}

