package com.example.movietmdb.repository

import com.example.movietmdb.MovieTmdbApplication
import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.repository.db.entity.MovieData
import com.example.movietmdb.repository.retrofit.RetrofitInitializer
import com.example.movietmdb.repository.retrofit.SearchResults
import java.lang.Exception

class RepositoryRules {

    suspend fun getMovies(name: String, page: Int) =
        RetrofitInitializer().retrofitServices.searchMoviesByUser(name, page)


    suspend fun getFavMovies() = MovieTmdbApplication.db.movieDao().getAll()

    suspend fun insertMovie(movie: MovieData) {
        MovieTmdbApplication.db.movieDao().insertMovie(movie)
    }

    suspend fun removeMovie(movie: MovieData) {
        MovieTmdbApplication.db.movieDao().removeMovie(movie)
    }

    suspend fun getMoviesByGenres(id: Int, page: Int): ArrayList<MoviePresentation> {
        val resultsRetrofit: SearchResults =
            RetrofitInitializer().retrofitServices.getMoviesByGenres(id, page)
        if (resultsRetrofit.results.size == 0) {
            throw Exception("Movies not found")
        }
        return MoviePresentationMapper().convertListMovieService(
            resultsRetrofit.results, MovieTmdbApplication.db.movieDao().getAll()
        )
    }
}