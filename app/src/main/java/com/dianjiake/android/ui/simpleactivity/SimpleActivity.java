package com.dianjiake.android.ui.simpleactivity;

import android.content.Intent;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.dianjiake.android.base.BaseWebViewActivity;
import com.dianjiake.android.util.IntentUtil;

/**
 * Created by lfs on 2017/7/21.
 */

public class SimpleActivity extends BaseWebViewActivity {
    public static Intent getStartIntent(String url, String title) {
        Intent intent = IntentUtil.getIntent(SimpleActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        return intent;
    }

    String url, title;

    @Override
    protected void provideIntent(Intent intent) {
        url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");
    }

    @Override
    protected String getToolbarTitle() {
        return title;
    }

    @Override
    protected String getUrl() {
        return url;
    }

    @Override
    protected void addView(FrameLayout view) {

    }

    @Override
    protected void initWebView(WebView webView) {

    }
}
