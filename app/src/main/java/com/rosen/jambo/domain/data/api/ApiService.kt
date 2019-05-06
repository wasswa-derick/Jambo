package com.rosen.jambo.domain.data.api

import com.rosen.jambo.views.articles.Articles
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Derick W on 13,March,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
interface ApiService {

    @GET("everything")
    fun getNewsArticles(@Query("q")location: String, @Query("apiKey")apiKey: String): Observable<Articles>

}
