package com.rosen.jambo.domain.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rosen.jambo.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Derick W on 26,February,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
public class RetrofitModule {

    static Retrofit retrofitService = null;


    /**
     * Retrofit Instance initialized with a converter
     *
     * @return The GitHubUserAPI interface
     */
    public static APIService getNewsArticlesService() {
        Gson gson = new GsonBuilder().setLenient().create();

        if (retrofitService == null) {
            retrofitService = new Retrofit
                    .Builder()
                    .baseUrl(Constants.API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofitService.create(APIService.class);
    }
}
