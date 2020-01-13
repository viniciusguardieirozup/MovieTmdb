package com.example.movietmdb.repository.retrofit

import com.example.movietmdb.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//interface to use retrofit
interface MoviesServices {
    @GET("search/movie?api_key=" + BuildConfig.TMDB_KEY)
    suspend fun searchMoviesByUser(@Query("query") name: String, @Query("page") number :Int): SearchResults

    @GET("genre/movie/list?api_key=" + BuildConfig.TMDB_KEY)
    suspend fun getGenres() : GenresList

    @GET("discover/movie?api_key=" + BuildConfig.TMDB_KEY)
    suspend fun getMoviesByGenres(@Query("with_genres")id : Int, @Query("page") page :Int): SearchResults

    @GET("movie/{movie_id}/similar?api_key=" + BuildConfig.TMDB_KEY)
    suspend fun getSimilars(@Path("movie_id") id :Int, @Query("page") page : Int) : SearchResults
}