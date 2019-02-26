package com.rosen.jambo.domain.dependencyinjection

import com.rosen.jambo.domain.data.api.APIService
import com.rosen.jambo.domain.data.api.RetrofitModule
import dagger.Module
import dagger.Provides

/**
 * Created by Derick W on 14,February,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
@Module
class RetrofitServiceModule {

    @Provides
    fun provideAPIService(): APIService {
        return RetrofitModule.getNewsArticlesService()
    }

}
