package com.example.movietmdb.retrofit

import retrofit2.http.GET
import retrofit2.http.Query

//interface to use retrofit
interface MoviesServices {
    @GET("search/movie?api_key=d272326e467344029e68e3c4ff0b4059&")
    suspend fun searchMoviesByUser(@Query("query") name: String): SearchResults
}