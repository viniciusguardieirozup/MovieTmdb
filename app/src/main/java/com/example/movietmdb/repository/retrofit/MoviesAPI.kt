package com.example.movietmdb.repository.retrofit

import com.example.movietmdb.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//interface to use retrofit
interface MoviesAPI {
    @GET("search/movie?")
    suspend fun searchMoviesByUser(@Query("query") name: String, @Query("page") number: Int): SearchResults

    @GET("genre/movie/list?")
    suspend fun getGenres(): GenresList

    @GET("discover/movie?")
    suspend fun getMoviesByGenres(@Query("with_genres") id: Int, @Query("page") page: Int): SearchResults

    @GET("movie/{movie_id}/similar?")
    suspend fun getSimilars(@Path("movie_id") id: Int, @Query("page") page: Int): SearchResults
}