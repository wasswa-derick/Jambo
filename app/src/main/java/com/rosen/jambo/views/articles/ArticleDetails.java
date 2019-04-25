package com.rosen.jambo.views.articles;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.rosen.jambo.R;
import com.rosen.jambo.databinding.ArticleDetailsBinding;
import com.rosen.jambo.views.bookmarks.Bookmark;

/**
 * Created by Derick W on 14,March,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
public class ArticleDetails extends AppCompatActivity {

    Article article;
    String title, content, description, author, timestamp, url, image;
    ArticleDetailsBinding binding;
    ArticlesViewModel articlesViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.article_details);
        articlesViewModel = ViewModelProviders.of(this, new ArticleViewModelFactory(getApplication())).get(ArticlesViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

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
    }

    public void shareButtonClickHandler(View view){
        String articleShare = "Check out this awesome article @ " + url;
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, articleShare);
        startActivity(Intent.createChooser(shareIntent, "Share Article"));
    }

    public void webViewDetails(View view) {
        Intent intent = new Intent(ArticleDetails.this, MoreDetailsActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_copy) {
            String data = title + "\n\n" + description + "\n\n" + content + "\n\n" + author + "\n\n" + url;
            copyToClipboard(getApplicationContext(), data);
            Toast.makeText(this, "Copied", Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.action_bookmark_article) {
            Bookmark bookmark = new Bookmark();
            bookmark.setArticleID(title);
            articlesViewModel.bookArticles(bookmark);
            Toast.makeText(this, "Article bookmarked.", Toast.LENGTH_LONG).show();
        }

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean copyToClipboard(Context context, String data) {

        try {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context
                        .getSystemService(context.CLIPBOARD_SERVICE);
                clipboard.setText(data);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context
                        .getSystemService(context.CLIPBOARD_SERVICE);

                android.content.ClipData clip = android.content.ClipData
                        .newPlainText(context.getResources().getString(R.string.copyContent), data);
                clipboard.setPrimaryClip(clip);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
