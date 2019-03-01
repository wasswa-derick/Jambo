package com.rosen.jambo.domain.data.api;

import com.rosen.jambo.views.articles.Articles;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Derick W on 26,February,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
public interface APIService {

    @GET("everything")
    Call<Articles> getNewsArticles(@Query("q") String location, @Query("apiKey") String apiKey);

}
