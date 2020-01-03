package com.example.movietmdb.repository

import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.movietmdb.MovieTmdbApplication
import com.example.movietmdb.mapper.MoviePresentationMapper
import com.example.movietmdb.recycler.MoviePresentation
import com.example.movietmdb.repository.db.entity.MovieData
import com.example.movietmdb.repository.retrofit.RetrofitInitializer
import com.example.movietmdb.repository.retrofit.SearchResults
import kotlinx.android.synthetic.main.search_movies_layout.*
import kotlinx.coroutines.launch

class RepositoryRules {

    suspend fun getMovies(name: String, page: Int): ArrayList<MoviePresentation> {
        val resultsRetrofit: SearchResults = RetrofitInitializer()
            .retrofitServices.searchMoviesByUser(name, page)
        val moviesResults = resultsRetrofit.results
        return MoviePresentationMapper().convertListMovieService(
            moviesResults,
            MovieTmdbApplication.db.movieDao().getAll()
        )
    }

    suspend fun getFavMovies(): ArrayList<MoviePresentation> {
        val fav = MovieTmdbApplication.db.movieDao().getAll()
        return MoviePresentationMapper().converterListMovieData(fav)
    }

    suspend fun insertMovie(movie: MovieData) {
        MovieTmdbApplication.db.movieDao().insertMovie(movie)
    }

    suspend fun removeMovie(movie: MovieData) {
        MovieTmdbApplication.db.movieDao().removeMovie(movie)
    }
}