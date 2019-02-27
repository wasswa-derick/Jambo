package com.rosen.jambo.domain.data.api;

import com.rosen.jambo.views.articles.Article;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Derick W on 26,February,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
public interface APIService {

    @GET("everything?q={location}&apiKey={apiKey}")
    Call<Article> getNewsArticles(@Path("location") String location, @Path("apiKey") String apiKey);

}
