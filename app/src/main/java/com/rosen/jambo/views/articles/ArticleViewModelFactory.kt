package com.rosen.jambo.views.articles

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

/**
 * Created by Derick W on 16,March,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
class ArticleViewModelFactory(application: Application) : ViewModelProvider.Factory {

    private var mApplication: Application? = application

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArticlesViewModel(mApplication) as T
    }
}
