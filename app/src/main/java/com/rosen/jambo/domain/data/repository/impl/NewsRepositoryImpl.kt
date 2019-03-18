package com.rosen.jambo.domain.data.repository.impl

import android.app.Application
import android.os.AsyncTask
import com.rosen.jambo.domain.data.api.RetrofitModule
import com.rosen.jambo.domain.data.db.ArticleDao
import com.rosen.jambo.domain.data.db.ArticleDatabase
import com.rosen.jambo.domain.data.repository.abstractions.NewsInterfaceRepository
import com.rosen.jambo.views.articles.Article
import com.rosen.jambo.views.articles.Articles
import io.reactivex.Observable
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.Future

/**
 * Created by Derick W on 13,March,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
class NewsRepositoryImpl(application: Application?) : NewsInterfaceRepository {

    private var articleDao: ArticleDao
    private var articlesBy: List<Article>? = null

    init {
        val db = ArticleDatabase.getDatabase(application)
        articleDao = db.articleDao()
    }

    private var apiService = RetrofitModule().getNewsArticlesService()
    internal var articles: MutableList<Article> = ArrayList()

    override fun getArticleList(): List<Article> = articles

    override fun getNewsArticlesForLocation(location: String, apiKey: String): Observable<Articles> = apiService.getNewsArticles(location, apiKey)

    override fun getNewsArticlesForLocationOffline(location: String): List<Article> {
        return getArticlesBy(location)
    }

    override fun saveUsers(articles: List<Article>, location: String) {

        for (article in articles) {
            article.id = location
            var insertTask = InsertAsyncTask(articleDao)
            insertTask.execute(article)
        }

    }

    override fun deleteTagArticles(location: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    @Throws(ExecutionException::class, InterruptedException::class)
    fun getArticlesBy(articleTag : String): List<Article> {

        var callable: Callable<List<Article>> = Callable { articleDao.getAllArticles(articleTag) }
        var future : Future<List<Article>> = Executors.newSingleThreadExecutor().submit<List<Article>>(callable)

        return future.get()
    }

    private class InsertAsyncTask : AsyncTask<Article, Void, Void> {

        override fun doInBackground(vararg params: Article?): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }

        private var mAsyncTaskDao: ArticleDao

        constructor(dao: ArticleDao) {
            mAsyncTaskDao = dao
        }

    }

}
