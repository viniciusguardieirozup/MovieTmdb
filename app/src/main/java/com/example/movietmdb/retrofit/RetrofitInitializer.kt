package com.example.movietmdb.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {
    val retrofitServices: MoviesServices
        get() {
            return Retrofit.Builder().baseUrl("https://www.themoviedb.org/").addConverterFactory(GsonConverterFactory.create())
                .build().create(MoviesServices::class.java)

        }
}