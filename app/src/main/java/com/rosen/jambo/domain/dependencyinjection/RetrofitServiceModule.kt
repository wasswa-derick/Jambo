package com.rosen.jambo.domain.dependencyinjection

import com.rosen.jambo.domain.data.api.ApiService
import com.rosen.jambo.domain.data.api.RetrofitModule
import dagger.Module
import dagger.Provides
import javax.inject.Inject

/**
 * Created by Derick W on 14,February,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
@Module
class RetrofitServiceModule @Inject constructor() {

    @Provides
    fun provideAPIService(): ApiService {
        return RetrofitModule().getNewsArticlesService()
    }

}
