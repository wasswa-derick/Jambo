package com.rosen.jambo

import android.app.Application
import com.rosen.jambo.domain.dependencyinjection.AppComponent
import com.rosen.jambo.domain.dependencyinjection.DaggerAppComponent
import com.rosen.jambo.domain.dependencyinjection.RetrofitServiceModule

/**
 * Created by Derick W on 26,February,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
class JamboApplication: Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        component = createApplicationComponent()

        if (BuildConfig.DEBUG) {
            // Maybe TimberPlant etc.
        }
    }

    fun getApplicationComponent(): AppComponent {
        return component
    }

    fun createApplicationComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .retrofitServiceModule(RetrofitServiceModule())
                .build();
    }

}
