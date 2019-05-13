package com.rosen.jambo.domain.data.repository.impl

import android.app.Application
import android.os.AsyncTask
import com.rosen.jambo.JamboApplication
import com.rosen.jambo.domain.data.api.RetrofitModule
import com.rosen.jambo.domain.data.db.ArticleDao
import com.rosen.jambo.domain.data.db.ArticleDatabase
import com.rosen.jambo.domain.data.db.BookmarkDao
import com.rosen.jambo.domain.data.repository.abstractions.NewsInterfaceRepository
import com.rosen.jambo.domain.dependencyinjection.RetrofitServiceModule
import com.rosen.jambo.views.articles.Article
import com.rosen.jambo.views.articles.Articles
import com.rosen.jambo.views.bookmarks.Bookmark
import io.reactivex.Observable
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.Future
import javax.inject.Inject

/**
 * Created by Derick W on 13,March,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
class NewsRepositoryImpl(application: Application?) : NewsInterfaceRepository {

    private var articleDao: ArticleDao
    private var bookmarkDao: BookmarkDao
    @Inject lateinit var retrofitServiceModule : RetrofitServiceModule

    init {
        (application as JamboApplication).getApplicationComponent().inject(this)
        val db = ArticleDatabase.getDatabase(application)
        articleDao = db.articleDao()
        bookmarkDao = db.bookmarkDao()
    }

    var apiService = retrofitServiceModule.provideAPIService()

    override fun getNewsArticlesForLocation(location: String, apiKey: String): Observable<Articles> = apiService.getNewsArticles(location, "popularity", apiKey)

    override fun getNewsArticlesForLocationOffline(location: String): List<Article> {
        return getArticlesBy(location)
    }

    override fun getArticleByID(articleID: String) : Article {
        return getSingleArticle(articleID)
    }

    override fun saveArticles(articles: List<Article>, location: String) {

        for (article in articles) {
            article.id = location
            var insertTask = InsertArticleAsyncTask(articleDao)
            insertTask.execute(article)
        }

    }

    override fun getBookmarks(): List<Bookmark> {
        return fetchBookmarks()
    }

    override fun bookmarkArticle(bookmark: Bookmark) {
        var insertTask = InsertBookmarkAsyncTask(bookmarkDao)
        insertTask.execute(bookmark)
    }


    @Throws(ExecutionException::class, InterruptedException::class)
    fun getArticlesBy(articleTag : String): List<Article> {

        var callable: Callable<List<Article>> = Callable { articleDao.getAllArticles(articleTag) }
        var future : Future<List<Article>> = Executors.newSingleThreadExecutor().submit<List<Article>>(callable)

        return future.get()
    }

    @Throws(ExecutionException::class, InterruptedException::class)
    fun fetchBookmarks(): List<Bookmark> {

        var callable: Callable<List<Bookmark>> = Callable { bookmarkDao.bookmarks }
        var future : Future<List<Bookmark>> = Executors.newSingleThreadExecutor().submit<List<Bookmark>>(callable)

        return future.get()
    }

    @Throws(ExecutionException::class, InterruptedException::class)
    fun getSingleArticle(title : String): Article {

        var callable: Callable<Article> = Callable { articleDao.getArticle(title) }
        var future : Future<Article> = Executors.newSingleThreadExecutor().submit<Article>(callable)

        return future.get()
    }

    private class InsertArticleAsyncTask : AsyncTask<Article, Void, Void> {
        override fun doInBackground(vararg params: Article?): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }

        private var mAsyncTaskDao: ArticleDao

        constructor(dao: ArticleDao) {
            mAsyncTaskDao = dao
        }
    }

    private class InsertBookmarkAsyncTask : AsyncTask<Bookmark, Void, Void> {
        override fun doInBackground(vararg params: Bookmark?): Void? {
            mAsyncTaskDao.insertBookmark(params[0])
            return null
        }

        private var mAsyncTaskDao: BookmarkDao

        constructor(dao: BookmarkDao) {
            mAsyncTaskDao = dao
        }
    }

}
