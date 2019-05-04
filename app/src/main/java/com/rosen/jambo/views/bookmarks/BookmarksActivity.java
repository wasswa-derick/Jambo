package com.rosen.jambo.views.bookmarks;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.rosen.jambo.R;
import com.rosen.jambo.databinding.BookmarksBinding;
import com.rosen.jambo.databinding.FragmentMainBinding;
import com.rosen.jambo.views.articles.Article;
import com.rosen.jambo.views.articles.ArticleDetails;
import com.rosen.jambo.views.articles.ArticleListClick;
import com.rosen.jambo.views.articles.ArticleViewModelFactory;
import com.rosen.jambo.views.articles.ArticlesAdapter;
import com.rosen.jambo.views.articles.ArticlesViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Derick W on 26,April,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
public class BookmarksActivity extends AppCompatActivity {

    RecyclerView articlesRecyclerView;

    StaggeredGridLayoutManager staggeredGridLayoutManager;
    LinearLayoutManager linearLayoutManager;

    ArticlesAdapter articlesAdapter;
    List<Article> articleList = new ArrayList<>();
    List<Bookmark> bookmarkList = new ArrayList<>();
    ArticlesViewModel articlesViewModel;

    //Font Style Preferences
    private SharedPreferences.OnSharedPreferenceChangeListener prefListener;
    SharedPreferences prefs;

    BookmarksBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bookmarks);
        View view = binding.getRoot();

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getApplicationContext().getString(R.string.bookmarks));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        articlesViewModel = ViewModelProviders.of(this, new ArticleViewModelFactory(getApplication())).get(ArticlesViewModel.class);
        fetchBookmarks();

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        //listener on changed sort text preference:
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        prefs.registerOnSharedPreferenceChangeListener(prefListener);

        binding.setViewModel(articlesViewModel);


        articlesRecyclerView = view.findViewById(R.id.articles_list);
        articlesAdapter = new ArticlesAdapter(getApplicationContext(), articleList);
        articlesRecyclerView.setAdapter(articlesAdapter);
        articlesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        getArticleDetails();
        getBookmarkArticles();

    }

    public void fetchBookmarks() {
        if (!bookmarkList.isEmpty()) {
            bookmarkList.clear();
        }
        bookmarkList.addAll(articlesViewModel.getAllBookmarks());
    }

    public void getBookmarkArticles(){

        if (!articleList.isEmpty()) {
            articleList.clear();
        }

        for (Bookmark bookmark: bookmarkList) {
            articleList.add(articlesViewModel.getArticleByID(bookmark.getArticleID()));
        }

        articlesAdapter.notifyDataSetChanged();
    }

    private void getArticleDetails() {
        articlesRecyclerView.addOnItemTouchListener(new ArticleListClick(getApplicationContext(), new ArticleListClick.OnItemClickListener() {
            @Override
            public void onItemClick(View childView, int position) {

                    Intent intent = new Intent(getApplicationContext(), ArticleDetails.class);
                    Bundle bundle = new Bundle();

                    Article article = articleList.get(position);
                    bundle.putString("title", article.getTitle());
                    bundle.putString("content", article.getContent());
                    bundle.putString("description", article.getDescription());
                    bundle.putString("author", article.getAuthor());
                    bundle.putString("timestamp", article.getPublishedAt());
                    bundle.putString("url", article.getUrl());
                    bundle.putString("image", article.getUrlToImage());
                    intent.putExtra("data", bundle);
                    startActivity(intent);

            }

            @Override
            public void onItemLongPress(View childView, int position) {
            }
        }));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_quilt) {
            articlesRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        } else if (id == R.id.action_list) {
            articlesRecyclerView.setLayoutManager(linearLayoutManager);
        } else if (id == R.id.action_text_size) {
            showTextSize();
        } else if (id == R.id.action_text_style) {
            showTextStyle();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem bookmarkMenu = menu.findItem(R.id.action_bookmarks);
        bookmarkMenu.setVisible(false);
        return true;
    }

    // show the dialog text style
    public void showTextStyle() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(BookmarksActivity.this, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = LayoutInflater.from(BookmarksActivity.this);
        View dl = inflater.inflate(R.layout.text_style, null);

        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/RobotoRegular.ttf");
        Typeface typeface1 = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/LobsterTwoRegular.otf");
        Typeface typeface2 = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/journal.ttf");
        Typeface typeface3 = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Walkway.ttf");
        Typeface typeface5 = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/GoodDog.otf");
        Typeface typeface4 = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/DancingScriptRegular.otf");

        RadioButton roboto = dl.findViewById(R.id.roboto);
        roboto.setTypeface(typeface);
        RadioButton journal = dl.findViewById(R.id.journal);
        journal.setTypeface(typeface2);
        RadioButton dancing = dl.findViewById(R.id.dancing);
        dancing.setTypeface(typeface4);
        RadioButton walkway = dl.findViewById(R.id.walkway);
        walkway.setTypeface(typeface3);
        RadioButton gooddog = dl.findViewById(R.id.gooddog);
        gooddog.setTypeface(typeface5);
        RadioButton lobster = dl.findViewById(R.id.lobster);
        lobster.setTypeface(typeface1);

        String style = prefs.getString("text_style", "1");
        if (style.equalsIgnoreCase("1")) {
            roboto.setChecked(true);
        }else if (style.equalsIgnoreCase("2")) {
            journal.setChecked(true);
        }else if (style.equalsIgnoreCase("3")) {
            lobster.setChecked(true);
        }else if (style.equalsIgnoreCase("4")) {
            dancing.setChecked(true);
        }else if (style.equalsIgnoreCase("5")) {
            walkway.setChecked(true);
        }else if (style.equalsIgnoreCase("6")) {
            gooddog.setChecked(true);
        }

        RadioGroup text_styles = dl.findViewById(R.id.text_styles);
        text_styles.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.roboto:
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("text_style", "1");
                        editor.apply();
                        break;
                    case R.id.journal:
                        SharedPreferences.Editor editor1 = prefs.edit();
                        editor1.putString("text_style", "2");
                        editor1.apply();
                        break;
                    case R.id.lobster:
                        SharedPreferences.Editor editor2 = prefs.edit();
                        editor2.putString("text_style", "3");
                        editor2.apply();
                        break;
                    case R.id.gooddog:
                        SharedPreferences.Editor editor3 = prefs.edit();
                        editor3.putString("text_style", "6");
                        editor3.apply();
                        break;
                    case R.id.dancing:
                        SharedPreferences.Editor editor4 = prefs.edit();
                        editor4.putString("text_style", "4");
                        editor4.apply();
                        break;
                    case R.id.walkway:
                        SharedPreferences.Editor editor5 = prefs.edit();
                        editor5.putString("text_style", "5");
                        editor5.apply();
                        break;
                }

                articlesAdapter.notifyDataSetChanged();
            }
        });

        builder.setTitle("Choose text style.");
        builder.setView(dl);


        final AlertDialog customAlertDialog = builder.create();
        customAlertDialog.show();

    }

    public void showTextSize() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(BookmarksActivity.this, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = LayoutInflater.from(BookmarksActivity.this);
        View dl = inflater.inflate(R.layout.text_sizes, null);

        RadioButton normal = dl.findViewById(R.id.normal);
        RadioButton small = dl.findViewById(R.id.small);
        RadioButton medium = dl.findViewById(R.id.medium);
        RadioButton large = dl.findViewById(R.id.large);
        RadioButton xlarge = dl.findViewById(R.id.xlarge);


        String style = prefs.getString("text_style", "1");
        if (style.equalsIgnoreCase("1")) {
            normal.setChecked(true);
        }else if (style.equalsIgnoreCase("2")) {
            small.setChecked(true);
        }else if (style.equalsIgnoreCase("3")) {
            medium.setChecked(true);
        }else if (style.equalsIgnoreCase("4")) {
            large.setChecked(true);
        }else if (style.equalsIgnoreCase("5")) {
            xlarge.setChecked(true);
        }

        RadioGroup text_sizes = dl.findViewById(R.id.text_sizes);
        text_sizes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.normal:
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("text_size", "1");
                        editor.apply();
                        break;
                    case R.id.small:
                        SharedPreferences.Editor editor1 = prefs.edit();
                        editor1.putString("text_size", "2");
                        editor1.apply();
                        break;
                    case R.id.medium:
                        SharedPreferences.Editor editor2 = prefs.edit();
                        editor2.putString("text_size", "3");
                        editor2.apply();
                        break;
                    case R.id.large:
                        SharedPreferences.Editor editor3 = prefs.edit();
                        editor3.putString("text_size", "4");
                        editor3.apply();
                        break;
                    case R.id.xlarge:
                        SharedPreferences.Editor editor4 = prefs.edit();
                        editor4.putString("text_size", "5");
                        editor4.apply();
                        break;
                }

                articlesAdapter.notifyDataSetChanged();
            }
        });

        builder.setTitle("Choose text style.");
        builder.setView(dl);

        final AlertDialog customAlertDialog = builder.create();
        customAlertDialog.show();


    }
}
