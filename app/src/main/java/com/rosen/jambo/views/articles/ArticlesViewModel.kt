package com.rosen.jambo.views.articles

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.rosen.jambo.domain.data.repository.impl.NewsRepositoryImpl
import com.rosen.jambo.views.bookmarks.Bookmark
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.disposables.Disposable
import io.reactivex.Observer


/**
 * Created by Derick W on 28,February,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
class ArticlesViewModel(application: Application?) : ViewModel() {

    private var mApplication: Application? = null

    var articleList: List<Article> = listOf()
    val articlesLoading = ObservableBoolean()
    val articleListIsEmpty = ObservableBoolean()
    val bookMarksEmpty = ObservableBoolean()


    init {
        mApplication = application
    }

    private val repository: NewsRepositoryImpl = NewsRepositoryImpl(mApplication)

    fun getAllNewsArticles(location : String, apiKey : String) : Observable<MutableList<Article>> {
        articlesLoading.set(true)
        val articlesObservable : Observable<MutableList<Article>> = repository.getNewsArticlesForLocation(location, apiKey)
                .flatMap {
                    Observable.just(it.articles)
                }


        articlesObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<Article>> {

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(articles: List<Article>) {
                        if (!articleList.isEmpty()) articleList = listOf()

                        articleList = articles
                        articleListIsEmpty.set(articles.isEmpty())
                    }

                    override fun onError(e: Throwable) {
                    }

                    override fun onComplete() {
                        articlesLoading.set(false)
                        if (articleList.isEmpty()) {
                            articleListIsEmpty.set(true)
                        } else {
                            articleListIsEmpty.set(false)
                        }
                    }
                })

        return articlesObservable
    }

    fun getOfflineArticlesByTag(articleTag : String) : List<Article> {
        articleList = repository.getArticlesBy(articleTag)
        articlesLoading.set(false)
        if (articleList.isEmpty()) {
            articleListIsEmpty.set(true)
        } else {
            articleListIsEmpty.set(false)
        }
        return repository.getArticlesBy(articleTag)
    }

    fun saveTagArticles(articles : List<Article>, articleTag: String) {
        repository.saveArticles(articles, articleTag)
    }

    fun bookArticles(bookmark: Bookmark) {
        repository.bookmarkArticle(bookmark)
    }

    fun getAllBookmarks() : List<Bookmark> {
        if (repository.getBookmarks().isEmpty()) {
            bookMarksEmpty.set(true)
        } else {
            bookMarksEmpty.set(false)
        }
        return repository.getBookmarks()
    }

    fun getArticleByID(articleID : String) : Article {
        return repository.getArticleByID(articleID)
    }


}
