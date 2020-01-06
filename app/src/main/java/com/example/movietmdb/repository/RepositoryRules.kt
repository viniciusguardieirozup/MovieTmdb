package com.example.movietmdb.repository

import com.example.movietmdb.MovieTmdbApplication
import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.recycler.MoviePresentation
import com.example.movietmdb.repository.db.entity.MovieData
import com.example.movietmdb.repository.retrofit.RetrofitInitializer
import com.example.movietmdb.repository.retrofit.SearchResults
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RepositoryRules : CoroutineScope {
    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun stopJob() {
        job.cancel()
    }

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

    suspend fun getMoviesByGenres(id: Int, page: Int): ArrayList<MoviePresentation> {
        return MoviePresentationMapper().convertListMovieService(
            RetrofitInitializer().retrofitServices.getMoviesByGenres(id, page).results,
            MovieTmdbApplication.db.movieDao().getAll()
        )
    }

    fun saveMovie(movie: MovieData) {
        launch {
            MovieTmdbApplication.db.movieDao().insertMovie(movie)
        }
    }

    fun deleteMovie(movie: MovieData) {
        launch {
            MovieTmdbApplication.db.movieDao().removeMovie(movie)
        }
    }

}