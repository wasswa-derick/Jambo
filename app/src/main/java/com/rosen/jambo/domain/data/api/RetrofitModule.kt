package com.rosen.jambo.domain.data.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rosen.jambo.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Derick W on 13,March,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
class RetrofitModule {

    private var retrofitService : Retrofit? = null

    fun getNewsArticlesService(): ApiService {

        var gson : Gson = GsonBuilder().setLenient().create()

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()

        if (retrofitService == null) {
            retrofitService = Retrofit.Builder()
                    .baseUrl(Constants.API_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
        }

        return retrofitService!!.create(ApiService::class.java)
    }

}
