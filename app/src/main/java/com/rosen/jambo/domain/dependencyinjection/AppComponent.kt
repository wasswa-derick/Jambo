package com.rosen.jambo.domain.dependencyinjection

import com.rosen.jambo.views.articles.MainActivity
import dagger.Component

/**
 * Created by Derick W on 14,February,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */

@Component(modules = [RetrofitServiceModule::class])
interface AppComponent {

    fun inject(mainactivity: MainActivity)

}
