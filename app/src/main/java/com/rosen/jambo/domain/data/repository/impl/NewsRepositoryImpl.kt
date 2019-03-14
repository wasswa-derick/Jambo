package com.rosen.jambo.domain.data.repository.impl

import com.rosen.jambo.domain.data.api.RetrofitModule
import com.rosen.jambo.domain.data.repository.abstractions.NewsInterfaceRepository
import com.rosen.jambo.views.articles.Article
import com.rosen.jambo.views.articles.Articles
import io.reactivex.Observable
import java.util.ArrayList

/**
 * Created by Derick W on 13,March,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
class NewsRepositoryImpl : NewsInterfaceRepository {

    private var apiService = RetrofitModule().getNewsArticlesService()
    internal var articles: MutableList<Article> = ArrayList()

    override fun getArticleList(): List<Article> = articles

    override fun getNewsArticlesForLocation(location: String, apiKey: String): Observable<Articles> = apiService.getNewsArticles(location, apiKey)

    override fun getNewsArticlesForLocationOffline(location: String): List<Article> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveUsers(articles: List<Article>, location: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
