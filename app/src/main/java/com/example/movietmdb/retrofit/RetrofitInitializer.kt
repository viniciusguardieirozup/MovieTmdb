package com.example.movietmdb.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {
    val retrofitServices: MoviesServices
        get() {
            val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build()
            return retrofit.create(MoviesServices::class.java)
        }
}g