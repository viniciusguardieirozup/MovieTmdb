package com.example.movietmdb.repository.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//retrofit initializer
class RetrofitInitializer {
    val retrofitServices: MoviesServices
        get() {
            //build retrofit for the baseurl, add a JSON to Object cionverter
            val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build()
            //create the retrofit with the MovieServices interface
            return retrofit.create(MoviesServices::class.java)
        }
}