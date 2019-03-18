package com.rosen.jambo.views.articles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.rosen.jambo.R;
import com.rosen.jambo.utils.ArticleWebViewClient;

/**
 * Created by Derick W on 15,March,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
public class MoreDetailsActivity extends AppCompatActivity {

    WebView articleWebView;
    ProgressBar progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.article_webview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        articleWebView = (WebView)findViewById(R.id.article_web_view);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        articleWebView.setWebViewClient(new ArticleWebViewClient(progress));

        articleWebView.getSettings().setLoadsImagesAutomatically(true);
        articleWebView.getSettings().setJavaScriptEnabled(true);
        articleWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        articleWebView.loadUrl(getIntent().getStringExtra("url"));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
