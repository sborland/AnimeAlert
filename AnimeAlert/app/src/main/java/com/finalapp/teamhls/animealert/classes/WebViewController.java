package com.finalapp.teamhls.animealert.classes;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by sab on 3/4/2016.
 */
public class WebViewController extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
