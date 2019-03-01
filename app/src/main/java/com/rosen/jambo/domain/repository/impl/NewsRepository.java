package com.rosen.jambo.domain.repository.impl;

import android.util.Log;

import com.rosen.jambo.domain.data.api.APIService;
import com.rosen.jambo.domain.data.api.RetrofitModule;
import com.rosen.jambo.domain.repository.abstractions.NewsRepositoryInterface;
import com.rosen.jambo.views.articles.Article;
import com.rosen.jambo.views.articles.Articles;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Derick W on 28,February,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
public class NewsRepository implements NewsRepositoryInterface {

    APIService apiService = RetrofitModule.getNewsArticlesService();
    List<Article> articles = new ArrayList<>();

    @Override
    public List<Article> getNewsArticlesForLocation(String location, final String apiKey) {

        apiService
                .getNewsArticles(location, apiKey)
                .enqueue(new Callback<Articles>() {
                    @Override
                    public void onResponse(Call<Articles> call, Response<Articles> response) {

                        if (response.isSuccessful()) {
                            articles = response.body().getArticles();
                        }
                    }

                    @Override
                    public void onFailure(Call<Articles> call, Throwable t) {
                        try {
                            Log.d("message", t.getLocalizedMessage());
                            throw new InterruptedException("Something went wrong!");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

        return articles;

    }

    @Override
    public List<Article> getNewsArticlesForLocationOffline(String location) {
        return null;
    }

    @Override
    public void saveUsers(List<Article> articles, String location) {

    }

}
