package com.rosen.jambo.utils;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by Derick W on 15,March,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
public class ArticleWebViewClient extends WebViewClient {

    private ProgressBar progressBar;

    public ArticleWebViewClient(ProgressBar progressBar) {
        this.progressBar = progressBar;
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }


    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        progressBar.setProgress(100);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        progressBar.setProgress(0);
        super.onPageStarted(view, url, favicon);
    }

}
