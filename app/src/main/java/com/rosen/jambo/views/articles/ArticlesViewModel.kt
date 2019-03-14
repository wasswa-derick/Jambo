package com.rosen.jambo.views.articles

import android.arch.lifecycle.ViewModel
import com.rosen.jambo.domain.data.repository.impl.NewsRepositoryImpl
import io.reactivex.Observable

/**
 * Created by Derick W on 28,February,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
class ArticlesViewModel : ViewModel() {

    private val repository: NewsRepositoryImpl = NewsRepositoryImpl()
    private var articles: List<Articles> = listOf()
//    internal var articles: MutableList<Article> = ArrayList()


    fun getAllNewsArticles(location : String, apiKey : String): Observable<MutableList<Article>>? {
        return repository.getNewsArticlesForLocation(location, apiKey)
                .flatMap {
                    Observable.just(it.articles)
                }
    }

    fun getArticleList(): Observable<List<Article>> {
        return Observable.just(repository.getArticleList())
    }


}
