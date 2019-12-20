package com.example.movietmdb.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesServices {
    @GET("search/movie?api_key=d272326e467344029e68e3c4ff0b4059")
    fun searchMoviesByUser(@Query("query") name: String): Call<SearchResults>
}