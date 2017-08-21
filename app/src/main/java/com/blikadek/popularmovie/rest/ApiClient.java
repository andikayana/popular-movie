package com.blikadek.popularmovie.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by M13x5aY on 21/08/2017.
 */

public class ApiClient {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String BASE_IMG_URL = "http://image.tmdb.org/t/p/";

    private static Retrofit mRetrofit, mRetrofitImage;

    public static Retrofit getRetrofitClient(){
        if (mRetrofit ==null ){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    public static Retrofit getRetrofitImageClient(){
        if (mRetrofitImage ==null ){
            mRetrofitImage = new Retrofit.Builder()
                    .baseUrl(BASE_IMG_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofitImage;
    }
}
