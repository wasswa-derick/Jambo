package com.rosen.jambo.views.articles;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.rosen.jambo.R;
import com.rosen.jambo.databinding.ArticleDetailsBinding;

/**
 * Created by Derick W on 14,March,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
public class ArticleDetails extends AppCompatActivity {

    FloatingActionButton share;

    Article article;
    String title, content, description, author, timestamp, url, image;
    ArticleDetailsBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.article_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        share = findViewById(R.id.share);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        title = bundle.getString("title");
        content = bundle.getString("content");
        description = bundle.getString("description");
        author = bundle.getString("author");
        timestamp = bundle.getString("timestamp");
        url = bundle.getString("url");
        image = bundle.getString("image");

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(author);

        article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setDescription(description);
        article.setPublishedAt(timestamp);
        article.setUrlToImage(image);
        article.setAuthor(author);
        article.setUrl(url);
        binding.setArticle(article);
        shareButtonClickHandler();
    }

    public void shareButtonClickHandler(){
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String articleShare = "Check out this awesome article @ " + url;
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, articleShare);
                startActivity(Intent.createChooser(shareIntent, "Share Article"));
            }
        });
    }
}
