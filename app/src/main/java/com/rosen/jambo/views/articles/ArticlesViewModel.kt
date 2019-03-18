package com.rosen.jambo.views.articles

import android.app.Application
import android.arch.lifecycle.ViewModel
import com.rosen.jambo.domain.data.repository.impl.NewsRepositoryImpl
import io.reactivex.Observable

/**
 * Created by Derick W on 28,February,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
class ArticlesViewModel(application: Application?) : ViewModel() {

    private var mApplication: Application? = null

    init {
        mApplication = application
    }

    private val repository: NewsRepositoryImpl = NewsRepositoryImpl(mApplication)

    fun getAllNewsArticles(location : String, apiKey : String): Observable<MutableList<Article>>? {
        return repository.getNewsArticlesForLocation(location, apiKey)
                .flatMap {
                    Observable.just(it.articles)
                }
    }

    fun getOfflineArticlesByTag(articleTag : String) : List<Article> {
        return repository.getArticlesBy(articleTag)
    }

    fun saveTagArticles(articles : List<Article>, articleTag: String) {
        repository.saveUsers(articles, articleTag)
    }

}
