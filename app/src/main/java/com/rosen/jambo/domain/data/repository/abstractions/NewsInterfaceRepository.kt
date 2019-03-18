package com.rosen.jambo.domain.data.repository.abstractions

import com.rosen.jambo.views.articles.Article
import com.rosen.jambo.views.articles.Articles
import io.reactivex.Observable

/**
 * Created by Derick W on 13,March,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
interface NewsInterfaceRepository {

    fun getNewsArticlesForLocation(location: String, apiKey: String): Observable<Articles>

    fun getNewsArticlesForLocationOffline(location: String): List<Article>

    fun saveUsers(articles: List<Article>, location: String)

    fun deleteTagArticles(location: String)

}
