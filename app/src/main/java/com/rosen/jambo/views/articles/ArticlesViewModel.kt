package com.rosen.jambo.views.articles

import android.arch.lifecycle.ViewModel
import com.rosen.jambo.domain.repository.impl.NewsRepository
import io.reactivex.Observable

/**
 * Created by Derick W on 28,February,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
class ArticlesViewModel : ViewModel() {

    private val repository: NewsRepository = NewsRepository()

    fun getAllNewsArticles(location : String, apiKey : String): Observable<List<Article>> {
        return Observable.just(repository.getNewsArticlesForLocation(location, apiKey))
    }


}
