package com.rosen.jambo.domain.repository.abstractions;

import com.rosen.jambo.views.articles.Article;

import java.util.List;

/**
 * Created by Derick W on 28,February,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
public interface NewsRepositoryInterface {

    List<Article> getNewsArticlesForLocation(String location, String apiKey);

    List<Article> getNewsArticlesForLocationOffline(String location);

    void saveUsers(List<Article> articles, String location);

}
